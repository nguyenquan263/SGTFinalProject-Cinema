import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
declare var $: any;
@Injectable()
export class SnackService {

  constructor(private http: Http) { }

  getSnacks() {
    return this.http.get('http://localhost:8080/QuanPhongGroup/getAllSnacksREST')
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }
  addSnack(snack: any) {
    var ok = true;
    var na = snack.name.trim();
    var co = snack.cost.trim();
    var ig = snack.imageLink.trim();
    if (na === '') ok = false;
    if (co === '') ok = false;
    if (ig === '') ok = false;

    if (isNaN(co)) ok = false;
    else {
      if (parseInt(co) < 0) ok = false;
    }


    //   alert(ok);
    if (ok === true) {
      let newSnack = {
        name: na,
        cost: co,
        imagelink : ig,
        status: 0,
      };
      $.ajax({
        url: 'http://localhost:8080/QuanPhongGroup/addSnackREST',
        type: 'POST',
        dataType: 'html',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: newSnack,
        success: function (result) {
          alert('Add successfully!');
          setTimeout(() => {
            location.reload();
          }, 2000);
        },
        error: function (result) {
          alert('Duplicate name!');
        }
      });
    } else alert('Please fill all necessary field or check the format of each field');

  }

  updateSnack(snack: any) {
    var ok = true;
    var na = snack.name.trim();
    var co = snack.cost.trim();
    var ig = snack.imageLink.trim();
    var st = snack.status.trim();
    if (na === '') ok = false;
    if (co === '') ok = false;
    if (ig === '') ok = false;

    if (isNaN(co)) ok = false;
    else {
      if (parseInt(co) < 0) ok = false;
    }

    //alert(ok);
    if (ok === true) {
      let newSnack = {
        id: snack.id,
        name : na,
        cost : co,
        imagelink : ig,
        status: st,
      };

      $.ajax({
        url: 'http://localhost:8080/QuanPhongGroup/updateSnackREST',
        type: 'POST',
        dataType: 'html',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: newSnack,
        success: function (result) {
          alert('Update successfully!');
          setTimeout(() => {
            location.reload();
          }, 2000);
        },
        error: function (result) {
          alert(result.toString());
        }
      });

    } else alert('Please fill all necessary field or check the format of each field');
  }

  deleteSnack(id: number) {
    return this.http.get('http://localhost:8080/QuanPhongGroup/deleteSnackREST/'+id).subscribe(data => {
      alert("Delete successfully!");
      setTimeout(() => {
        location.reload();
      }, 2000);
    });
  }
}
