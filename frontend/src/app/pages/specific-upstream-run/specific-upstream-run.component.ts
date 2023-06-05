import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, NavigationStart, Router} from "@angular/router";
import {BuildRepository} from "../../repositories/upstream/build.repository";
import {TestResultRepository} from "../../repositories/test-result/test-result.repository";
import {BehaviorSubject, Observable, Subject, Subscription, tap} from "rxjs";
import {LinkedCellRendererConfig} from "../../components/renderers/linked-cell-renderer/linked-cell-renderer";
import {HeatmapRendererConfig, Status} from "../../components/renderers/heatmap-renderer/heatmap-renderer";
import {TestInfoModel} from "../../models/test-info.model";
import {GridState} from "@vcd/ui-components";
import {formatDate, Location} from "@angular/common";
import {UpstreamInfoModel} from "../../models/upstream-info.model";
import {PageModel} from '../../models/meta/page.model';
import {DATE_FORMAT, DATE_LOCALE, TIMEZONE} from "../../utils";
import {HOME, TESTS, UPSTREAM} from "../../app-routing.module";
import {Sddc} from "../../models/meta/abstract-upstream.model";
import {BuildSummaryModel} from "../../models/build-summary.model";
import {T} from "@angular/cdk/keycodes";

@Component({
  selector: 'app-specific-upstream-run',
  templateUrl: './specific-upstream-run.component.html',
  styleUrls: ['./specific-upstream-run.component.scss']
})
export class SpecificUpstreamRunComponent implements OnInit, OnDestroy {

  public static CORNER_TITLE: string = "Test Name [Parameters]";

  cornerTitle = SpecificUpstreamRunComponent.CORNER_TITLE;

  upstreamBuildId = new BehaviorSubject<string>(null);

  modelSubject = new Subject<UpstreamInfoModel>();

  model$ = this.modelSubject.asObservable();

  gridDataSubject = new Subject<PageModel<TestInfoModel>>();

  gridData$ = this.gridDataSubject.asObservable();

  buildSummariesSubject = new Subject<Map<Sddc, BuildSummaryModel>>();

  buildSummaries$ = this.buildSummariesSubject.asObservable();

  columnRenderRequest = new BehaviorSubject(0);

  private subscription = new Subscription();

  constructor(private router: Router,
              private route: ActivatedRoute,
              private location: Location,
              private buildRepository: BuildRepository,
              private testRepository: TestResultRepository) {}

  ngOnInit() {
    this.subscription.add(this.route.paramMap.subscribe(params => this.upstreamBuildId.next(params.get("id"))));
    this.subscription.add(this.upstreamBuildId.subscribe(id => {
      this.subscription.add(this.buildRepository.getUpstreamJob(id).subscribe(data => {
        this.modelSubject.next(data);
      }));
      this.subscription.add(this.buildRepository.getBuildSummary(id).subscribe(data => {
        this.buildSummariesSubject.next(data);
      }));
      this.fetchGridData({ pagination: null });
    }))
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  fetchGridData(gridState: GridState<TestInfoModel>) {
    this.subscription.add(this.testRepository.getTestResults(
      this.upstreamBuildId.getValue(),
      gridState.pagination?.pageNumber,
      gridState.pagination?.itemsPerPage,
      gridState.filters ? gridState.filters : null,
      gridState.sortColumn?.name,
      gridState.sortColumn?.reverse
    ).pipe(tap(() => this.columnRenderRequest.next(0)))
      .subscribe(data => this.gridDataSubject.next(data)));
  }

  linkRenderer(model: TestInfoModel): LinkedCellRendererConfig {
    return {
      text: model.name,
      tooltip: model.parameters === "[]" ? null : model.parameters,
      link: `${TESTS}/${model.id}`
    };
  }

  heatMapRenderer(model: TestInfoModel, sddc: string): HeatmapRendererConfig {
    let text;
    let result = model.results[sddc];

    switch (result?.status || "") {
      case Status.PASSED:
      case Status.FAILED:
        text = formatDate(result.startedAt, DATE_FORMAT, DATE_LOCALE, TIMEZONE);
        break;
      case Status.SKIPPED:
        text = "Skipped";
        break;
      case Status.MISSING:
        text = "No Data";
        break;
    }

    return {
      text: text,
      status: result?.status || ""
    };
  }

  columnTitleRenderer(buildSummaries: Map<Sddc, BuildSummaryModel>): (c: string) => string {
    return c => this._columnTitleRenderer(c, buildSummaries);
  }

  private _columnTitleRenderer(sddc: string, buildSummaries: Map<Sddc, BuildSummaryModel>): string {
    let summaries = buildSummaries[sddc as Sddc];
    if (!summaries) return sddc;

    return `${sddc} [${summaries.passed}/${summaries.skipped}/${summaries.failed}]`
  }

  formatBuildTimestamp(buildTimestamp: string): string {
    return formatDate(buildTimestamp, DATE_FORMAT, DATE_LOCALE, TIMEZONE);

  }

  getGridData(data: PageModel<TestInfoModel>) {
    return {
      items: data.content,
      totalItems: data.totalElements
    };
  }

  navigateBack() {
    this.router.navigateByUrl(HOME);
  }

  navigate(id: number) {
    console.log(id);
    this.router.navigateByUrl(`${UPSTREAM}/${id}`);
  }

  hasNext(model: UpstreamInfoModel): boolean {
    return !!model.nextBuildId;
  }

  hasPrev(model: UpstreamInfoModel): boolean {
    return !!model.prevBuildId;
  }

}
