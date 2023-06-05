/**
 * Contains information summarizing the test results of a specific (non-upstream) build.
 */
export interface BuildSummaryModel {

  passed: number;

  failed: number;

  skipped: number;

}
