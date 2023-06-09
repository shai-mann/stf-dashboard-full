package com.vmware.stfdashboard.api.builders;

import com.vmware.stfdashboard.api.UpstreamRun;
import com.vmware.stfdashboard.api.UpstreamSummary;
import com.vmware.stfdashboard.api.meta.RunInfo;
import com.vmware.stfdashboard.api.meta.RunSummary;
import com.vmware.stfdashboard.util.SddcType;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractUpstreamBuilder<E, T> {

    protected Integer jobId, buildId, buildNumber;
    protected long buildTimestamp;
    protected String build, status;

    protected Map<SddcType, T> results = new HashMap<>();

    public static class UpstreamRunBuilder extends AbstractUpstreamBuilder<UpstreamRun, RunInfo> {
        @Override
        public UpstreamRun build() {
            UpstreamRun out = new UpstreamRun(
                    jobId, buildId, buildNumber, build, buildTimestamp, status
            );

            results.forEach(out::addResult);

            return out;
        }
    }

    public static class UpstreamSummaryBuilder extends AbstractUpstreamBuilder<UpstreamSummary, RunSummary> {
        @Override
        public UpstreamSummary build() {
            UpstreamSummary out = new UpstreamSummary(
                    jobId, buildId, buildNumber, build, buildTimestamp, status
            );

            results.forEach(out::addResult);

            return out;
        }
    }

    public abstract E build();

    public AbstractUpstreamBuilder<E, T> setJobId(Integer jobId) {
        this.jobId = jobId;
        return this;
    }

    public AbstractUpstreamBuilder<E, T> setBuildId(Integer buildId) {
        this.buildId = buildId;
        return this;
    }

    public AbstractUpstreamBuilder<E, T> setBuildNumber(Integer buildNumber) {
        this.buildNumber = buildNumber;
        return this;
    }

    public AbstractUpstreamBuilder<E, T> setBuild(String build) {
        this.build = build;
        return this;
    }

    public AbstractUpstreamBuilder<E, T> setBuildTimestamp(long buildTimestamp) {
        this.buildTimestamp = buildTimestamp;
        return this;
    }

    public AbstractUpstreamBuilder<E, T> setStatus(String status) {
        this.status = status;
        return this;
    }

}
