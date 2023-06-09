package com.vmware.stfdashboard.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.Date;
import java.util.Optional;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UpstreamInfo {
    private int jobId;
    private String upstreamName, jobUrl, buildUrl;
    private int buildId, buildNumber;
    private String build, status;
    private Date buildTimestamp;
    private int numPassed, numSkipped, numFailed;
    private Optional<Integer> nextBuildId, prevBuildId;

    public UpstreamInfo(int jobId, String upstreamName, String jobUrl, String buildUrl,
                        int buildId, int buildNumber, String build, String status,
                        Date buildTimestamp, int numPassed, int numSkipped, int numFailed,
                        Optional<Integer> nextBuildId, Optional<Integer> prevBuildId) {
        this.jobId = jobId;
        this.upstreamName = upstreamName;
        this.jobUrl = jobUrl;
        this.buildUrl = buildUrl;
        this.buildId = buildId;
        this.buildNumber = buildNumber;
        this.build = build;
        this.status = status;
        this.buildTimestamp = buildTimestamp;
        this.numPassed = numPassed;
        this.numSkipped = numSkipped;
        this.numFailed = numFailed;
        this.nextBuildId = nextBuildId;
        this.prevBuildId = prevBuildId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getUpstreamName() {
        return upstreamName;
    }

    public void setUpstreamName(String upstreamName) {
        this.upstreamName = upstreamName;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public String getBuildUrl() {
        return buildUrl;
    }

    public void setBuildUrl(String buildUrl) {
        this.buildUrl = buildUrl;
    }

    public int getBuildId() {
        return buildId;
    }

    public void setBuildId(int buildId) {
        this.buildId = buildId;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBuildTimestamp() {
        return buildTimestamp;
    }

    public void setBuildTimestamp(Date buildTimestamp) {
        this.buildTimestamp = buildTimestamp;
    }

    public int getNumPassed() {
        return numPassed;
    }

    public void setNumPassed(int numPassed) {
        this.numPassed = numPassed;
    }

    public int getNumSkipped() {
        return numSkipped;
    }

    public void setNumSkipped(int numSkipped) {
        this.numSkipped = numSkipped;
    }

    public int getNumFailed() {
        return numFailed;
    }

    public void setNumFailed(int numFailed) {
        this.numFailed = numFailed;
    }

    public Optional<Integer> getNextBuildId() {
        return nextBuildId;
    }

    public void setNextBuildId(Optional<Integer> nextBuildId) {
        this.nextBuildId = nextBuildId;
    }

    public Optional<Integer> getPrevBuildId() {
        return prevBuildId;
    }

    public void setPrevBuildId(Optional<Integer> prevBuildId) {
        this.prevBuildId = prevBuildId;
    }

}
