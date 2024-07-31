import { Component, OnInit } from '@angular/core';
import { ViewEncapsulation } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Http, Response } from '@angular/http';
import { Router } from '@angular/router';
declare var $: any;
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  encapsulation: ViewEncapsulation.None,
  providers: [CookieService]
})
export class HeaderComponent implements OnInit {
  cusName = "";
  signed = true;
  currCustomer: any;
  //----------------
  acode: string;
  afirstName: string;
  alastName: string;
  adob: string;
  aphoneNumber: string;
  ausername: string;
  apassword: string;
  //----------------
  userName: string;
  password: string;
  constructor(private cookies: CookieService, private http: Http, private router: Router) {
    let currCusid = this.cookies.get("signedCusID");
    if (currCusid !== "") {
      this.http.get('http://localhost:8080/QuanPhongGroup/getCustomerREST/' + currCusid)
        .map((res: Response) => res.json())
        .subscribe(customer => {
          this.currCustomer = customer;
        });

      setTimeout(() => {
        this.cusName = this.currCustomer.firstName + " " + this.currCustomer.lastName;
      }, 2000);
      this.signed = true;
    }
    else {
      this.signed = false;
    }

  }

  ngOnInit() {
    $('#aCusdobTF').daterangepicker({

      singleDatePicker: true,
      showDropdowns: true,
      locale: {
        format: 'YYYY-MM-DD'
      }
    });

    $('#uCusdobTF').daterangepicker({
      parentEl: '#customerEdittingModal',
      singleDatePicker: true,
      showDropdowns: true,
      locale: {
        format: 'YYYY-MM-DD'
      }
    });

    $('#uCuslevelDDL').select2({
      minimumResultsForSearch: -1,
      dropdownAutoWidth: true,
      width: '100%'
    });

    $('#uCusstatusDDL').select2({
      minimumResultsForSearch: -1,
      dropdownAutoWidth: true,
      width: '100%'
    });
  }

  logIn() {
    let customers: any;
    this.http.get('http://localhost:8080/QuanPhongGroup/getAllCustomersREST')
      .map((res: Response) => res.json())
      .subscribe(customer => {
        customers = customer;
      });

    setTimeout(() => {
      let ok = false;
      for (let i = 0; i < customers.length; i++) {
        let currCustomer1 = customers[i];
        if ((this.userName === currCustomer1.userName) && (this.password === currCustomer1.password) && (currCustomer1.status === 0)) {
          this.currCustomer = currCustomer1;
          ok = true;
          break;
        }
      }

      if (ok === true) {
        this.cusName = this.currCustomer.firstName + " " + this.currCustomer.lastName;
        this.cookies.set("signedCusID", this.currCustomer.id);
        //alert("Log In Successfully!!!")
        $("#loginModal").modal("hide");
        location.reload();

      } else alert("Incorrect Username or Password!!!");


    }, 2000);
  }

  logOut() {
    this.cookies.set("signedCusID", "");
    $("#logoutModal").modal("hide");
    location.reload();

  }

  addCustomer() {
    // alert(this.acode);
    // alert(this.afirstName);
    // alert(this.alastName);
    // alert(this.aphoneNumber);
    // alert($('#aCusdobTF').val());
    // alert(this.ausername);
    // alert(this.apassword);

    let ok = true;
    if (this.acode === "") ok = false;
    if (this.afirstName === "") ok = false;
    if (this.alastName === "") ok = false;
    if (this.aphoneNumber === "") ok = false;
    if ($('#aCusdobTF').val() === "") ok = false;
    if (this.ausername === "") ok = false;
    if (this.apassword === "") ok = false;

    if (ok) {
      let newCustomer = {
        code: this.acode,
        firstName: this.afirstName,
        lastName: this.alastName,
        dob: $('#aCusdobTF').val(),
        phoneNumber: this.aphoneNumber,
        userName: this.ausername,
        password: this.apassword,
        level: "STAR",
        status: "0"
      }

      $.ajax({
        url: 'http://localhost:8080/QuanPhongGroup/addCustomersREST',
        type: 'POST',
        dataType: 'html',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: newCustomer,
        success: function (result) {
          alert('Add successfully!');
          setTimeout(() => {
            $('input[name="inputCustomer"]').val("");
            $('#customerAddingModal').modal('hide');
          }, 2000);

        },
        error: function (result) {
          alert('Duplicate username or code!');

        }
      });
    } else alert("PLEASE FILL ALL NECCESSARY FIELDS!!!");


  }

  openUpdateinfo() {
    let temp = this.currCustomer;
    $('#uCusfnameTF').val(temp.firstName);
    $('#uCuslnameTF').val(temp.lastName);
    $('#uCusdobTF').val(temp.dob);
    $('#uCusaddressTF').val(temp.address);
    $('#uCuspnumberTF').val(temp.phoneNumber);
    $('#uCusemailTF').val(temp.email);
    $('#uCususernameTF').val(temp.userName);
    $('#uCuspasswordTF').val(temp.password);


  }

  finishUpdateinfo() {

    //-----------------------------------------------------
    let updateCustomer = {
      id: this.currCustomer.id,
      code: this.currCustomer.code,
      firstName: $('#uCusfnameTF').val(),
      lastName: $('#uCuslnameTF').val(),
      dob: $('#uCusdobTF').val(),
      address: $('#uCusaddressTF').val(),
      phoneNumber: $('#uCuspnumberTF').val(),
      email: $('#uCusemailTF').val(),
      userName: $('#uCususernameTF').val(),
      password: $('#uCuspasswordTF').val(),
      level: this.currCustomer.level,
      status: this.currCustomer.status

    }
    let ok = true;
    if (updateCustomer.firstName === "") ok = false;
    if (updateCustomer.lastName === "") ok = false;
    if (isNaN(updateCustomer.phoneNumber)) ok = false;

    if (ok) {
      $.ajax({
        url: 'http://localhost:8080/QuanPhongGroup/updateCustomerREST',
        type: 'POST',
        dataType: 'html',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: updateCustomer,
        success: function (result) {
          alert('Update successfully!');
          setTimeout(() => {
            $('#customerEdittingModal').modal('hide');
            location.reload();
          }, 2000);

        },
        error: function (result) {
          alert('Something wrong!');

        }
      });
    } else {
      alert("PLEASE FILL IN NECESSARY FIELD AND BE CAREFUL FOR YOUR PHONE NUMBER");
    }




  }



}
