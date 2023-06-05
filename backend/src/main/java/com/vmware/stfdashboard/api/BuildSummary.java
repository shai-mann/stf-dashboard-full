package com.vmware.stfdashboard.api;

import com.vmware.stfdashboard.models.processed.JobBuildEntity;

/**
 * Stores information about the results of a single {@link JobBuildEntity}
 * @param passed how many tests in this build passed.
 * @param skipped how many tests in this build were skipped.
 * @param failed how many tests in this build failed.
 */
public record BuildSummary(int passed, int skipped, int failed) {}
