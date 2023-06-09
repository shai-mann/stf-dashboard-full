package com.vmware.stfdashboard.api;

import com.vmware.stfdashboard.models.processed.JobBuildEntity;

/**
 * Stores information about the results of a single {@link JobBuildEntity}
 */
public class BuildSummary {
    private int passed, skipped, failed;

    public BuildSummary(int passed, int skipped, int failed) {
        this.passed = passed;
        this.skipped = skipped;
        this.failed = failed;
    }

    public int getPassed() {
        return passed;
    }

    public void setPassed(int passed) {
        this.passed = passed;
    }

    public int getSkipped() {
        return skipped;
    }

    public void setSkipped(int skipped) {
        this.skipped = skipped;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }
}
