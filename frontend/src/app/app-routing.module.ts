import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UpstreamJobRunsComponent} from "./pages/upstream-job-runs/upstream-job-runs.component";
import {SpecificUpstreamRunComponent} from "./pages/specific-upstream-run/specific-upstream-run.component";
import {SpecificTestViewComponent} from "./pages/specific-test-view/specific-test-view.component";
import {TestSpecificBreakdownComponent} from "./pages/test-specific-breakdown/test-specific-breakdown.component";
import {TestHistoryComponent} from "./pages/test-history/test-history.component";

/**
 * Constants defining routes of the website
 */
export const HOME = ""
export const UPSTREAM = "upstream"
export const TESTS = "tests"
export const TEST_HISTORY = `${TESTS}/history`

const routes: Routes = [
  { path: HOME, component: UpstreamJobRunsComponent },
  { path: `${UPSTREAM}/:id`, component: SpecificUpstreamRunComponent },
  { path: `${TEST_HISTORY}/:id`, component: TestHistoryComponent },
  { path: `${TESTS}/:id`, component: SpecificTestViewComponent },
  { path: `${TESTS}/:id/:build`, component: SpecificTestViewComponent },
  { path: TESTS, component: TestSpecificBreakdownComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
