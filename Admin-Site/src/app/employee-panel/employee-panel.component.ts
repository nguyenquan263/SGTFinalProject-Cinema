import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../employee.service';
import { Http, RequestOptions } from '@angular/http';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import 'rxjs/add/operator/map';


declare var $: any;
@Component({
  selector: 'app-employee-panel',
  templateUrl: './employee-panel.component.html',
  styleUrls: ['./employee-panel.component.css'] ,
  providers: [EmployeeService,CookieService]
})
export class EmployeePanelComponent implements OnInit {
  employees: any;



  //-------------------------------------------------
  id = '';
  code = '';
  firstName = '';
  lastName = '';
  dob = '';
  address = '';
  phoneNumber = '';
  email = '';
  username = '';
  password = '';
  role = '';
  status = '';
  empName = '';
  //-------------------------------------------------


  constructor(private employeeService: EmployeeService, private cookies: CookieService, private http: Http, private router: Router) { }

  ngOnInit() {
    this.empName=this.cookies.get('loginEmployee');
    
    $(document).ready(function () {
      $('table').DataTable();
      $('#aEmpdobTF').daterangepicker({
        parentEl:'#employeeAddingModal',
        singleDatePicker: true,
        showDropdowns: true,
        locale: {
          format: 'YYYY-MM-DD'
        }
      });

      $('#aEmproleDDL').select2({
        minimumResultsForSearch: -1
      });

      //-----------------------------
      $('#cEmpdobTF').daterangepicker({
        parentEl:'#employeeUpdateModal',
        singleDatePicker: true,
        showDropdowns: true,
        locale: {
          format: 'YYYY-MM-DD'
        }
      });
      $('#cEmproleDDL').select2({
        minimumResultsForSearch: -1
      });
      $('#cEmpstatusDDL').select2({
        minimumResultsForSearch: -1
      });



    });

    //------------------------------------------------------------------------
    this.employeeService.getEmployees()
      .then(employees => {
        this.employees = employees;
        console.log(employees);
      })
      .catch(err => console.log(err));

  }
  openAddEmployee(){
    this.id = '';
    this.code = '';
    this.firstName = '';
    this.lastName ='';
    this.dob = '' 
    this.address = '';
    this.phoneNumber = '';
    this.email = '';
    this.username = '';
    this.password = '';
    this.role = '';
    this.status = '' ;

  }
  addEmployee() {
    let inputEmployee = {
      code: this.code,
      firstName: this.firstName,
      lastName: this.lastName,
      dob: $('#aEmpdobTF').val(),
      address: this.address,
      phoneNumber: this.phoneNumber,
      email: this.email,
      userName: this.username,
      password: this.password,
      role: $('#aEmproleDDL :selected').text()
    };

    this.employeeService.addEmployee(inputEmployee);

  }

  openEditemployee(id: number) {

    //---------------------------------------------------------------------

    $('#cEmpidTF').prop('disabled', true);
    


    let i: number
    let targetEmployee: any;
    for (i = 0; i < this.employees.length; i++) {
      if (id === this.employees[i].id) {
        targetEmployee = this.employees[i];
        break;
      }
    }
    //---------------------------------------------------------------------
    // alert(targetEmployee.firstName);
    $('#cEmpidTF').val(targetEmployee.id);
    $('#cEmpcodeTF').val(targetEmployee.code);
    $('#cEmpfnameTF').val(targetEmployee.firstName);
    $('#cEmplnameTF').val(targetEmployee.lastName);
    $('#cEmpdobTF').val(targetEmployee.dob);
    $('#cEmpaddressTF').val(targetEmployee.address);
    $('#cEmppnumberTF').val(targetEmployee.phoneNumber);
    $('#cEmpemailTF').val(targetEmployee.email);
    $('#cEmpusernameTF').val(targetEmployee.userName);
    $('#cEmppasswordTF').val(targetEmployee.password);
    
    $('#cEmproleDDL').val(targetEmployee.role);
    $('#cEmproleDDL').trigger('change');

    $('#cEmpstatusDDL').val(targetEmployee.status);
    $('#cEmpstatusDDL').trigger('change');

    this.id = targetEmployee.id;
    this.code = targetEmployee.code;
    this.firstName = targetEmployee.firstName;
    this.lastName =targetEmployee.lastName;
    this.dob = targetEmployee.dob; 
    this.address = targetEmployee.address;
    this.phoneNumber = targetEmployee.phoneNumber;
    this.email = targetEmployee.email;
    this.username = targetEmployee.userName;
    this.password = targetEmployee.password;
    this.role = targetEmployee.role;
    if(targetEmployee.status==0)
      this.status = 'Active' ;
    else
      this.status = 'Deactive'
  }

  editEmployee() {
   
    

      //-----------------------------------------------------
      let newEmployee = {
        id: $('#cEmpidTF').val(),
        code: $('#cEmpcodeTF').val(),
        firstName: $('#cEmpfnameTF').val(),
        lastName: $('#cEmplnameTF').val(),
        dob: $('#aEmpdobTF').val(),
        address: $('#cEmpaddressTF').val(),
        phoneNumber: $('#cEmppnumberTF').val(),
        email: $('#cEmpemailTF').val(),
        userName: $('#cEmpusernameTF').val(),
        password: $('#cEmppasswordTF').val(),
        role: $('#cEmproleDDL :selected').text(),
        status: $('#cEmpstatusDDL :selected').val()

      }
     
      this.employeeService.updateEmployee(newEmployee);
    
      //-----------------------------------------------------

    }
  

  deleteEmployee(){
    let selectedID = $('#cEmpidTF').val();
    this.employeeService.deleteEmployee(selectedID);
  }

  signOut(){
    this.cookies.set('loginEmployeeID', '');
    this.cookies.set('loginEmployee', '');
    this.router.navigate(['./']);
  }

  validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email.toLowerCase());
  }

}


