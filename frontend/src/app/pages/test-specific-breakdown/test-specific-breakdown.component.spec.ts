import {ComponentFixture, TestBed} from '@angular/core/testing';

import {TestSpecificBreakdownComponent} from './test-specific-breakdown.component';

describe('TestSpecificBreakdownComponent', () => {
  let component: TestSpecificBreakdownComponent;
  let fixture: ComponentFixture<TestSpecificBreakdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestSpecificBreakdownComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TestSpecificBreakdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
