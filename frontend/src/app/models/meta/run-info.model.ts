/**
 * A type of result that can be stored in AbstractUpstreamModel objects or AbstractTestResultModel objects.
 */
export interface RunInfoModel {
  status: string;

  duration: number;

  startedAt: Date;

  exception: string;

  upstreamBuildId: number;

  buildNumber: number;

  ob: number;
}
