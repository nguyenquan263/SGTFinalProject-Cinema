import { Component, OnInit } from '@angular/core';
import { CustomerServiceService } from '../customer-service.service';
import { Http, RequestOptions } from '@angular/http';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { Cookie } from 'ng2-cookies';
import 'rxjs/add/operator/map';

declare var $: any;
@Component({
  selector: 'app-customer-panel',
  templateUrl: './customer-panel.component.html',
  styleUrls: ['./customer-panel.component.css'],
  providers: [CustomerServiceService,CookieService]
})
export class CustomerPanelComponent implements OnInit {
  customers: any;



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
  level = '';
  status = '';
  empName = '';
  //-------------------------------------------------


  constructor(private customerService: CustomerServiceService,  private cookies: CookieService, private http: Http, private router: Router) { }

  ngOnInit() {
   

    this.empName=this.cookies.get('loginEmployee');
    if(this.cookies.get('loginEmployeeID')===''){
      this.router.navigate(['./']);
    }

    $(document).ready(function () {
      $('table').DataTable();
      $('#aCusdobTF').daterangepicker({
        parentEl:'#customerAddingModal',
        singleDatePicker: true,
        showDropdowns: true,
        locale: {
          format: 'YYYY-MM-DD'
        }
      });

      $('#aCuslevelDDL').select2({
        minimumResultsForSearch: -1
      });

      //-----------------------------
      $('#cCusdobTF').daterangepicker({
        parentEl:'#customerUpdateModal',
        singleDatePicker: true,
        showDropdowns: true,
        locale: {
          format: 'YYYY-MM-DD'
        }
      });
      $('#cCuslevelDDL').select2({
        minimumResultsForSearch: -1
      });
      $('#cCusstatusDDL').select2({
        minimumResultsForSearch: -1
      });
    });

    //------------------------------------------------------------------------
    this.customerService.getCustomers()
      .then(customers => {
        this.customers = customers;
        console.log(customers);
      })
      .catch(err => console.log(err));

  }
  openAddCustomer(){
   
    this.code = '';
    this.firstName = '';
    this.lastName ='';
    this.dob = '' 
    this.address = '';
    this.phoneNumber = '';
    this.email = '';
    this.username = '';
    this.password = '';
    this.level = '';
    this.status = '' ;

  }
  addCustomer() {
    let inputCustomer = {
      code: this.code,
      firstName: this.firstName,
      lastName: this.lastName,
      dob: $('#aCusdobTF').val(),
      address: this.address,
      phoneNumber: this.phoneNumber,
      email: this.email,
      userName: this.username,
      password: this.password,
      level: $('#aCuslevelDDL :selected').text()
    };

    this.customerService.addCustomer(inputCustomer);
    

  }

  openEditcustomer(id: number) {

    //---------------------------------------------------------------------


    $('#cCusidTF').prop('disabled', true);
 


    let i: number
    let targetCustomer: any;
    for (i = 0; i < this.customers.length; i++) {
      if (id === this.customers[i].id) {
        targetCustomer = this.customers[i];
        break;
      }
    }
    //---------------------------------------------------------------------
    // alert(targetCustomer.firstName);
    $('#cCusidTF').val(targetCustomer.id);
    $('#cCuscodeTF').val(targetCustomer.code);
    $('#cCusfnameTF').val(targetCustomer.firstName);
    $('#cCuslnameTF').val(targetCustomer.lastName);
    $('#cCusdobTF').val(targetCustomer.dob);
    $('#cCusaddressTF').val(targetCustomer.address);
    $('#cCuspnumberTF').val(targetCustomer.phoneNumber);
    $('#cCusemailTF').val(targetCustomer.email);
    $('#cCususernameTF').val(targetCustomer.userName);
    $('#cCuspasswordTF').val(targetCustomer.password);
    $('#cCuslevelDDL').val(targetCustomer.level);
    $('#cCuslevelDDL').trigger('change');

    $('#cCusstatusDDL').val(targetCustomer.status);
    $('#cCusstatusDDL').trigger('change');

    this.id = targetCustomer.id;
    this.code = targetCustomer.code;
    this.firstName = targetCustomer.firstName;
    this.lastName =targetCustomer.lastName;
    this.dob = targetCustomer.dob; 
    this.address = targetCustomer.address;
    this.phoneNumber = targetCustomer.phoneNumber;
    this.email = targetCustomer.email;
    this.username = targetCustomer.userName;
    this.password = targetCustomer.password;
    this.level = targetCustomer.level;
    if(targetCustomer.status==0)
      this.status = 'Active' ;
    else
      this.status = 'Deactive'
  }

  editCustomer() {
    

      //-----------------------------------------------------
      let newCustomer = {
        id: $('#cCusidTF').val(),
        code: $('#cCuscodeTF').val(),
        firstName: $('#cCusfnameTF').val(),
        lastName: $('#cCuslnameTF').val(),
        dob: $('#aCusdobTF').val(),
        address: $('#cCusaddressTF').val(),
        phoneNumber: $('#cCuspnumberTF').val(),
        email: $('#cCusemailTF').val(),
        userName: $('#cCususernameTF').val(),
        password: $('#cCuspasswordTF').val(),
        level: $('#cCuslevelDDL :selected').text(),
        status: $('#cCusstatusDDL :selected').val()

      }

      this.customerService.updateCustomer(newCustomer);
      
      //-----------------------------------------------------

    }
  

  deleteCustomer(){
    let selectedID = $('#cCusidTF').val();
    this.customerService.deleteCustomer(selectedID);
    
  }

   signOut1(){
    this.cookies.set('loginEmployeeID', '');
    this.cookies.set('loginEmployee', '');
    this.router.navigate(['./']);
    
  }
}


