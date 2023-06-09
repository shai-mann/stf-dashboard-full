import {Component, OnDestroy, OnInit} from '@angular/core';
import {GridDataFetchResult, GridState} from "@vcd/ui-components";
import {Subject, Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {TestResultRepository} from "../../repositories/test-result/test-result.repository";
import {LinkedCellRendererConfig} from "../../components/renderers/linked-cell-renderer/linked-cell-renderer";
import {HeatmapRendererConfig, Status} from "../../components/renderers/heatmap-renderer/heatmap-renderer";
import {PageModel} from "../../models/meta/page.model";
import {UpstreamRunModel} from "../../models/upstream-run.model";
import {TestModel} from "../../models/test.model";
import {formatDate} from "@angular/common";
import {DATE_FORMAT, DATE_LOCALE, TIMEZONE} from "../../utils";
import {TESTS} from "../../app-routing.module";

@Component({
  selector: 'app-test-history',
  templateUrl: './test-history.component.html',
  styleUrls: ['./test-history.component.scss']
})
export class TestHistoryComponent implements OnInit, OnDestroy {

  public static CORNER_TITLE: string = "Build # (ob)";

  cornerTitle = TestHistoryComponent.CORNER_TITLE;

  testId: string;

  gridDataSubject = new Subject<PageModel<UpstreamRunModel>>();

  gridData$ = this.gridDataSubject.asObservable();

  testModelSubject = new Subject<TestModel>();

  testModel$ = this.testModelSubject.asObservable();

  readonly linkRenderer = this._linkRenderer.bind(this);

  private subscription = new Subscription();

  constructor(private router: Router,
              private route: ActivatedRoute,
              private testRepository: TestResultRepository) {}

  ngOnInit() {
    this.subscription.add(this.route.paramMap.subscribe(params => this.testId = params.get("id")));
    this.subscription.add(this.testRepository.getTest$(this.testId).subscribe(this.testModelSubject));
    this.fetchGridData({ pagination: null });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  fetchGridData(gridState: GridState<UpstreamRunModel>) {
    this.subscription.add(this.testRepository.getTestHistory$(
      this.testId,
      gridState.pagination?.pageNumber,
      gridState.pagination?.itemsPerPage,
      gridState.filters ? gridState.filters[0] : null
    ).subscribe(this.gridDataSubject));
  }

  heatMapRenderer(model: UpstreamRunModel, sddc: string): HeatmapRendererConfig {
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
      default:
        text = "No Data";
        break;
    }

    return {
      text: text,
      status: result?.status || ""
    };
  }

  getGridData(data: UpstreamRunModel[]): GridDataFetchResult<UpstreamRunModel> {
    return {
      items: data,
    };
  }

  navigateToTestBreakdown() {
    this.router.navigateByUrl(TESTS);
  }

  private _linkRenderer(model: UpstreamRunModel): LinkedCellRendererConfig {
    return {
      text: `${model.buildNumber} (${model.build})`,
      link: `${TESTS}/${this.testId}/${model.buildId}`
    };
  }

}
