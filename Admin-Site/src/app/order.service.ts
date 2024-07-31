import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';

declare var $: any;
@Injectable()
export class OrderService {

  constructor(private http: Http) { }

  getOrders() {
    return this.http.get('http://localhost:8080/QuanPhongGroup/getAllOrdersREST')
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }

  getOrdersNew() {
    return this.http.get('http://localhost:8080/QuanPhongGroup/getAllOrdersNewREST')
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }

  getOrdersbyTime() {
    return this.http.get('http://localhost:8080/QuanPhongGroup/getAllOrdersbyTimeREST')
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }

  getRevenue1() {
    return this.http.get('http://localhost:8080/QuanPhongGroup/getAllmovieshowRevenue')
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }

  getRevenue2() {
    return this.http.get('http://localhost:8080/QuanPhongGroup/getAllorderRevenue')
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }

  expireOrders(id:number) {
    return this.http.get('http://localhost:8080/QuanPhongGroup/expireOrder/'+id)
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }


  updateOrder(order: any) {

      let newOrder = {
        id: order.id,
        code: order.code,
        orddate: order.ordDate,
        ordtime: order.ordTime,
        empid: order.empid,
        cusid: order.cusid,
        status: order.status
      };

      $.ajax({
        url: 'http://localhost:8080/QuanPhongGroup/updateOrderREST',
        type: 'POST',
        dataType: 'html',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: newOrder,
        success: function (result) {
          alert('Update successfully!');
          setTimeout(() => {
            location.reload();
          }, 2000);
        },
        error: function (result) {
          alert('SOMETHING WRONG!!!');
        }
      });

  }

}
