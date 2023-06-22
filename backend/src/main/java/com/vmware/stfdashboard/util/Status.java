package com.vmware.stfdashboard.util;

import com.vmware.stfdashboard.models.generated.JenkinsTestResult;
import com.vmware.stfdashboard.models.processed.JobBuildEntity;
import com.vmware.stfdashboard.models.processed.TestResultEntity;

/**
 * An enum containing Status types supported by this project. The status types are divided
 * between the statuses available for {@link TestResultEntity} (originally sourced from
 * {@link JenkinsTestResult} objects), and statuses available for {@link JobBuildEntity}.
 *
 * <p>Both are contained in the same enum, but not for any particular design reason.</p>
 */
public enum Status {
    /* Test Result Status Options */
    FAILED("FAIL"),
    PASSED("PASS"),
    SKIPPED("SKIP"),
    MISSING("MISSING"),

    /* Job Build Status Options */
    ABORTED("ABORTED"),
    FAILURE("FAILURE"),
    SUCCESS("SUCCESS"),
    UNSTABLE("UNSTABLE");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static Status findByValue(String value) {
        for (Status s : values()) {
            if (s.value().equals(value)) return s;
        }

        throw new IllegalArgumentException("Failed to find Status for value " + value);
    }
}
