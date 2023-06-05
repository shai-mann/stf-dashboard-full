import {Observable} from "rxjs";
import {TestInfoModel} from "../../models/test-info.model";
import {TestSummaryModel} from "../../models/test-summary.model";
import {UpstreamRunModel} from "../../models/upstream-run.model";
import {PageModel} from "../../models/meta/page.model";
import {TestInfoListModel} from "../../models/test-info-list.model";
import {TestModel} from "../../models/test.model";

/**
 * This repository is responsible for the management of Test results.
 */
export abstract class TestResultRepository {

  abstract getTestResults(buildId: string,
                          page?: number,
                          itemsPerPage?: number,
                          filter?: string[],
                          orderingColumn?: string,
                          reversed?: boolean): Observable<PageModel<TestInfoModel>>;

  abstract getTestSummaries(suite: string,
                            page?: number,
                            itemsPerPage?: number,
                            filter?: string): Observable<PageModel<TestSummaryModel>>;

  abstract getTestRuns(id: string,
                       buildId?: string): Observable<TestInfoListModel>;

  abstract getTestHistory(id: string,
                          page?: number,
                          itemsPerPage?: number,
                          filter?: string,
                          orderingColumn?: string,
                          reversed?: boolean): Observable<PageModel<UpstreamRunModel>>;

  abstract getTest(id: string): Observable<TestModel>;

}
