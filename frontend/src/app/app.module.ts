import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ClarityModule} from "@clr/angular";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {VcdComponentsModule} from "@vcd/ui-components";
import {I18nModule} from "@vcd/i18n";
import {GridComponent} from "./components/grid/grid.component";
import {HttpClientModule} from "@angular/common/http";
import {ApplicationRepository} from "./repositories/application/application.repository";
import {ApiApplicationRepository} from "./repositories/application/api-application.repository";
import {API_BASE_URL} from "./tokens";
import {UpstreamJobRunsComponent} from './pages/upstream-job-runs/upstream-job-runs.component';
import {BuildRepository} from "./repositories/upstream/build.repository";
import {ApiBuildRepository} from "./repositories/upstream/api-build.repository";
import {HeatMapRendererComponent} from "./components/renderers/heatmap-renderer/heatmap-renderer";
import {LinkedCellRendererComponent} from "./components/renderers/linked-cell-renderer/linked-cell-renderer";
import {SpecificUpstreamRunComponent} from './pages/specific-upstream-run/specific-upstream-run.component';
import {TestResultRepository} from "./repositories/test-result/test-result.repository";
import {ApiTestResultRepository} from "./repositories/test-result/api-test-result.repository";
import {SpecificTestViewComponent} from './pages/specific-test-view/specific-test-view.component';
import {TestSpecificBreakdownComponent} from './pages/test-specific-breakdown/test-specific-breakdown.component';
import {TestHistoryComponent} from './pages/test-history/test-history.component';
import {CdsModule} from "@cds/angular";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    GridComponent,
    HeatMapRendererComponent,
    LinkedCellRendererComponent,
    UpstreamJobRunsComponent,
    SpecificUpstreamRunComponent,
    SpecificTestViewComponent,
    TestSpecificBreakdownComponent,
    TestHistoryComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ClarityModule,
    CdsModule,
    BrowserAnimationsModule,
    VcdComponentsModule,
    I18nModule.forRoot(),
    FormsModule
  ],
  providers: [
    { provide: API_BASE_URL, useValue: 'http://localhost:8080/' },
    { provide: ApplicationRepository, useClass: ApiApplicationRepository },
    { provide: BuildRepository, useClass: ApiBuildRepository },
    { provide: TestResultRepository, useClass: ApiTestResultRepository },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
