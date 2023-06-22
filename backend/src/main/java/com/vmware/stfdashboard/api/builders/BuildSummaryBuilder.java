package com.vmware.stfdashboard.api.builders;

import com.vmware.stfdashboard.api.BuildSummary;

/**
 * A builder for the {@link BuildSummary} class.
 */
public class BuildSummaryBuilder {

    private int passed, skipped, failed;

    public BuildSummary build() {
        return new BuildSummary(passed, skipped, failed);
    }

    public BuildSummaryBuilder setPassed(int passed) {
        this.passed = passed;
        return this;
    }

    public BuildSummaryBuilder setSkipped(int skipped) {
        this.skipped = skipped;
        return this;
    }

    public BuildSummaryBuilder setFailed(int failed) {
        this.failed = failed;
        return this;
    }
}
