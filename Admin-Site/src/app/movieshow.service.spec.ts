import { TestBed, inject } from '@angular/core/testing';

import { MovieshowService } from './movieshow.service';

describe('MovieshowService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MovieshowService]
    });
  });

  it('should be created', inject([MovieshowService], (service: MovieshowService) => {
    expect(service).toBeTruthy();
  }));
});
