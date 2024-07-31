import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Http, Response } from '@angular/http';
import { ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router'
declare var $: any;
@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
  encapsulation: ViewEncapsulation.None,
  providers: [CookieService]
})
export class CheckoutComponent implements OnInit {
  orders: any;

  seatArr: string[];
  priceArr: string[];
  snackNamearr: string[];
  snackQuantityarr: string[];
  snackPricearr: string[];
  snackIDarr: string[];
  sumCost: number = 0;
  seatIDarr: string[];
  movieShowIDarr: string[];
  constructor(private cookie: CookieService, private http: Http, private router: Router) { }


  ngOnInit() {
    let signedCusID = this.cookie.get("signedCusID");
    if (signedCusID === "") {
      alert("Please Log In");
      this.router.navigate(['./home']);
    }

    let ticketSeats = this.cookie.get('TicketSeats');
    let snackNames = this.cookie.get('SnackNames');
    
    ticketSeats = ticketSeats.substring(0, ticketSeats.length - 1);

    let ticketPrices = this.cookie.get('TicketPrices');

    ticketPrices = ticketPrices.substring(0, ticketPrices.length - 1);
    //alert(ticketPrices);
    let ticketSeatIDs = this.cookie.get('TicketSeatIDs');
    ticketSeatIDs = ticketSeatIDs.substring(0, ticketSeatIDs.length - 1);
    //alert(ticketSeatIDs);
    let ticketMovieShowIDs = this.cookie.get('TicketMovieShowIDs');
    ticketMovieShowIDs = ticketMovieShowIDs.substring(0, ticketMovieShowIDs.length - 1);
    //alert(ticketMovieShowIDs);

    this.seatArr = ticketSeats.split('|');
    this.priceArr = ticketPrices.split('|'); //* can
    this.seatIDarr = ticketSeatIDs.split('|');//*can
    this.movieShowIDarr = ticketMovieShowIDs.split('|');//*can
    //console.log(this.priceArr);
    //console.log(this.seatIDarr);
    //console.log(this.movieShowIDarr);
    let snackIDs = this.cookie.get('SnackIDs');
    snackIDs = snackIDs.substring(0, snackIDs.length - 1);
    //alert(snackIDs+" la id snack");
    // let snackNames = this.cookie.get('SnackNames');
    let snackQuantities = this.cookie.get('SnackQuantities');
    snackQuantities = snackQuantities.substring(0, snackQuantities.length - 1);
    //alert(snackQuantities+ " la so luong snack");
    let snackPrices = this.cookie.get('SnackPrices');
    snackPrices = snackPrices.substring(0, snackPrices.length - 1);
    //alert(snackPrices +" la gia cua snack");


    snackNames = snackNames.substring(0, snackNames.length - 1);
    //alert(snackNames);
    this.snackIDarr = snackIDs.split('|');
    //console.log(this.snackIDarr);
    this.snackNamearr = snackNames.split('|');
    this.snackQuantityarr = snackQuantities.split('|');
    //console.log(this.snackQuantityarr);
    this.snackPricearr = snackPrices.split('|');
    //console.log(this.snackPricearr);

    if (this.seatArr[0] !== "") {
      for (let i = 0; i < this.seatArr.length; i++) {
        this.sumCost += parseInt(this.priceArr[i]);
      }
    }


    if (this.snackIDarr[0] !== "") {
      for (let i = 0; i < this.snackNamearr.length; i++) {
        this.sumCost += parseInt(this.snackQuantityarr[i]) * parseInt(this.snackPricearr[i]);
      }
    }

  }

  checkOut() {
    var ords: any;
    var future: number;
    this.http.get('http://localhost:8080/QuanPhongGroup/getAllOrdersREST')
      .map((res: Response) => res.json())
      .subscribe(order => {
        ords = order;
      });

    setTimeout(() => {


      let currentDate = new Date();
      let cusIDfromCookie = parseInt(this.cookie.get("signedCusID"));
      let newOrder = {
        code: ords[ords.length - 1].id,
        cusid: cusIDfromCookie,
        empid: "",
        status: 0,
        orddate: currentDate.getFullYear() + "-" + (currentDate.getMonth() + 1) + "-" + currentDate.getDate(),
        ordtime: currentDate.getHours() + ":" + currentDate.getMinutes() + ":" + currentDate.getSeconds()
      }



      $.ajax({
        url: 'http://localhost:8080/QuanPhongGroup/addOrderREST',
        type: 'POST',
        dataType: 'html',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: newOrder,
        success: function (result) {
          future = result;

          //alert('Add Order successful!')
        },
        error: function (result) {
          //alert('Add Order fail!');
        },

      });

    }, 2000);

    setTimeout(() => {
      for (let i = 0; i < this.priceArr.length; i++) {
        let targetOrderid = future + "";
        let targetSeatid = this.seatIDarr[i];
        let targetMShowid = this.movieShowIDarr[i];
        let targetCost = this.priceArr[i];

        let newTicket = {
          ordid: targetOrderid,
          seatid: targetSeatid,
          mshowid: targetMShowid,
          cost: targetCost,
          status: 0
        }

        $.ajax({
          url: 'http://localhost:8080/QuanPhongGroup/addTicketREST',
          type: 'POST',
          dataType: 'html',
          contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
          data: newTicket,
          success: function (result) {
            //alert('Add Ticket successfully!');
          },
          error: function (result) {
            //alert('Adding ticket Fail');
          }
        });

      }
      //----------------------------------------------------------------------------

      for (let i = 0; i < this.snackIDarr.length; i++) {
        let targetOrderid = future + "";
        let targetSnackid = this.snackIDarr[i];
        let targetSnackquantity = this.snackQuantityarr[i];
        let targetSnackprice = this.snackPricearr[i];

        let newSnack = {
          ordid: targetOrderid,
          snackid: targetSnackid,
          quantity: targetSnackquantity,
          cost: targetSnackprice,
          status: 0
        }

        $.ajax({
          url: 'http://localhost:8080/QuanPhongGroup/addOrderDetailSnackREST',
          type: 'POST',
          dataType: 'html',
          contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
          data: newSnack,
          success: function (result) {
            //alert('Add Snack successfully!');
          },
          error: function (result) {
            //alert('Adding Snack Fail');
          }
        });

      }

      
    }, 5000);


    this.cookie.set("TicketSeats", "");
    this.cookie.set("TicketSeatIDs", "");
    this.cookie.set("TicketPrices", "");
    this.cookie.set("TicketMovieShowIDs", "");
    this.cookie.set("SnackIDs", "");
    this.cookie.set("SnackNames", "");
    this.cookie.set("SnackPrices", "");
    this.cookie.set("SnackQuantities", "");
    setTimeout(() => {
      alert("YOUR ORDER HAS BEEN ADDED SUCCESSFULLY!");
    }, 6000);
    setTimeout(() => {
      this.router.navigate(['./home']);
    }, 7000);

  }

  destroy() {
    this.cookie.set("TicketSeats", "");
    this.cookie.set("TicketSeatIDs", "");
    this.cookie.set("TicketPrices", "");
    this.cookie.set("TicketMovieShowIDs", "");
    this.cookie.set("SnackIDs", "");
    this.cookie.set("SnackNames", "");
    this.cookie.set("SnackPrices", "");
    this.cookie.set("SnackQuantities", "");

    setTimeout(() => {
      this.router.navigate(['./home']);
    }, 6000);
  }

  deleteTicket(index: number) {

    this.seatIDarr[index] = "";
    this.priceArr[index] = "";
    this.seatArr[index] = "";
    this.movieShowIDarr[index] = "";

    let newSeatID: string = '';
    let newPrice: string = '';
    let newSeat: string = '';
    let newMovieshowID: string = '';

    for (let i = 0; i < this.seatIDarr.length; i++)
      if (this.seatIDarr[i] !== "") {
        newSeatID += this.seatIDarr[i] + "|";
        newPrice += this.priceArr[i] + "|";
        newSeat += this.seatArr[i] + "|";
        newMovieshowID += this.movieShowIDarr[i] + "|";

      }

    // newSeatID = newSeatID.substring(0, newSeatID.length - 1);
    // newPrice = newPrice.substring(0, newPrice.length - 1);
    // newSeat = newSeat.substring(0, newSeat.length - 1);
    // newMovieshowID = newMovieshowID.substring(0, newMovieshowID.length - 1);

    alert(newSeatID);
    alert(newPrice);
    alert(newSeat);
    alert(newMovieshowID);

    this.cookie.set("TicketSeats", newSeat);
    this.cookie.set("TicketSeatIDs", newSeatID);
    this.cookie.set("TicketPrices", newPrice);
    this.cookie.set("TicketMovieShowIDs", newMovieshowID);

  }

  deleteSnack(index: number) {

    this.snackIDarr[index] = "";
    this.snackNamearr[index] = "";
    this.snackPricearr[index] = "";
    this.snackQuantityarr[index] = "";

    let newSnackID: string = '';
    let newName: string = '';
    let newPrice: string = '';
    let newQuantity: string = '';

    for (let i = 0; i < this.snackIDarr.length; i++)
      if (this.snackIDarr[i] !== "") {
        newSnackID += this.snackIDarr[i] + "|";
        newName += this.snackNamearr[i] + "|";
        newPrice += this.snackPricearr[i] + "|";
        newQuantity += this.snackQuantityarr[i] + "|";

      }

    // newSnackID = newSnackID.substring(0, newSnackID.length - 1);
    // newName = newName.substring(0, newName.length - 1);
    // newPrice = newPrice.substring(0, newPrice.length - 1);
    // newQuantity = newQuantity.substring(0, newQuantity.length - 1);

    // alert(newSnackID);
    // alert(newName);
    // alert(newPrice);
    // alert(newQuantity);

    this.cookie.set("SnackIDs", newSnackID);
    this.cookie.set("SnackNames", newName);
    this.cookie.set("SnackPrices", newPrice);
    this.cookie.set("SnackQuantities", newQuantity);

  }


}

