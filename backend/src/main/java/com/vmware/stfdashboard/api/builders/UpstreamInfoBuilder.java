package com.vmware.stfdashboard.api.builders;

import com.vmware.stfdashboard.api.UpstreamInfo;

import java.util.Date;
import java.util.Optional;

/**
 * A builder for the {@link UpstreamInfo} class.
 */
public class UpstreamInfoBuilder {

    private int jobId;
    private String upstreamName, jobUrl, buildUrl;
    private int buildId, buildNumber;
    private String build, status;
    private Date buildTimestamp;
    private int numPassed, numSkipped, numFailed;
    private Optional<Integer> nextBuildId, prevBuildId; // todo investigate alternatives

    public UpstreamInfo build() {
        return new UpstreamInfo(
                jobId, upstreamName, jobUrl, buildUrl,
                buildId, buildNumber, build, status, buildTimestamp,
                numPassed, numSkipped, numFailed,
                nextBuildId, prevBuildId
        );
    }

    public UpstreamInfoBuilder setJobId(int jobId) {
        this.jobId = jobId;
        return this;
    }

    public UpstreamInfoBuilder setUpstreamName(String upstreamName) {
        this.upstreamName = upstreamName;
        return this;
    }

    public UpstreamInfoBuilder setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
        return this;
    }

    public UpstreamInfoBuilder setBuildUrl(String buildUrl) {
        this.buildUrl = buildUrl;
        return this;
    }

    public UpstreamInfoBuilder setBuildId(int buildId) {
        this.buildId = buildId;
        return this;
    }

    public UpstreamInfoBuilder setBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
        return this;
    }

    public UpstreamInfoBuilder setBuild(String build) {
        this.build = build;
        return this;
    }

    public UpstreamInfoBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public UpstreamInfoBuilder setBuildTimestamp(Date buildTimestamp) {
        this.buildTimestamp = buildTimestamp;
        return this;
    }

    public UpstreamInfoBuilder setNumPassed(int numPassed) {
        this.numPassed = numPassed;
        return this;
    }

    public UpstreamInfoBuilder setNumSkipped(int numSkipped) {
        this.numSkipped = numSkipped;
        return this;
    }

    public UpstreamInfoBuilder setNumFailed(int numFailed) {
        this.numFailed = numFailed;
        return this;
    }

    public UpstreamInfoBuilder setNextBuildId(Optional<Integer> nextBuildId) {
        this.nextBuildId = nextBuildId;
        return this;
    }

    public UpstreamInfoBuilder setPrevBuildId(Optional<Integer> prevBuildId) {
        this.prevBuildId = prevBuildId;
        return this;
    }
}
