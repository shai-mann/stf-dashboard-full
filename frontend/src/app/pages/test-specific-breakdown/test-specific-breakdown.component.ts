import {Component, OnDestroy, OnInit} from '@angular/core';
import {GridDataFetchResult, GridState} from "@vcd/ui-components";
import {BehaviorSubject, combineLatest, debounceTime, Observable, Subscription, switchMap} from "rxjs";
import {ApplicationRepository} from "../../repositories/application/application.repository";
import {LinkedCellRendererConfig} from "../../components/renderers/linked-cell-renderer/linked-cell-renderer";
import {HeatmapRendererConfig, Status} from "../../components/renderers/heatmap-renderer/heatmap-renderer";
import {TestResultRepository} from "../../repositories/test-result/test-result.repository";
import {TestSummaryModel} from "../../models/test-summary.model";
import {Router} from "@angular/router";
import {HOME, TEST_HISTORY} from "../../app-routing.module";

@Component({
  selector: 'app-test-specific-breakdown',
  templateUrl: './test-specific-breakdown.component.html',
  styleUrls: ['./test-specific-breakdown.component.scss']
})
export class TestSpecificBreakdownComponent implements OnDestroy, OnInit {

  public static CORNER_TITLE: string = "Test Name";
  public static MASTER_FILTER_TITLE: string = "master_filter";

  cornerTitle = TestSpecificBreakdownComponent.CORNER_TITLE;

  masterFilterTitle = TestSpecificBreakdownComponent.MASTER_FILTER_TITLE;

  gridData: Map<String, GridDataFetchResult<TestSummaryModel>> = new Map();

  shouldExpandAll: boolean = false;

  private subscription = new Subscription();

  private gridStateCache = new BehaviorSubject<GridState<TestSummaryModel>>({ pagination: null });

  private masterFilterObservable = new BehaviorSubject("");

  constructor(private router: Router,
              private testRepository: TestResultRepository,
              private applicationRepository: ApplicationRepository) {}

  ngOnInit() {
    this.subscription.add(combineLatest(
      this.masterFilterObservable.pipe(debounceTime(500)),
      this.suiteTypes$
    ).subscribe(([filter, suites]) => {
      suites.forEach(s => this.refreshGridData(this.gridStateCache.getValue(), s as string));
      this.shouldExpandAll = !!filter;
    }));
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  get suiteTypes$(): Observable<String[]> {
    return this.applicationRepository.getSuiteTypes$();
  }

  refreshGridData(gridState: GridState<TestSummaryModel>, suite: string) {
    return this._refreshGridData(gridState, suite);
  }

  linkRenderer(model: TestSummaryModel): LinkedCellRendererConfig {
    return {
      text: `${model.name}()`,
      link: `${TEST_HISTORY}/${model.id}`,
      tooltip: model.parameters === '[]' ? null : model.parameters
    };
  }

  heatMapRenderer(model: TestSummaryModel, sddc: string): HeatmapRendererConfig {
    let result = model.results[sddc];

    if (result.total == 0) {
      return {
        text: "No Data",
        status: Status.MISSING,
      };
    }

    return {
      text: `${result.amount}/${result.total} Passed!`,
      status: result.amount == result.total ? Status.PASSED : Status.FAILED
    };
  }

  getGridData(suite: string): GridDataFetchResult<TestSummaryModel> {
    return this.gridData.get(suite) || { items: [] };
  }

  navigateToHome() {
    this.router.navigateByUrl(HOME);
  }

  masterFilterUpdate(event) {
    this.masterFilterObservable.next(event);
  }

  private _refreshGridData(gridState: GridState<TestSummaryModel>, suite: string) {
    this.gridStateCache.next(gridState);
    this.subscription.add(this.testRepository.getTestSummaries$(
      suite,
      gridState.pagination?.pageNumber,
      gridState.pagination?.itemsPerPage,
      this.getFilters(gridState)
    ).subscribe(data => {
      this.gridData.set(suite, {
        items: data.content,
        totalItems: data.totalElements
      });
    }));
  }

  private getFilters(gridState: GridState<TestSummaryModel>): string {
    if (this.masterFilterObservable.getValue() !== "") return "name==" + this.masterFilterObservable.getValue() + "*";

    return gridState.filters ? gridState.filters[0] : null;
  }

}
