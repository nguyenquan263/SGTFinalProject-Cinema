import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FoodmodalComponent } from './foodmodal.component';

describe('FoodmodalComponent', () => {
  let component: FoodmodalComponent;
  let fixture: ComponentFixture<FoodmodalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FoodmodalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FoodmodalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
