import {Observable} from "rxjs";
import {ApplicationRepository} from "./application.repository";
import {Inject, Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {API_BASE_URL} from "../../tokens";

@Injectable()
export class ApiApplicationRepository extends ApplicationRepository {

  private readonly sddcTypes$: Observable<String[]>;
  private readonly suiteTypes$: Observable<String[]>;

  constructor(private http: HttpClient, @Inject(API_BASE_URL) private apiBaseUrl: string) {
    super();

    this.sddcTypes$ = this.http.get(
      this.apiBaseUrl + "sddc-types",
      {}
    ) as Observable<String[]>;
    this.suiteTypes$ = this.http.get(
      this.apiBaseUrl + "suite-types",
      {}
    ) as Observable<String[]>;
  }

  getSddcTypes$(): Observable<String[]> {
    return this.sddcTypes$;
  }

  getSuiteTypes$(): Observable<String[]> {
    return this.suiteTypes$;
  }
}
