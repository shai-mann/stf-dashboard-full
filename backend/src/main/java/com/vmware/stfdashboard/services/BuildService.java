package com.vmware.stfdashboard.services;

import com.vmware.stfdashboard.api.BuildSummary;
import com.vmware.stfdashboard.api.UpstreamInfo;
import com.vmware.stfdashboard.api.UpstreamSummary;
import com.vmware.stfdashboard.api.builders.AbstractUpstreamBuilder;
import com.vmware.stfdashboard.api.meta.RunSummary;
import com.vmware.stfdashboard.models.processed.JobBuildEntity;
import com.vmware.stfdashboard.models.processed.UpstreamJobBuildEntity;
import com.vmware.stfdashboard.models.processed.UpstreamJobEntity;
import com.vmware.stfdashboard.repositories.processed.JobBuildRepository;
import com.vmware.stfdashboard.repositories.processed.UpstreamBuildRepository;
import com.vmware.stfdashboard.repositories.processed.UpstreamJobRepository;
import com.vmware.stfdashboard.util.SddcType;
import com.vmware.stfdashboard.util.Status;
import com.vmware.stfdashboard.util.SuiteType;
import com.vmware.stfdashboard.util.Utils;

import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BuildService {

    @Autowired
    private UpstreamBuildRepository upstreamBuildRepository;

    @Autowired
    private UpstreamJobRepository upstreamJobRepository;

    @Autowired
    private TestService testService;

    public List<UpstreamSummary> getUpstreamBuilds(SuiteType suite, Optional<String[]> filters) {
        List<UpstreamSummary> out =  Utils.filterMapSuppressed(
                getUpstreamBuildsUnformatted(suite),
                this::fromUpstreamBuildEntity
        );

        filters.ifPresent(fs -> {
            String filter = String.join("*", fs) + "*";

            for (SddcType sddc : SddcType.values()) {
                String s = Utils.extractFilter(filter, sddc.value(), "");
                if (s.equals("")) continue;

                Status status = Status.findByValue(s);

                if (status.equals(Status.MISSING)) {
                    out.removeIf(u -> u.hasResult(sddc));
                } else {
                    out.removeIf(u -> !(u.hasResult(sddc)
                            && u.getResult(sddc).getStatus().equals(status)));
                }
            }
        });

        return out;
    }

    private List<UpstreamJobBuildEntity> getUpstreamBuildsUnformatted(SuiteType suite) {
        return getUpstreamJob(suite).getBuilds().stream().sorted(
                (b1, b2) -> -1 * Integer.compare(b1.getBuildNumber(), b2.getBuildNumber())
        ).toList();
    }

    public UpstreamInfo getUpstreamJob(int buildId) {
        UpstreamJobBuildEntity build = upstreamBuildRepository.findById(buildId).orElseThrow(() -> {
            throw new IllegalArgumentException("Failed to find Upstream Build with Id #" + buildId);
        });

        Optional<UpstreamJobBuildEntity> nextBuild = build.getUpstreamJob().getBuilds().stream()
                .sorted(Comparator.comparing(UpstreamJobBuildEntity::getBuildNumber))
                .filter(b -> b.getBuildNumber() > build.getBuildNumber())
                .findFirst();
        Optional<UpstreamJobBuildEntity> prevBuild = build.getUpstreamJob().getBuilds().stream()
                .sorted(Collections.reverseOrder(
                        Comparator.comparing(UpstreamJobBuildEntity::getBuildNumber))
                )
                .filter(b -> b.getBuildNumber() < build.getBuildNumber())
                .findFirst();

        int numPassed = testService.countByStatus(build, Status.PASSED);
        int numSkipped = testService.countByStatus(build, Status.SKIPPED);
        int numFailed = testService.countByStatus(build, Status.FAILED);

        return new UpstreamInfo(
                build.getUpstreamJob().getId(),
                build.getUpstreamJob().getName(),
                build.getUpstreamJob().getUrl().toString(),
                build.getUrl().toString(),
                build.getId(),
                build.getBuildNumber(),
                build.getOb(),
                build.getStatus().value(),
                new Date(build.getBuildTimestamp()),
                numPassed,
                numSkipped,
                numFailed,
                nextBuild.map(UpstreamJobBuildEntity::getId),
                prevBuild.map(UpstreamJobBuildEntity::getId)
            );
    }

    public UpstreamJobBuildEntity getUpstreamBuild(int upstreamBuild) {
        return upstreamBuildRepository.findById(upstreamBuild)
                .orElseThrow(() -> {
                    throw new RuntimeException("Failed to find Upstream Build with ID #"
                            + upstreamBuild);
                });
    }

    public UpstreamJobEntity getUpstreamJob(SuiteType suite) {
        return upstreamJobRepository.findFirstBySuite(suite).orElseThrow(() -> {
            throw new RuntimeException("Failed to find Upstream Job with SuiteType "
                    + suite);
        });
    }

    /* HELPERS */

    private UpstreamSummary fromUpstreamBuildEntity(UpstreamJobBuildEntity build) {
        UpstreamSummary out = new AbstractUpstreamBuilder.UpstreamSummaryBuilder()
                .setJobId(build.getUpstreamJob().getId())
                .setBuildId(build.getId())
                .setBuildNumber(build.getBuildNumber())
                .setOb(build.getOb())
                .setBuildTimestamp(build.getBuildTimestamp())
                .setStatus(build.getStatus().value())
                .build();

        build.getTriggeredJobs().forEach(j ->
                out.addResult(j.getJob().getSddc(), fromJobBuildEntity(j)));

        return out;
    }

    private static RunSummary fromJobBuildEntity(JobBuildEntity build) {
        RunSummary out = new RunSummary();

        if (build.getFailedCount() > 0) {
            out.setStatus(Status.FAILED);
            out.setAmount(build.getFailedCount());
        } else if (build.getSkippedCount() > 0) {
            out.setStatus(Status.SKIPPED);
            out.setAmount(build.getSkippedCount());
        } else {
            out.setStatus(Status.PASSED);
        }

        return out;
    }

    public Map<SddcType, BuildSummary> getBuildSummary(int id) {
        Map<SddcType, BuildSummary> out = new HashMap<>();

        upstreamBuildRepository.findById(id).orElseThrow().getTriggeredJobs().forEach(b -> out.put(
                b.getJob().getSddc(),
                new BuildSummary(
                        testService.countByStatus(b, Status.PASSED),
                        testService.countByStatus(b, Status.SKIPPED),
                        testService.countByStatus(b, Status.FAILED)
                )
        ));

        return out;
    }

}
