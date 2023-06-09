import {Observable} from "rxjs";
import {UpstreamSummaryModel} from "../../models/upstream-summary.model";
import {BuildRepository} from "./build.repository";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Inject, Injectable} from "@angular/core";
import {API_BASE_URL} from "../../tokens";
import {UpstreamInfoModel} from "../../models/upstream-info.model";
import {Sddc} from "../../models/meta/abstract-upstream.model";
import {BuildSummaryModel} from "../../models/build-summary.model";

@Injectable()
export class ApiBuildRepository extends BuildRepository {

  constructor(private http: HttpClient, @Inject(API_BASE_URL) private apiBaseUrl: string) {
    super();
  }

  getUpstreamJobs$(suite: string, filter?: string[]): Observable<UpstreamSummaryModel[]> {
    let params = new HttpParams().set("suite", suite);

    if (filter) {
      filter.forEach(f => params = params.append("filters", f));
    }

    return this.http.get(
      this.apiBaseUrl + "builds/upstream",
      { params: params }
    ) as Observable<UpstreamSummaryModel[]>;
  }

  getUpstreamJob$(id: string): Observable<UpstreamInfoModel> {
    return this.http.get(
      this.apiBaseUrl + "builds/upstream/" + id,
      {}
    ) as Observable<UpstreamInfoModel>;
  }

  getBuildSummary$(id: string): Observable<Map<Sddc, BuildSummaryModel>> {
    return this.http.get(
      this.apiBaseUrl + "builds/summary/" + id,
      {}
    ) as Observable<Map<Sddc, BuildSummaryModel>>;
  }

}
