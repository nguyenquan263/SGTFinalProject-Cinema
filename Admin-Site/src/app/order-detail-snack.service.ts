import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';

declare var $: any;
@Injectable()
export class OrderDetailSnackService {

  constructor(private http: Http) { }

  getsnackOrders() {
    return this.http.get('http://localhost:8080/QuanPhongGroup/getAllOrderDetailSnacksREST')
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }


}
