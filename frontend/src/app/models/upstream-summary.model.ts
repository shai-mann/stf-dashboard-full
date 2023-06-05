import {AbstractUpstreamModel} from "./meta/abstract-upstream.model";
import {RunSummaryModel} from "./meta/run-summary.model";

/**
 * A specific extension of the AbstractUpstreamModel with a RunSummaryModel.
 */
export interface UpstreamSummaryModel extends AbstractUpstreamModel<RunSummaryModel> {}
