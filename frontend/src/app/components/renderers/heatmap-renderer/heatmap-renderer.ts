import {Component, Input} from '@angular/core';
import {ColumnComponentRendererSpec, ColumnRendererSpec, ComponentRenderer} from "@vcd/ui-components";

/**
 * {@link ComponentRenderer.config} type that the {@link HeatMapRendererComponent} can understand
 */
export interface HeatmapRendererConfig {
  /**
   * Text to be displayed in a colored cell
   */
  text: string;
  /**
   * The status of the cell - mapping to the color
   */
  status: string;
}

export enum Status {
  PASSED = "PASSED",
  SKIPPED = "SKIPPED",
  FAILED = "FAILED",
  MISSING = ""
}

/**
 * A {@link ComponentRenderer} component that is used for rendering a colored cell inside a column cell template
 */
@Component({
  selector: 'app-heatmap-renderer',
  templateUrl: './heatmap-renderer.html',
  styleUrls: ['heatmap-renderer.scss']
})
export class HeatMapRendererComponent implements ComponentRenderer<HeatmapRendererConfig> {
  @Input()
  config: HeatmapRendererConfig;

  getIcon(): string {
    switch(this.config.status) {
      case Status.PASSED:
        return "success-standard"
      case Status.SKIPPED:
        return "warning-standard"
      case Status.FAILED:
        return "error-standard"
      case Status.MISSING:
      default:
        return "unknown-status"
    }
  }

  getStatus(): string {
    switch (this.config.status) {
      case Status.PASSED:
        return "success"
      case Status.SKIPPED:
        return "warning"
      case Status.FAILED:
        return "danger"
      case Status.MISSING:
      default:
        return "info"
    }
  }
}

/**
 * Creates a {@link ColumnRendererSpec} for rendering a heatmap in a column.
 */
export function HeatMapRenderer<R>(
  extractor: (record: R) => HeatmapRendererConfig
): ColumnRendererSpec<R, HeatmapRendererConfig> {
  return ColumnComponentRendererSpec({
    type: HeatMapRendererComponent,
    config(record): HeatmapRendererConfig {
      return extractor(record) as HeatmapRendererConfig;
    },
  });
}
