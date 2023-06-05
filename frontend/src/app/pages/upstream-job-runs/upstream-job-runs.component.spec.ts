import {ComponentFixture, TestBed} from '@angular/core/testing';

import {UpstreamJobRunsComponent} from './upstream-job-runs.component';

describe('UpstreamJobRunsComponent', () => {
  let component: UpstreamJobRunsComponent;
  let fixture: ComponentFixture<UpstreamJobRunsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpstreamJobRunsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpstreamJobRunsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
