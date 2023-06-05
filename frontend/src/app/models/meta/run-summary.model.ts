/**
 * A type of result that can be stored in AbstractUpstreamModel objects or AbstractTestResultModel objects.
 */
export interface RunSummaryModel {
  status: string;

  amount: number;

  total?: number;
}
