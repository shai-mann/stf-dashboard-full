import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject, Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {TestResultRepository} from "../../repositories/test-result/test-result.repository";
import {formatDate, Location} from "@angular/common";
import {ApplicationRepository} from "../../repositories/application/application.repository";
import {TestInfoListModel} from "../../models/test-info-list.model";
import {DATE_FORMAT, DATE_LOCALE, DURATION_FORMAT, TIMEZONE} from "../../utils";
import {UPSTREAM} from "../../app-routing.module";

@Component({
  selector: 'app-specific-test-view',
  templateUrl: './specific-test-view.component.html',
  styleUrls: ['./specific-test-view.component.scss']
})
export class SpecificTestViewComponent implements OnInit, OnDestroy {

  testId: string;
  buildId: string;

  modelSubject = new Subject<TestInfoListModel>();

  model$ = this.modelSubject.asObservable();

  private subscription = new Subscription();

  constructor(private route: ActivatedRoute,
              private location: Location,
              private repository: ApplicationRepository,
              private testRepository: TestResultRepository) {}

  ngOnInit() {
    this.subscription.add(this.route.paramMap.subscribe(params => {
      this.testId = params.get("id");
      this.buildId = params.get("build");
      this.subscription.add(this.testRepository.getTestRuns$(this.testId, this.buildId).subscribe(data => {
        this.modelSubject.next(data);
      }));
    }));
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  get sddcTypes() {
    return this.repository.getSddcTypes$();
  }

  navigateBack() {
    this.location.back();
  }

  navigateToUpstream(upstreamBuildId: number): string {
    return `${UPSTREAM}/${upstreamBuildId}`;
  }

  formatDuration(duration: number): string {
    return formatDate(duration * 1000, DURATION_FORMAT, DATE_LOCALE, TIMEZONE);
  }

  formatStartedAt(startedAt: string): string {
    return formatDate(startedAt, DATE_FORMAT, DATE_LOCALE, TIMEZONE);
  }

  formatClassName(model: TestInfoListModel): string {
    return `${model.packagePath}.${model.className}`;
  }

}
