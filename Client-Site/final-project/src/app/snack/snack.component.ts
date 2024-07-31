import { Component, OnInit } from '@angular/core';
import { snackService } from './snack.service';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Http, Response } from '@angular/http';
declare var $: any;
@Component({
  selector: 'app-snack',
  templateUrl: './snack.component.html',
  styleUrls: ['./snack.component.css'],
  providers: [snackService, CookieService]
})
export class SnackComponent implements OnInit {
  snacks: any;
  quantity: number = 1;
  currentSnackID: number;
  currentSnack: any;
  constructor(private snackServ: snackService, private router: Router, private cookies: CookieService, private http: Http) {
    this.snackServ.getSnacks()
      .then(snacks => {
        this.snacks = snacks;
        console.log(snacks);
      })
      .catch(err => console.log(err));

  }

  ngOnInit() {

  }
  getSnack(id: number) {
    this.currentSnackID = id;


  }

  addSnack() {
    let signedCusID = this.cookies.get("signedCusID");
    if (signedCusID === "") {
      alert("Please Log In");
      this.router.navigate(['./home']);
    } else {

      let q = this.quantity;
      
      if (q>0){
      this.http.get('http://localhost:8080/QuanPhongGroup/getSnackREST/' + this.currentSnackID)
        .map((res: Response) => res.json())
        .subscribe(currentSnack => {
          this.currentSnack = currentSnack;
          console.log(this.currentSnack);

          //------------------------------------------------------------------------------
          let existSnackid = this.cookies.get('SnackIDs');
          let existSnackname = this.cookies.get('SnackNames');
          let existSnackQuantites = this.cookies.get('SnackQuantities');
          let existSnackPrices = this.cookies.get('SnackPrices');

          if ((existSnackid === "") && (existSnackname === "") && (existSnackQuantites == "") && (existSnackPrices == "")) {
            this.cookies.set('SnackIDs', currentSnack.id + "|");
            this.cookies.set('SnackNames', currentSnack.name + "|");
            this.cookies.set('SnackQuantities', q + "|");
            this.cookies.set('SnackPrices', currentSnack.cost + "|");
          }
          else {
            this.cookies.set('SnackIDs', existSnackid + currentSnack.id + "|");
            this.cookies.set('SnackNames', existSnackname + currentSnack.name + "|");
            this.cookies.set('SnackQuantities', existSnackQuantites + q + "|");
            this.cookies.set('SnackPrices', existSnackPrices + currentSnack.cost + "|");
          }
        });
      
        alert("Added that item to your cart!");
        $('#snackName').val("1");
        $('#SelectModal').modal('hide');
      } else alert("The quantity must greater than 0!");
      // alert(this.cookies.get('SnackNames'));
      // alert(this.cookies.get('SnackQuantities'));
      // alert(this.cookies.get('SnackPrices'));
    }
  }

  cancelSnack(){
    $('#snackName').val("1");
  }
}



