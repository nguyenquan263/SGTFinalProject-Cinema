import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SnackPanelComponent } from './snack-panel.component';

describe('SnackPanelComponent', () => {
  let component: SnackPanelComponent;
  let fixture: ComponentFixture<SnackPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SnackPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SnackPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
