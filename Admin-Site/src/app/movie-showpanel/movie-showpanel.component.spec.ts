import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieShowpanelComponent } from './movie-showpanel.component';

describe('MovieShowpanelComponent', () => {
  let component: MovieShowpanelComponent;
  let fixture: ComponentFixture<MovieShowpanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MovieShowpanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MovieShowpanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
