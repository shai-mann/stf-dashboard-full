package com.vmware.stfdashboard.api;

import com.vmware.stfdashboard.api.meta.AbstractUpstream;
import com.vmware.stfdashboard.api.meta.RunInfo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Essentially a marker class for this type of {@link AbstractUpstream}.
 * @see RunInfo
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UpstreamRun extends AbstractUpstream<RunInfo> {

    public UpstreamRun(int jobId, int buildId, int buildNumber,
                       long ob, long buildTimestamp, String status) {
        super(jobId, buildId, buildNumber, ob, buildTimestamp, status);
    }

}
