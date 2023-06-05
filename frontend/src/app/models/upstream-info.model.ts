/**
 * An API model containing information about a specific Upstream Build.
 */
export interface UpstreamInfoModel {

  jobId: number;

  upstreamName: string;

  jobUrl: string;

  buildUrl: string;

  buildId: number;

  buildNumber: number;

  ob: number;

  status: string;

  buildTimestamp: Date;

  numPassed: number;

  numSkipped: number;

  numFailed: number;

  nextBuildId?: number;

  prevBuildId?: number;

}
