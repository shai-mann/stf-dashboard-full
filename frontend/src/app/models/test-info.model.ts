import {AbstractTestModel} from "./meta/abstract-test.model";
import {RunInfoModel} from "./meta/run-info.model";

/**
 * A specific extension of the AbstractTestModel with a RunInfoModel.
 */
export interface TestInfoModel extends AbstractTestModel<RunInfoModel> {}
