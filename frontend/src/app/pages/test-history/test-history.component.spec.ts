import {ComponentFixture, TestBed} from '@angular/core/testing';

import {TestHistoryComponent} from './test-history.component';

describe('TestHistoryComponent', () => {
  let component: TestHistoryComponent;
  let fixture: ComponentFixture<TestHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestHistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TestHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
