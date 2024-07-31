import { Component, OnInit } from '@angular/core';
import { EmployeeService  } from '../employee.service';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [EmployeeService,CookieService]
})
export class LoginComponent implements OnInit {
  username = '';
  password = '';
  wrong = '';
  employees: any;
  EmployeeName = '';
  Employeeid = -1;
  constructor(private employeeService : EmployeeService,private router: Router, private cookies: CookieService) {
    
    this.employeeService.getEmployees()
    .then(employees => {
      this.employees = employees;
      console.log(employees);
    })
    .catch(err => console.log(err));
  }

  ngOnInit() {
  }

  check(){
    let ok = false;
    for (let emp of this.employees){
       if ((emp.userName == this.username) && (emp.password == this.password) && (emp.status === 0)) {
        this.EmployeeName = emp.firstName+' '+emp.lastName;
        this.Employeeid = emp.id;
        ok = true;
       }
        
    }

    if (ok == false) {

    this.wrong = 'Wrong password/username or your account has been locked';

    }
    else {
      this.cookies.set('loginEmployee', this.EmployeeName);
      this.cookies.set('loginEmployeeID', this.Employeeid +"");
      this.router.navigate(['./employee']);
      console.log(this.EmployeeName)
    }
  }
}
