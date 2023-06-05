import {AbstractTestModel} from "./meta/abstract-test.model";
import {RunSummaryModel} from "./meta/run-summary.model";

/**
 * A specific extension of the AbstractTestModel with a RunInfoModel.
 */
export interface TestSummaryModel extends AbstractTestModel<RunSummaryModel> {}
