import {Component, Input} from '@angular/core';
import {ColumnComponentRendererSpec, ColumnRendererSpec, ComponentRenderer} from "@vcd/ui-components";

/**
 * {@link ComponentRenderer.config} type that the {@link LinkedCellRendererComponent} can understand
 */
export interface LinkedCellRendererConfig {
  /**
   * Text to be displayed in the id cell
   */
  text: string;

  /**
   * An optional tooltip for the cell being rendered
   */
  tooltip?: string;
  /**
   * The link to redirect to on-click of this cell
   */
  link: string;

  /**
   * The subtext of this linked cell.
   */
  subtext?: string;
}

/**
 * A {@link ComponentRenderer} component that is used for rendering a colored cell inside a column cell template
 */
@Component({
  selector: 'app-linked-cell-renderer',
  templateUrl: 'linked-cell-renderer.html',
  styleUrls: ['linked-cell-renderer.scss'],
})
export class LinkedCellRendererComponent implements ComponentRenderer<LinkedCellRendererConfig> {
  @Input()
  config: LinkedCellRendererConfig;

  get shouldShowConfigTooltip() {
    return !!this.config.tooltip;
  }
}

/**
 * Creates a {@link ColumnRendererSpec} for rendering a linked-cell in a column.
 */
export function LinkedCellRenderer<R>(
  extractor: (record: R) => LinkedCellRendererConfig
): ColumnRendererSpec<R, LinkedCellRendererConfig> {
  return ColumnComponentRendererSpec({
    type: LinkedCellRendererComponent,
    config(record): LinkedCellRendererConfig {
      return extractor(record) as LinkedCellRendererConfig;
    },
  });
}
