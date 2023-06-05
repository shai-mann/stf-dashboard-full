package com.vmware.stfdashboard.processing;

import com.vmware.stfdashboard.models.generated.JenkinsJob;
import com.vmware.stfdashboard.models.generated.JenkinsJobBuild;
import com.vmware.stfdashboard.models.generated.JenkinsTestResult;
import com.vmware.stfdashboard.models.processed.JobBuildEntity;
import com.vmware.stfdashboard.models.processed.JobEntity;
import com.vmware.stfdashboard.models.processed.TestEntity;
import com.vmware.stfdashboard.models.processed.TestResultEntity;
import com.vmware.stfdashboard.models.processed.UpstreamJobBuildEntity;
import com.vmware.stfdashboard.models.processed.UpstreamJobEntity;
import com.vmware.stfdashboard.repositories.generated.JenkinsJobBuildRepository;
import com.vmware.stfdashboard.repositories.generated.JenkinsJobRepository;
import com.vmware.stfdashboard.repositories.generated.JenkinsTestResultRepository;
import com.vmware.stfdashboard.repositories.processed.JobBuildRepository;
import com.vmware.stfdashboard.repositories.processed.JobRepository;
import com.vmware.stfdashboard.repositories.processed.TestRepository;
import com.vmware.stfdashboard.repositories.processed.TestResultRepository;
import com.vmware.stfdashboard.repositories.processed.UpstreamBuildRepository;
import com.vmware.stfdashboard.repositories.processed.UpstreamJobRepository;
import com.vmware.stfdashboard.util.SddcType;
import com.vmware.stfdashboard.util.Status;
import com.vmware.stfdashboard.util.SuiteType;
import com.vmware.stfdashboard.util.Utils;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

/**
 * Processes and moves all data from the PUBLIC schema to the PROCESSED schema.
 * This is done using Jenkins repositories (from the PUBLIC schema) and transferring them to
 * other repositories (from PROCESSED schema).
 */
@Service
public class Processor {

    private final static Logger logger = LoggerFactory.getLogger(Processor.class);

    /**
     * The token identifier (found in the job name) that marks a job
     * as an "Upstream" job - a job that runs a job of that test suite
     * against every SDDC.
     */
    public static final String UPSTREAM_JOB_TOKEN = "Upstream";

    public static final String STAGING_JOB_TOKEN = "Staging";

    private static final Function<String, Supplier<RuntimeException>> ifEntityNotFound = (s) ->
            () -> {
                throw new IllegalArgumentException(s);
            };

    /* GENERATED (JENKINS) REPOSITORIES */
    @Autowired
    private JenkinsJobRepository jenkinsJobRepository;

    @Autowired
    private JenkinsJobBuildRepository jenkinsJobBuildRepository;

    @Autowired
    private JenkinsTestResultRepository jenkinsTestResultRepository;

    /* PROCESSED REPOSITORIES */
    @Autowired
    private UpstreamJobRepository upstreamJobRepository;

    @Autowired
    private UpstreamBuildRepository upstreamBuildRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobBuildRepository jobBuildRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    public Processor() {}

    public void process() {
        logger.info("Beginning Jenkins data processing.");

        processUpstreamJobs();
        List<JenkinsJob> skippedJobs = processJobs();

        processUpstreamBuilds();
        List<JenkinsJobBuild> skippedJobBuilds = processBuilds(skippedJobs);

        processTestsTable(skippedJobBuilds);

//        processUniqueTests();
//        processTestResults(skippedJobBuilds);
    }

    @Transactional
    public void emptyProcessedTables() {
        logger.info("Deleting all old processed info.");

        // order of deletion matters
        testResultRepository.deleteAll();
        testRepository.deleteAll();

        jobBuildRepository.deleteAll();
        jobRepository.deleteAll();

        upstreamBuildRepository.deleteAll();
        upstreamJobRepository.deleteAll();
    }

    /* JOB PROCESSING */

    @Transactional
    private void processUpstreamJobs() {
        logger.info("Processing Upstream Jobs.");

        List<JenkinsJob> upstreamJobs =
                jenkinsJobRepository.findAllByNameContains(UPSTREAM_JOB_TOKEN);
        Pair<List<UpstreamJobEntity>, List<JenkinsJob>> out =
                Utils.filterMap(upstreamJobs, this::processUpstream);
        upstreamJobRepository.saveAll(out.getFirst());

        // no upstream jobs should be skipped, so it's a problem if any are.
        if (!out.getSecond().isEmpty()) {
            logger.warn("Skipped Upstream Job entries for: " +
                    out.getSecond().stream().map(JenkinsJob::getName).toList());
        }

        logger.info("Created {} entries.", upstreamJobRepository.count());
    }

    @Transactional
    private List<JenkinsJob> processJobs() {
        logger.info("Processing Jobs.");

        // grabs all non-upstream (non-staging) jobs
        List<JenkinsJob> jobs = jenkinsJobRepository.findAllByNameNotContainsAndNameNotContains(
                UPSTREAM_JOB_TOKEN, STAGING_JOB_TOKEN);

        Pair<List<JobEntity>, List<JenkinsJob>> out = Utils.filterMap(jobs, this::processJob);
        jobRepository.saveAll(out.getFirst());

        logger.info("Created {} entries.", jobRepository.count());

        return out.getSecond();
    }

    private UpstreamJobEntity processUpstream(JenkinsJob job) {
        return new UpstreamJobEntity(job.getId(), job.getName(),
                Utils.extractSuiteType(job.getName()), job.getUrl());
    }

    private JobEntity processJob(JenkinsJob job) {
        UpstreamJobEntity upstreamJob = findBySuite(job.getName());
        SddcType sddc = Utils.extractSddcType(job.getName());

        return new JobEntity(job.getId(), upstreamJob, job.getName(), sddc, job.getUrl());
    }

    /* BUILD PROCESSING */

    @Transactional
    private void processUpstreamBuilds() {
        logger.info("Processing Upstream Builds.");

        List<JenkinsJobBuild> upstreamBuilds =
                jenkinsJobBuildRepository.findAllByNameContains(UPSTREAM_JOB_TOKEN);

        Pair<List<UpstreamJobBuildEntity>, List<JenkinsJobBuild>> out =
                Utils.filterMap(upstreamBuilds, this::processUpstreamBuild);
        upstreamBuildRepository.saveAll(out.getFirst());

        // no upstream builds should be skipped, so it's a problem if any are.
        if (!out.getSecond().isEmpty()) {
            logger.warn("Skipped Upstream Build entries for: " +
                    out.getSecond().stream().map(JenkinsJobBuild::getName).toList());
        }

        logger.info("Created {} entries.", upstreamBuildRepository.count());
    }

    @Transactional
    private List<JenkinsJobBuild> processBuilds(List<JenkinsJob> skippedJobs) {
        logger.info("Processing Job Builds.");

        // ignore all builds linked to jobs that weren't processed
        List<JenkinsJobBuild> jobBuilds = findProcessableJobs(
                skippedJobs
        );

        Pair<List<JobBuildEntity>, List<JenkinsJobBuild>> out =
                Utils.filterMap(jobBuilds, this::processJobBuild);
        jobBuildRepository.saveAll(out.getFirst());

        logger.info("Created {} entries.", jobBuildRepository.count());

        // make note to skip all staging jobs
        out.getSecond().addAll(jenkinsJobBuildRepository.findAllByNameContains(STAGING_JOB_TOKEN));

        return out.getSecond();
    }

    private UpstreamJobBuildEntity processUpstreamBuild(JenkinsJobBuild build) {
        UpstreamJobEntity upstreamJob = findBySuite(build.getName());

        return new UpstreamJobBuildEntity(build.getId(), upstreamJob, build.getBuildNumber(),
                Utils.extractBuildNumber(build.getName()), build.getUrl(),
                Status.findByValue(build.getStatus()), build.getBuildTimestamp());
    }

    private JobBuildEntity processJobBuild(JenkinsJobBuild build) {
        UpstreamJobEntity upstreamJob = findBySuite(build.getName());
        SddcType sddc = Utils.extractSddcType(build.getName());

        JobEntity job = jobRepository.findBySddcAndUpstream(sddc, upstreamJob).orElseThrow(
                ifEntityNotFound.apply("Failed to find Job with Upstream ID " + upstreamJob.getId())
        );

        long ob = Utils.extractBuildNumber(build.getName());
        UpstreamJobBuildEntity upstreamBuild =
                findClosestByUpstreamAndOb(upstreamJob, ob, build.getBuildTimestamp()).orElseThrow(
                        ifEntityNotFound.apply("Failed to find Upstream Job Build with ob " + ob)
                );

        return new JobBuildEntity(build.getId(), job, upstreamBuild, build.getBuildNumber(),
                build.getUrl(), build.getCommitId(), build.getStatus(), build.getFailedCount(),
                build.getSkippedCount(), build.getBuildTimestamp());
    }

    private Optional<UpstreamJobBuildEntity> findClosestByUpstreamAndOb(
            UpstreamJobEntity upstream, long ob, long buildTimestamp) {
        return upstreamBuildRepository.findFirstByUpstreamJobAndObAndBuildTimestampLessThanEqualOrderByBuildNumberDesc(
                upstream, ob, buildTimestamp
        );
    }

    /* TEST PROCESSING */

    @Transactional
    private void processTestsTable(List<JenkinsJobBuild> skippedJobBuilds) {
        logger.info("Processing Jenkins Test table.");
        Map<Integer, TestEntity> uniqueTests = new HashMap<>();
        Set<TestResultEntity> results = new HashSet<>();

        jenkinsTestResultRepository.findAll().forEach(result -> {
            UpstreamJobEntity upstream;
            try {
                upstream = findBySuite(result.getBuild().getName());
            } catch (RuntimeException e) {
                logger.info("Skipped test with linked build {}", result.getBuild().getName());
                return;
            }

            Optional<TestEntity> test = uniqueExists(result, upstream, uniqueTests);

            if (test.isEmpty()) {
                TestEntity testEntity = processUniqueTest(result, upstream);
                test = Optional.of(testEntity);
                uniqueTests.put(hashTest(testEntity, upstream), testEntity);
            }

            results.addAll(processTest(result, test.get(), skippedJobBuilds));
        });

        testRepository.saveAll(uniqueTests.values());

        logger.info("Created {} Test entries.", testRepository.count());

        testResultRepository.saveAll(results);

        logger.info("Created {} TestResult entries.", testResultRepository.count());
    }

    private int hashTest(TestEntity test, UpstreamJobEntity upstream) {
        return Objects.hash(
                upstream.getId(),
                test.getClassName(),
                test.getName(),
                test.getDataProviderIndex(),
                test.getPackagePath()
        );
    }

    private int hashTest(JenkinsTestResult test, UpstreamJobEntity upstream) {
        return Objects.hash(
                upstream.getId(),
                test.getClassName(),
                test.getName(),
                test.getDataProviderIndex(),
                test.getPackagePath()
        );
    }

    private Optional<TestEntity> uniqueExists(JenkinsTestResult result, UpstreamJobEntity upstream,
                                              Map<Integer, TestEntity> uniqueTests) {
        return Optional.ofNullable(uniqueTests.getOrDefault(hashTest(result, upstream), null));
    }

    private TestEntity processUniqueTest(JenkinsTestResult t, UpstreamJobEntity upstream) {
        return new TestEntity(
                upstream, t.getName(), t.getDataProviderIndex(),
                t.getClassName(), t.getPackagePath(), t.getParameters()
        );
    }

    private List<TestResultEntity> processTest(JenkinsTestResult t,
                                               TestEntity test,
                                               List<JenkinsJobBuild> skippedJobBuilds) {
        List<Integer> buildNumbers = new ArrayList<>() {{
            if (t.getPassedBuilds() != null) {
                addAll(Arrays.stream(t.getPassedBuilds()).boxed().toList());
            }

            if (t.getSkippedBuilds() != null) {
                addAll(Arrays.stream(t.getSkippedBuilds()).boxed().toList());
            }

            if (t.getFailedBuilds() != null) {
                addAll(Arrays.stream(t.getFailedBuilds()).boxed().toList());
            }

            if (t.getPresumedPassedBuilds() != null) {
                addAll(Arrays.stream(t.getPresumedPassedBuilds()).boxed().toList());
            }

            int buildNumber = t.getBuild().getBuildNumber();
            if (!contains(buildNumber)) {
                add(buildNumber);
            }
        }};

        List<JenkinsJobBuild> jenkinsJobBuilds = findJobBuilds(t.getBuild(), buildNumbers);
        List<JobBuildEntity> jobBuilds = jenkinsJobBuilds.stream()
                .filter(b -> !skippedJobBuilds.contains(b))
                .map(JenkinsJobBuild::getId)
                .map(jobBuildRepository::findById)
                .map(Optional::get)
                .toList();

//        List<JobBuildEntity> jobBuilds = Utils.toList(jobBuildRepository.findAllById(
//                () -> jenkinsJobBuilds.stream()
//                        .filter(b -> !skippedJobBuilds.contains(b))
//                        .map(JenkinsJobBuild::getId)
//                        .iterator()
//        ));

        return jobBuilds.stream().map(b ->
                new TestResultEntity(
                        test, b,
                        t.getStatus(), t.getException(),
                        t.getDuration(), t.getStartedAt()
                )
        ).toList();
    }

    /* HELPERS */

    private UpstreamJobEntity findBySuite(String name) {
        SuiteType suite = Utils.extractSuiteType(name);
        return upstreamJobRepository.findFirstBySuite(suite).orElseThrow(
                ifEntityNotFound.apply("Failed to find Upstream job for suite " + suite)
        );
    }

    private List<JenkinsJobBuild> findProcessableJobs(List<JenkinsJob> skippableJobs) {
        if (skippableJobs.isEmpty()) {
            return jenkinsJobBuildRepository.findAllByNameNotContainsAndNameNotContains(
                    Processor.UPSTREAM_JOB_TOKEN, Processor.STAGING_JOB_TOKEN);
        }

        // strangely, this method returns no entries if given an empty list of
        // skippable jobs - hence the above logic.
        return jenkinsJobBuildRepository.findAllByNameNotContainsAndNameNotContainsAndJobEntityNotIn(
                Processor.UPSTREAM_JOB_TOKEN, Processor.STAGING_JOB_TOKEN, skippableJobs
        );
    }

    private List<JenkinsJobBuild> findJobBuilds(JenkinsJobBuild build, List<Integer> buildNumbers) {
        return jenkinsJobBuildRepository.findByJobEntity_IdAndBuildNumberIn(
                build.getJobEntity().getId(), buildNumbers
        );
    }

}
