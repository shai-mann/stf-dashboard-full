import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SpecificTestViewComponent} from './specific-test-view.component';

describe('SpecificTestViewComponent', () => {
  let component: SpecificTestViewComponent;
  let fixture: ComponentFixture<SpecificTestViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SpecificTestViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpecificTestViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
