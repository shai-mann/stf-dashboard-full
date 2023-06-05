package com.vmware.stfdashboard.services;

import com.vmware.stfdashboard.api.Test;
import com.vmware.stfdashboard.api.TestRun;
import com.vmware.stfdashboard.api.TestRunList;
import com.vmware.stfdashboard.api.TestSummary;
import com.vmware.stfdashboard.api.UpstreamRun;
import com.vmware.stfdashboard.api.builders.AbstractTestBuilder;
import com.vmware.stfdashboard.api.builders.AbstractUpstreamBuilder;
import com.vmware.stfdashboard.api.builders.RunInfoBuilder;
import com.vmware.stfdashboard.api.meta.RunInfo;
import com.vmware.stfdashboard.api.meta.RunSummary;
import com.vmware.stfdashboard.models.processed.JobBuildEntity;
import com.vmware.stfdashboard.models.processed.TestEntity;
import com.vmware.stfdashboard.models.processed.TestResultEntity;
import com.vmware.stfdashboard.models.processed.UpstreamJobBuildEntity;
import com.vmware.stfdashboard.models.processed.UpstreamJobEntity;
import com.vmware.stfdashboard.repositories.processed.TestRepository;
import com.vmware.stfdashboard.repositories.processed.TestResultRepository;
import com.vmware.stfdashboard.util.SddcType;
import com.vmware.stfdashboard.util.Status;
import com.vmware.stfdashboard.util.Utils;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TestService {

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private TestRepository testRepository;

    public TestEntity getTestById(int testId) {
        return testRepository.findById(testId).orElseThrow(() -> {
            throw new RuntimeException("Failed to find Test with ID #" + testId);
        });
    }

    public Page<TestRun> compileResults(UpstreamJobBuildEntity upstreamBuild,
                                        int page, int itemsPerPage,
                                        Optional<String[]> filter, Optional<String> orderingColumn,
                                        Optional<Boolean> reverse) {
        List<JobBuildEntity> builds = upstreamBuild.getTriggeredJobs();

        Page<TestEntity> tests = collectTestPage(upstreamBuild, page, itemsPerPage,
                filter, orderingColumn, reverse);
        Page<TestRun> out = tests.map(this::toTestRun);

        Map<Integer, TestRun> testsById = out.stream().collect(
                Collectors.toMap(TestRun::getId, Function.identity()));

        builds.forEach(b -> addResults(b, testsById, tests));
        return out;
    }

    public Page<TestSummary> compileResults(UpstreamJobEntity upstreamJob, int page,
                                            int itemsPerPage, Optional<String> filter) {
        List<JobBuildEntity> builds = new ArrayList<>();
        upstreamJob.getBuilds().forEach(b -> builds.addAll(b.getTriggeredJobs()));

        // format filter correctly
        String formattedFilter = Utils.extractFilter(filter.orElse(""), "name", "");

        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        Page<TestSummary> out = testRepository.findAllByUpstreamAndNameContainingIgnoreCase(
                        upstreamJob, formattedFilter, pageable).map(this::toTestSummary);

        out.forEach(t -> {
            for (SddcType sddc : SddcType.values()) {
                List<JobBuildEntity> subBuilds = builds.stream().filter(b ->
                        b.getJob().getSddc().equals(sddc)).toList();

                int passedCount = testResultRepository.countByTest_IdAndBuildInAndStatus(t.getId(),
                        subBuilds, Status.PASSED.value());
                int total = testResultRepository.countByTest_IdAndBuildIn(t.getId(), subBuilds);

                t.addResult(sddc, new RunSummary(passedCount, total, Status.PASSED));
            }
        });

        return out;
    }

    public TestRunList getTestRunResults(TestEntity test, Optional<Integer> build) {
        TestRunList out = toTestRunlist(test);

        test.getResults().forEach(r -> {
            if (build.isPresent() && r.getBuild().getUpstreamBuild().getId() != build.get()) return;

            out.addResult(r.getBuild().getJob().getSddc(), toRunInfo(r));
        });

        return out;
    }

    public List<UpstreamRun> getTestHistory(TestEntity test) {
        List<UpstreamRun> out = new ArrayList<>();

        test.getUpstream().getBuilds().forEach(b -> out.add(buildDurationUpstream(test, b)));

        return out;
    }

    public int countByStatus(UpstreamJobBuildEntity build, Status status) {
        return build.getTriggeredJobs().stream().mapToInt(b -> countByStatus(b, status)).sum();
    }

    public int countByStatus(JobBuildEntity build, Status status) {
        return testResultRepository.countAllByBuildAndStatus(build, status.value());
    }

    public Test getTest(int id) {
        TestEntity entity = this.getTestById(id);
        return new Test(entity.getId(), entity.getName(), entity.getParameters(),
                entity.getUpstream().getSuite().value());
    }

    /* HELPERS */

    private void addResults(JobBuildEntity build, Map<Integer, TestRun> tests,
                            Page<TestEntity> testEntities) {
        testEntities.forEach(t ->
                t.getResults().forEach(r -> {
                    if (r.getBuild().getId() != build.getId()) return;

                    SddcType sddc = r.getBuild().getJob().getSddc();
                    tests.get(t.getId()).addResult(sddc, toRunInfo(r));
                })
        );
    }

    private UpstreamRun buildDurationUpstream(TestEntity test, UpstreamJobBuildEntity build) {
        UpstreamRun out = new AbstractUpstreamBuilder.UpstreamRunBuilder()
                .setJobId(build.getUpstreamJob().getId())
                .setBuildId(build.getId())
                .setBuildNumber(build.getBuildNumber())
                .setOb(build.getOb())
                .setBuildTimestamp(build.getBuildTimestamp())
                .setStatus(build.getStatus().value())
                .build();

        build.getTriggeredJobs().forEach(b ->
            testResultRepository.findByBuildAndTest(b, test).ifPresent(testResult ->
                out.addResult(b.getJob().getSddc(), toRunInfo(testResult))
            )
        );

        return out;
    }

    private Page<TestEntity> collectTestPage(UpstreamJobBuildEntity upstreamBuild,
                                          int page, int itemsPerPage,
                                          Optional<String[]> filter,
                                          Optional<String> orderingColumn,
                                          Optional<Boolean> reversed) {
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        String fullFilter = String.join("*", filter.orElse(new String[]{})) + "*";

        // format filter correctly
        String formattedFilter = Utils.extractFilter(fullFilter, "name", "");

        // defaults to VMC if no ordering SDDC type is given.
        // if a status filter is given, ordering is overridden and that sddc is ordered by.
        SddcType sddc = SddcType.findByValue(orderingColumn.orElse(SddcType.VMC.value()));
        Optional<Status> status = Optional.empty();

        for (SddcType sddcType : SddcType.values()) {
            String statusFilter = Utils.extractFilter(fullFilter, sddcType.value(), "");
            if (!statusFilter.isBlank()) {
                sddc = sddcType;
                status = Optional.of(Status.findByValue(statusFilter));
            }
        }

        SddcType finalSddc = sddc;
        JobBuildEntity build = upstreamBuild.getTriggeredJobs().stream().filter(
                b -> b.getJob().getSddc().equals(finalSddc)).findFirst().orElse(null);

        // if given ordered column is defaulted + has no test results, find first column w/ results
        if (orderingColumn.isEmpty() && (build == null || build.getTestResults().isEmpty())) {
            build = upstreamBuild.getTriggeredJobs().stream().filter(
                    b -> !b.getTestResults().isEmpty()).findFirst().orElse(build);
        }

        if (build == null) {
            return Page.empty();
        }

        return findOrderedTests(upstreamBuild,
                build, formattedFilter, pageable, reversed.orElse(false), status
        );
    }

    private Page<TestEntity> findOrderedTests(UpstreamJobBuildEntity upstreamBuild,
                                              JobBuildEntity build,
                                              String formattedFilter,
                                              Pageable pageable,
                                              boolean reversed, Optional<Status> status) {
        String statusInput = status.isPresent() ? status.get().value() :
                Arrays.stream(Status.values()).map(Status::value).collect(Collectors.joining());

        return reversed ?
                testRepository.findOrderedTestsByUpstreamBuildDesc(
                        upstreamBuild.getUpstreamJob().getId(),
                        build.getId(),
                        formattedFilter,
                        statusInput,
                        pageable
                ) : testRepository.findOrderedTestsByUpstreamBuild(
                upstreamBuild.getUpstreamJob().getId(),
                build.getId(),
                formattedFilter,
                statusInput,
                pageable
        );
    }

    private TestRun toTestRun(TestEntity t) {
        return new AbstractTestBuilder.TestRunBuilder()
                .setId(t.getId())
                .setName(t.getName())
                .setParameters(t.getParameters())
                .setPackagePath(t.getPackagePath())
                .setClassName(t.getClassName())
                .build();
    }

    private TestRunList toTestRunlist(TestEntity t) {
        return new AbstractTestBuilder.TestRunListBuilder()
                .setId(t.getId())
                .setName(t.getName())
                .setParameters(t.getParameters())
                .setPackagePath(t.getPackagePath())
                .setClassName(t.getClassName())
                .build();
    }

    private TestSummary toTestSummary(TestEntity t) {
        return new AbstractTestBuilder.TestSummaryBuilder()
                .setId(t.getId())
                .setName(t.getName())
                .setParameters(t.getParameters())
                .setPackagePath(t.getPackagePath())
                .setClassName(t.getClassName())
                .build();
    }

    private RunInfo toRunInfo(TestResultEntity tr) {
        JobBuildEntity b = tr.getBuild();
        return new RunInfoBuilder()
                .setStatus(Status.findByValue(tr.getStatus()))
                .setDuration(tr.getDuration())
                .setStartedAt(new Date(tr.getStartedAt()))
                .setException(tr.getException())
                .setUpstreamBuildId(b.getUpstreamBuild().getId())
                .setBuildNumber(b.getBuildNumber())
                .setOb(b.getUpstreamBuild().getOb())
                .build();
    }

}
