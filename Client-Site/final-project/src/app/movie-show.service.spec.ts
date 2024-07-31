import { TestBed, inject } from '@angular/core/testing';

import { MovieShowService } from './movie-show.service';

describe('MovieShowService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MovieShowService]
    });
  });

  it('should be created', inject([MovieShowService], (service: MovieShowService) => {
    expect(service).toBeTruthy();
  }));
});
