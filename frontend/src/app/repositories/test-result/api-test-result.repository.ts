import {Observable} from "rxjs";
import {TestInfoModel} from "../../models/test-info.model";
import {TestSummaryModel} from "../../models/test-summary.model";
import {UpstreamRunModel} from "../../models/upstream-run.model";
import {TestResultRepository} from "./test-result.repository";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Inject, Injectable} from "@angular/core";
import {API_BASE_URL} from "../../tokens";
import {PageModel} from "../../models/meta/page.model";
import {TestInfoListModel} from "../../models/test-info-list.model";
import {TestModel} from "../../models/test.model";

/**
 * This repository is responsible for the management of Test results.
 */
@Injectable()
export class ApiTestResultRepository extends TestResultRepository {

  constructor(private http: HttpClient, @Inject(API_BASE_URL) private apiBaseUrl: string) {
    super();
  }

  getTestResults(buildId: string,
                 page?: number,
                 itemsPerPage?: number,
                 filter?: string[],
                 orderingColumn?: string,
                 reversed?: boolean): Observable<PageModel<TestInfoModel>> {
    let params = this.optionalAppend(["buildId", "page", "itemsPerPage", "orderingColumn", "reversed"],
      [buildId, page, itemsPerPage, orderingColumn, reversed]);

    if (filter) {
      filter.forEach(f => params = params.append("filter", f));
    }

    return this.http.get(
      this.apiBaseUrl + "tests/results",
      { params: params }
    ) as Observable<PageModel<TestInfoModel>>;
  }

  getTestSummaries(suite: string,
                   page?: number,
                   itemsPerPage?: number,
                   filter?: string): Observable<PageModel<TestSummaryModel>> {
    let params = this.optionalAppend(["suite", "page", "itemsPerPage", "filter"],
      [suite, page, itemsPerPage, filter]);

    return this.http.get(
      this.apiBaseUrl + "tests/summaries",
      { params: params }
    ) as Observable<PageModel<TestSummaryModel>>;
  }

  getTestRuns(id: string,
              buildId?: string): Observable<TestInfoListModel> {
    let params = this.optionalAppend(["id", "build"], [id, buildId]);

    return this.http.get(
      this.apiBaseUrl + "tests/runs",
      { params: params }
    ) as Observable<TestInfoListModel>;
  }

  getTestHistory(id: string,
                 page?: number,
                 itemsPerPage?: number,
                 filter?: string,
                 orderingColumn?: string,
                 reversed?: boolean): Observable<PageModel<UpstreamRunModel>> {
    let params = this.optionalAppend(["id", "page", "itemsPerPage", "filter", "orderingColumn", "reversed"],
      [id, page, itemsPerPage, filter, orderingColumn, reversed]);

    return this.http.get(
      this.apiBaseUrl + "tests/history",
      { params: params }
    ) as Observable<PageModel<UpstreamRunModel>>;
  }

  getTest(id: string): Observable<TestModel> {
    return this.http.get(
      this.apiBaseUrl + "tests?id=" + id,
      {}
    ) as Observable<TestModel>;
  }

  private optionalAppend(keys: string[], values: (string | number | boolean)[]): HttpParams {
    let params = new HttpParams();
    keys.forEach((k, i) => {
      if (values[i]) { // technically this skips if a boolean value is false, but that's okay in this instance
        params = params.set(k, values[i]);
      }
    });

    return params;
  }

}
