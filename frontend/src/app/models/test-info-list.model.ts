import {AbstractTestModel} from "./meta/abstract-test.model";
import {RunInfoModel} from "./meta/run-info.model";

/**
 * A specific extension of the AbstractTestModel with a list of RunInfoModels as results.
 */
export interface TestInfoListModel extends AbstractTestModel<RunInfoModel[]> {}
