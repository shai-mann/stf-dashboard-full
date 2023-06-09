import {Component, OnDestroy} from '@angular/core';
import {BuildRepository} from "../../repositories/upstream/build.repository";
import {Observable, Subscription} from "rxjs";
import {ApplicationRepository} from "../../repositories/application/application.repository";
import {UpstreamSummaryModel} from "../../models/upstream-summary.model";
import {HeatmapRendererConfig, Status} from "../../components/renderers/heatmap-renderer/heatmap-renderer";
import {LinkedCellRendererConfig} from "../../components/renderers/linked-cell-renderer/linked-cell-renderer";
import {GridDataFetchResult, GridState} from "@vcd/ui-components";
import {Router} from "@angular/router";
import {TESTS, UPSTREAM} from "../../app-routing.module";
import {formatDate} from "@angular/common";
import {DATE_FORMAT, DATE_LOCALE, TIMEZONE} from "../../utils";
import {WeatherIconShape} from "../../components/weather-icon/weather-icon.component";

export enum BuildStatus {
  ABORTED = "ABORTED",
  FAILURE = "FAILURE",
  SUCCESS = "SUCCESS",
  UNSTABLE = "UNSTABLE",
}

@Component({
  selector: 'app-upstream-job-runs',
  templateUrl: './upstream-job-runs.component.html',
  styleUrls: ['./upstream-job-runs.component.scss']
})
export class UpstreamJobRunsComponent implements OnDestroy {

  public static CORNER_TITLE: string = "Build # (ob)";

  /**
   * Indicates the number of recent builds to take into account when calculating
   * the weather icon.
   * @private
   */
  private static NUM_BUILDS_WEATHER = 10;

  /**
   * Handles mapping the status of a build to it's (unweighted) value in the weather calculation.
   * @private
   */
  private static BUILD_RESULT_MAP: Map<string, number> = new Map([
    [BuildStatus.FAILURE, 0],
    [BuildStatus.SUCCESS, 1],
    [BuildStatus.UNSTABLE, 0.75]
  ]);

  /**
   * Thresholds for the weighted average calculation for weather icons.
   * @private
   */
  private static SUNNY_THRESHOLD = 0.85;
  private static PARTLY_CLOUDY_THRESHOLD = 0.75;
  private static CLOUDY_THRESHOLD = 0.6;
  private static RAIN_THRESHOLD = 0.4;

  private static calculateWeight(index: number): number {
    let x = index / 10;
    return 1 - (x === 0 ? 0 : Math.pow(2, 10 * x - 10));
  }

  cornerTitle = UpstreamJobRunsComponent.CORNER_TITLE;

  gridData: Map<String, GridDataFetchResult<UpstreamSummaryModel>> = new Map();

  private subscription = new Subscription();

  constructor(private router: Router,
              private buildRepository: BuildRepository,
              private applicationRepository: ApplicationRepository) {}

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  get suiteTypes$(): Observable<String[]> {
    return this.applicationRepository.getSuiteTypes$();
  }

  fetchGridData(gridState: GridState<UpstreamSummaryModel>, suite: String) {
    this.subscription.add(this.buildRepository.getUpstreamJobs$(
      suite,
      gridState.filters ? gridState.filters : null,
    ).subscribe(data => {
      this.gridData.set(suite, {
        items: data
      });
    }));
  }

  linkRenderer(model: UpstreamSummaryModel): LinkedCellRendererConfig {
    return {
      text: `${model.buildNumber} (${model.build})`,
      link: `${UPSTREAM}/${model.buildId}`,
      detail: formatDate(model.buildTimestamp, DATE_FORMAT, DATE_LOCALE, TIMEZONE),
    };
  }

  heatMapRenderer(model: UpstreamSummaryModel, sddc: string): HeatmapRendererConfig {
    let text;
    let result = model.results[sddc];

    switch (result?.status || "") {
      case Status.PASSED:
        text = "All Passed!";
        break;
      case Status.FAILED:
        text = `${result.amount} Failed`;
        break;
      case Status.SKIPPED:
        text = `${result.amount} Skipped`;
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

  getGridData(suite: string): GridDataFetchResult<UpstreamSummaryModel> {
    return this.gridData.get(suite) || { items: [] };
  }

  navigateToTestBreakdown() {
    this.router.navigateByUrl(TESTS);
  }

  weatherIcon(suite: string): WeatherIconShape {
    let data: UpstreamSummaryModel[] = this.getGridData(suite).items.slice(0, UpstreamJobRunsComponent.NUM_BUILDS_WEATHER);

    let avg = data.reduce((prev, cur, i, arr) => {
      if (cur.status === BuildStatus.ABORTED) return;

      let weight = UpstreamJobRunsComponent.calculateWeight(i);

      return prev + UpstreamJobRunsComponent.BUILD_RESULT_MAP.get(cur.status) * weight;
    }, 0);

    avg /= data.length;

    if (avg >= UpstreamJobRunsComponent.SUNNY_THRESHOLD) {
      return WeatherIconShape.SUNNY;
    } else if (avg >= UpstreamJobRunsComponent.PARTLY_CLOUDY_THRESHOLD) {
      return WeatherIconShape.PARTLY_CLOUDY;
    } else if (avg >= UpstreamJobRunsComponent.CLOUDY_THRESHOLD) {
      return WeatherIconShape.CLOUDY;
    } else if (avg >= UpstreamJobRunsComponent.RAIN_THRESHOLD) {
      return WeatherIconShape.RAINY;
    } else {
      return WeatherIconShape.POURING;
    }
  }

}
