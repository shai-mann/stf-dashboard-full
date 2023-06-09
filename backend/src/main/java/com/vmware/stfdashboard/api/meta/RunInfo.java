package com.vmware.stfdashboard.api.meta;

import com.vmware.stfdashboard.util.Status;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.Date;

/**
 * Stores information about a single run of a test, and the time it took to complete the run
 * of that test.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RunInfo {

    private Status status;

    private double duration;

    private Date startedAt;

    private String exception;

    private int upstreamBuildId;

    private int buildNumber;

    private String build;

    public RunInfo(Status status, double duration, Date startedAt,
                   String exception, int upstreamBuildId, int buildNumber, String build) {
        this.status = status;
        this.duration = duration;
        this.startedAt = startedAt;
        this.exception = exception;
        this.upstreamBuildId = upstreamBuildId;
        this.buildNumber = buildNumber;
        this.build = build;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setUpstreamBuildId(int upstreamBuildId) {
        this.upstreamBuildId = upstreamBuildId;
    }

    public void setBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
    }

    public void setBuild(String build) {
        this.build = build;
    }
}
