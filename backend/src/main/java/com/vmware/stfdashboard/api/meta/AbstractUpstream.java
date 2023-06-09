package com.vmware.stfdashboard.api.meta;

import com.vmware.stfdashboard.util.SddcType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.HashMap;
import java.util.Map;

/**
 * An API model containing information about the runs of a single
 * {@link com.vmware.stfdashboard.models.processed.UpstreamJobEntity}.
 * @param <T> the data type to summarize a single run of a triggered job by the upstream.
 * @see RunInfo
 * @see RunSummary
 * @see AbstractTest
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class AbstractUpstream<T> {

    private Integer jobId, buildId, buildNumber;
    private Long buildTimestamp;
    private String build, status;

    private Map<SddcType, T> results = new HashMap<>();

    public AbstractUpstream(int jobId, int buildId, int buildNumber,
                            String build, long buildTimestamp, String status) {
        this.jobId = jobId;
        this.buildId = buildId;
        this.buildNumber = buildNumber;
        this.build = build;
        this.buildTimestamp = buildTimestamp;
        this.status = status;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public void setBuildId(int buildId) {
        this.buildId = buildId;
    }

    public void setBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public void addResult(SddcType sddc, T result) {
        this.results.put(sddc, result);
    }

    public boolean hasResult(SddcType sddc) {
        return this.results.containsKey(sddc);
    }

    public T getResult(SddcType sddc) {
        return this.results.get(sddc);
    }

}
