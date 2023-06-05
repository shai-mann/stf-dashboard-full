package com.vmware.stfdashboard.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.Date;
import java.util.Optional;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record UpstreamInfo(int jobId, String upstreamName, String jobUrl, String buildUrl,
                           int buildId, int buildNumber, long ob,
                           String status, Date buildTimestamp,
                           int numPassed, int numSkipped, int numFailed,
                           Optional<Integer> nextBuildId, Optional<Integer> prevBuildId) {}
