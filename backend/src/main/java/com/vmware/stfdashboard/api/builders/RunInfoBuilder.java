package com.vmware.stfdashboard.api.builders;

import com.vmware.stfdashboard.api.meta.RunInfo;
import com.vmware.stfdashboard.util.Status;

import java.util.Date;

/**
 * A builder for the {@link RunInfo} class.
 */
public class RunInfoBuilder {

    private Status status;

    private double duration;

    private Date startedAt;

    private String exception;

    private int upstreamBuildId;

    private int buildNumber;

    private String build;

    public RunInfo build() {
        return new RunInfo(
                status, duration, startedAt, exception, upstreamBuildId, buildNumber, build
        );
    }

    public RunInfoBuilder setStatus(Status status) {
        this.status = status;
        return this;
    }

    public RunInfoBuilder setDuration(double duration) {
        this.duration = duration;
        return this;
    }

    public RunInfoBuilder setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
        return this;
    }

    public RunInfoBuilder setException(String exception) {
        this.exception = exception;
        return this;
    }

    public RunInfoBuilder setUpstreamBuildId(int upstreamBuildId) {
        this.upstreamBuildId = upstreamBuildId;
        return this;
    }

    public RunInfoBuilder setBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
        return this;
    }

    public RunInfoBuilder setBuild(String build) {
        this.build = build;
        return this;
    }

}
