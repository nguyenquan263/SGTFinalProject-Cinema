import { Injectable } from '@angular/core';
import { Http } from '@angular/http';


import 'rxjs/add/operator/toPromise';

declare var $: any;

@Injectable()
export class CustomerServiceService {
  constructor(private http: Http) { }

  getCustomers() {
    return this.http.get('http://localhost:8080/QuanPhongGroup/getAllCustomersREST')
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }

  addCustomer(customer: any) {
    var ok = true;
    var co = customer.code.trim();
    var fn = customer.firstName.trim();
    var ln = customer.lastName.trim();
    var ad = customer.address.trim();
    var pn = customer.phoneNumber.trim();
    var em = customer.email.trim();
    var un = customer.userName.trim();
    var pw = customer.password.trim();
    if (co === '') ok = false;
    if (fn === '') ok = false;
    if (ln === '') ok = false;
    if (pn === '') ok = false;
    if (un === '') ok = false;
    if (pw === '') ok = false;
    if (isNaN(pn)) ok = false;
    if (!this.validateEmail(em)) ok = false;
    

    //   alert(ok);
    if (ok === true) {
      let newCustomer = {
        code: co,
        firstName: fn,
        lastName: ln,
        dob: customer.dob,
        address: ad,
        phoneNumber: pn,
        email: em,
        userName: un,
        password: pw,
        level: customer.level,
        status: '0'
      };
      $.ajax({
        url: 'http://localhost:8080/QuanPhongGroup/addCustomersREST',
        type: 'POST',
        dataType: 'html',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: newCustomer,
        success: function (result) {
          alert('Add successfully!');
          setTimeout(() => {
            location.reload();
          }, 2000);
        },
        error: function (result) {
          alert('Duplicate username or code!');
        }
      });
    } else alert('Please fill all necessary field or check the format of each field');

  }

  updateCustomer(customer: any) {
    var ok = true;
    var co = customer.code.trim();
    var fn = customer.firstName.trim();
    var ln = customer.lastName.trim();
    var ad = customer.address.trim();
    var pn = customer.phoneNumber.trim();
    var em = customer.email.trim();
    var un = customer.userName.trim();
    var pw = customer.password.trim();
    if (co === '') ok = false;
 //   alert(ok);
    if (fn === '') ok = false;
  //  alert(ok);
    if (ln === '') ok = false;
   // alert(ok);
    if (pn === '') ok = false;
    //alert(ok);
    if (un === '') ok = false;
    //alert(ok);
    if (pw === '') ok = false;
    if (isNaN(pn)) ok = false;
    if (!this.validateEmail(em)) ok = false;
    //alert(ok);
    if (ok === true) {
      let newCustomer = {
        id: customer.id,
        code: co,
        firstName: fn,
        lastName: ln,
        dob: customer.dob,
        address: ad,
        phoneNumber: pn,
        email: em,
        userName: un,
        password: pw,
        level: customer.level,
        status: customer.status
      };

      $.ajax({
        url: 'http://localhost:8080/QuanPhongGroup/updateCustomerREST',
        type: 'POST',
        dataType: 'html',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: newCustomer,
        success: function (result) {
          alert('Update successfully!');
          setTimeout(() => {
            location.reload();
          }, 2000);
        },
        error: function (result) {
          alert('Duplicate username!');
        }
      });

    } else alert('Please fill all necessary field or check the format of each field');
  }

  deleteCustomer(id: number) {
    this.http.get('http://localhost:8080/QuanPhongGroup/deleteCustomerREST/'+id).toPromise()
    .then(function (response) {
        console.log('get',response)
        setTimeout(() => {
          location.reload();
        }, 800);
    })
    .catch(function (data) {
     alert('You cannot delete this customer')
    });
  }

  validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email.toLowerCase());
  }
}
