import {AbstractUpstreamModel} from "./meta/abstract-upstream.model";
import {RunInfoModel} from "./meta/run-info.model";

/**
 * A specific extension of the AbstractUpstreamModel with a RunInfoModel.
 */
export interface UpstreamRunModel extends AbstractUpstreamModel<RunInfoModel> {}
