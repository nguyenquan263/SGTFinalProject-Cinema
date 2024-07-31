import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';

declare var $: any;
@Injectable()
export class EmployeeService {

  constructor(private http: Http) { }

  getEmployees() {
    return this.http.get('http://localhost:8080/QuanPhongGroup/getAllEmployeesREST')
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }

  addEmployee(employee: any) {
    var ok = true;
    var co = employee.code.trim();
    var fn = employee.firstName.trim();
    var ln = employee.lastName.trim();
    var ad = employee.address.trim();
    var pn = employee.phoneNumber.trim();
    var em = employee.email.trim();
    var un = employee.userName.trim();
    var pw = employee.password.trim();
    if (co === '') ok = false;
    if (fn === '') ok = false;
    if (ln === '') ok = false;
    if (pn === '') ok = false;
    if (un === '') ok = false;
    if (pw === '') ok = false;

    if (isNaN(pn)) ok = false;
    if (!this.validateEmail(em)) ok = false;

    alert(ok);
    if (ok === true) {
      let newEmployee = {
        code: co,
        firstName: fn,
        lastName: ln,
        dob: employee.dob,
        address: ad,
        phoneNumber: pn,
        email: em,
        userName: un,
        password: pw,
        role: employee.role,
        status: '0'
      };
      $.ajax({
        url: 'http://localhost:8080/QuanPhongGroup/addEmployeeREST',
        type: 'POST',
        dataType: 'html',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: newEmployee,
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

  updateEmployee(employee: any) {
    var ok = true;
    var co = employee.code.trim();
    var fn = employee.firstName.trim();
    var ln = employee.lastName.trim();
    var ad = employee.address.trim();
    var pn = employee.phoneNumber.trim();
    var em = employee.email.trim();
    var un = employee.userName.trim();
    var pw = employee.password.trim();
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
      let newEmployee = {
        id: employee.id,
        code: co,
        firstName: fn,
        lastName: ln,
        dob: employee.dob,
        address: ad,
        phoneNumber: pn,
        email: em,
        userName: un,
        password: pw,
        role : employee.role,
        level: employee.level,
        status: employee.status
      };

      $.ajax({
        url: 'http://localhost:8080/QuanPhongGroup/updateEmployeeREST',
        type: 'POST',
        dataType: 'html',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: newEmployee,
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

  // deleteEmployee(id: number) {

  //   return this.http.get('http://localhost:8080/QuanPhongGroup/deleteEmployeeREST/'+id)
  //   .subscribe(data => {
  //     alert("Delete successfully!");
  //     setTimeout(() => {
  //       location.reload();
  //     }, 2000);
  //   });
  // }
  deleteEmployee(id: number) {
    this.http.get('http://localhost:8080/QuanPhongGroup/deleteEmployeeREST/'+id).toPromise()
    .then(function (response) {
        console.log('get',response)
        setTimeout(() => {
          location.reload();
        }, 800);
    })
    .catch(function (data) {
     alert('You cannot delete this employee')
    });
  }

  validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email.toLowerCase());
  }

}
