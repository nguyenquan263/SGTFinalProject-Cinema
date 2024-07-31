import { TestBed, inject } from '@angular/core/testing';

import { OrderDetailSnackService} from './order-detail-snack.service';

describe('OrderService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [OrderDetailSnackService]
    });
  });

  it('should be created', inject([OrderDetailSnackService], (service: OrderDetailSnackService) => {
    expect(service).toBeTruthy();
  }));
});
