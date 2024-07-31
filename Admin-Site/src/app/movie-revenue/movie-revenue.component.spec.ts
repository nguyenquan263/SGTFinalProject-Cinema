import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieRevenueComponent } from './movie-revenue.component';

describe('MovieRevenueComponent', () => {
  let component: MovieRevenueComponent;
  let fixture: ComponentFixture<MovieRevenueComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MovieRevenueComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MovieRevenueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
