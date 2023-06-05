import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SpecificUpstreamRunComponent} from './specific-upstream-run.component';

describe('SpecificUpstreamRunComponent', () => {
  let component: SpecificUpstreamRunComponent;
  let fixture: ComponentFixture<SpecificUpstreamRunComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SpecificUpstreamRunComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpecificUpstreamRunComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
