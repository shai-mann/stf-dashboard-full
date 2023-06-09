import {Component, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {
  DatagridComponent,
  DatagridSelectFilter,
  DatagridStringFilter,
  GridColumn,
  GridDataFetchResult,
  GridState,
  PaginationConfiguration,
  WildCardPosition
} from "@vcd/ui-components";
import {ApplicationRepository} from "../../repositories/application/application.repository";
import {BehaviorSubject, combineLatest, Observable, Subject, Subscription} from "rxjs";
import {HeatMapRenderer, HeatmapRendererConfig, Status} from "../renderers/heatmap-renderer/heatmap-renderer";
import {LinkedCellRenderer, LinkedCellRendererConfig} from "../renderers/linked-cell-renderer/linked-cell-renderer";

@Component({
  selector: 'app-grid',
  templateUrl: './grid.component.html',
  styleUrls: ['./grid.component.scss']
})
export class GridComponent<R> implements OnInit, OnDestroy {

  @Output()
  gridRefresh = new EventEmitter<GridState<R>>;

  @Input()
  gridData: GridDataFetchResult<R> = { items: [] };

  @Input()
  cornerTitle: string;

  @Input()
  shouldDoIdFiltering: boolean = true;

  @Input()
  shouldDoStatusFiltering: boolean = false;

  @Input()
  shouldAllowSddcSorting: boolean = true;

  @Input()
  columnTitleRenderer: (column: string) => string = c => c;

  @Input()
  idRenderer: (r: R) => LinkedCellRendererConfig;

  @Input()
  resultRenderer: (r: R, sddc: String) => HeatmapRendererConfig;

  @Input()
  columnRenderRequest = new Subject();

  gridTrackBy = (i: number, s: String) => s as string;

  columns: GridColumn<R>[] = [];
  paginationInfo: PaginationConfiguration = {
    pageSize: 25,
    pageSizeOptions: [10, 25, 50, 100],
    shouldShowPageNumberInput: true,
    shouldShowPageSizeSelector: true,
  };

  private subscription = new Subscription();

  @ViewChild(DatagridComponent, { static: false })
  public dataGrid: DatagridComponent<R>;

  constructor(private repository: ApplicationRepository) {}

  ngOnInit() {
    this.subscription.add(combineLatest(this.sddcs$, this.columnRenderRequest).subscribe(([columns, r]) => {
      this.columns = [{
        displayName: this.cornerTitle,
        renderer: LinkedCellRenderer<R>(this.idRenderer),
        queryFieldName: "name",
        filter: this.shouldDoIdFiltering ? DatagridStringFilter(WildCardPosition.END, '') : null,
        sortable: false,
      }, ...columns.map(c => {
        return {
          displayName: this.columnTitleRenderer(c as string),
          renderer: HeatMapRenderer<R>(r => this.resultRenderer(r, c)),
          queryFieldName: c as string,
          filter: this.shouldDoStatusFiltering ? DatagridSelectFilter(Object.keys(Status).map(s =>
            ({value: this.getStatusValue(s), display: s}))
          ) : null,
          sortable: this.shouldAllowSddcSorting,
        }
      })];
    }));
    this.columnRenderRequest.next(null);
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  onGridRefresh(gridState: GridState<R>): void {
    this.gridRefresh.emit(gridState);
  }

  get sddcs$(): Observable<String[]> {
    return this.repository.getSddcTypes$();
  }

  private getStatusValue(status: string) {
    switch (status) {
      case "PASSED":
        return "PASS"
      case "SKIPPED":
        return "SKIP"
      case "FAILED":
        return "FAIL"
      case "MISSING":
      default:
        return "MISSING"
    }
  }

}
