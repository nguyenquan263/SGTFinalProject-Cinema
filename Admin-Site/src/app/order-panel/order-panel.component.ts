import { Component, OnInit } from '@angular/core';
import { OrderService } from '../order.service';
import { OrderDetailSnackService } from "../order-detail-snack.service";
import { TicketService } from '../ticket.service';
import { Http, RequestOptions } from '@angular/http';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import 'rxjs/add/operator/map';
import { MovieshowService } from '../movieshow.service';


declare var $: any;
@Component({
  selector: 'app-order-panel',
  templateUrl: './order-panel.component.html',
  styleUrls: ['./order-panel.component.css'],
  providers: [TicketService, OrderService, OrderDetailSnackService, CookieService, MovieshowService]
})
export class OrderPanelComponent implements OnInit {

  ordersNew: any[] = [];

  revenueMS: any;

  revenueOrd: any;

  resOrd: any[] = [];
  total2: number = 0;
  //-------------------------------------------------
  id = '';
  code = '';
  date = '';
  time = '';
  employeeId = '';
  customerId = '';
  employeeName = '';
  customerName = '';
  status = '';
  empName = '';
  disBTNstatus = 'DEACTIVATE';
  //-------------------------------------------------
  tickets: any;
  ticket: any[] = [];
  orders: any;
  order: any;
  movieshowes: any;
  movieshow: any[] = [];

  snackorders: any;
  snackorder: any[] = [];



  Totalticketcost = 0;
  Totalsnackcost = 0;
  //--------------------------------------------------
  msAftertoday: any;

  constructor(private ticketService: TicketService, private ordersnackService: OrderDetailSnackService, private orderService: OrderService, private movieshowService: MovieshowService, private cookies: CookieService , private http: Http, private router: Router) { }

  ngOnInit() {
    this.empName = this.cookies.get('loginEmployee');
    $(document).ready(function () {
      $('table').DataTable();

    });

    $('#cOrderstatusDDL').select2({
      minimumResultsForSearch: -1
    });

    $('#msAftertoday').select2({
      minimumResultsForSearch: -1,
      dropdownAutoWidth : true,
      width: '100%'
    });

    $('#beginDate').daterangepicker({
      singleDatePicker: true,
      showDropdowns: true,
      locale: {
        format: 'YYYY-MM-DD'
      }
    });

    $('#endDate').daterangepicker({
      singleDatePicker: true,
      showDropdowns: true,
      locale: {
        format: 'YYYY-MM-DD'
      }
    });

    //------------------------------------------------------------------------
    this.orderService.getOrdersbyTime()
      .then(orders => {
        this.orders = orders;
        console.log(orders);
      })
      .catch(err => console.log(err));

    this.orderService.getRevenue1()
      .then(rev => {
        this.revenueMS = rev;
        console.log(rev);
      })
      .catch(err => console.log(err));

    this.orderService.getRevenue2()
      .then(rev1 => {
        this.revenueOrd = rev1;
        console.log(rev1);
      })
      .catch(err => console.log(err));
    this.ticketService.getTickets()
      .then(tickets => {
        this.tickets = tickets;
      })
      .catch(err => console.log(err));

    this.ordersnackService.getsnackOrders()
      .then(snackorders => {
        this.snackorders = snackorders;
      })
      .catch(err => console.log(err));
    
    this.movieshowService.getMovieShowsafterToday()
    .then(ms => {
      this.msAftertoday = ms;
      console.log(ms);
    })
    .catch(err => console.log(err));

    this.orderService.getOrdersNew()
    .then(ms => {
      this.ordersNew = ms;
      console.log(ms);
    })
    .catch(err => console.log(err));
  }

  openEditorder(id: number) {
    let i: number
    let targetOrder: any;
    for (i = 0; i < this.orders.length; i++) {
      if (id === this.orders[i].id) {
        targetOrder = this.orders[i];
        break;
      }
    }
    //---------------------------------------------------------------------
    this.id = targetOrder.id;
    this.code = targetOrder.code;
    this.date = targetOrder.ordDate;
    this.time = targetOrder.ordTime;
    if (targetOrder.employees !== null) {
      this.employeeName = targetOrder.employees.firstName + " " + targetOrder.employees.lastName;
      this.employeeId = targetOrder.employees.id;
    }
    else {
      this.employeeName = "";
      this.employeeId = "";
    }
    this.customerName = targetOrder.customers.firstName + " " + targetOrder.customers.lastName;
    this.customerId = targetOrder.customers.id;
    $('#cOrderstatusDDL').val(targetOrder.status);
    $('#cOrderstatusDDL').trigger('change');
    //-------------------------------------------------------
    setTimeout(() => {
      let i: number;
      let j: number;
      this.ticket = [];
      this.order = '';
      this.snackorder = [];
      for (i = 0; i < this.orders.length; i++) {
        if (this.orders[i].id === id) {
          this.order = this.orders[i];
          break;
        }
      }
      for (i = 0; i < this.tickets.length; i++) {
        if (this.tickets[i].orders.id === this.order.id) {
          this.ticket.push(this.tickets[i]);
        }
      }

      for (i = 0; i < this.snackorders.length; i++) {
        if (this.snackorders[i].orders.id === this.order.id) {
          this.snackorder.push(this.snackorders[i]);
        }
      }

    }, 500);
    setTimeout(() => {
      this.Totalsnackcost = 0;
      this.Totalticketcost = 0;
      let i: number;
      for (i = 0; i < this.snackorder.length; i++) {
        this.Totalsnackcost = this.Totalsnackcost + this.snackorder[i].quantity * this.snackorder[i].cost;
      }
      for (i = 0; i < this.ticket.length; i++) {
        this.Totalticketcost = this.Totalticketcost + this.ticket[i].cost;
      }
      console.log(this.snackorders);


    }, 800);
  }


  signOut() {
    this.cookies.set('loginEmployeeID', '');
    this.cookies.set('loginEmployee', '');
    this.router.navigate(['./']);
  }

  expireOrder() {
    let chosenMSid:number = parseInt($("#msAftertoday").val());
    //alert(chosenMSid);
    // for (let i = 0; i < this.ordersNew.length; i++) 
    // {
    //   let currID = this.ordersNew[i].id;
    //   this.orderService.expireOrders(currID);
    // }

    for (let i = 0 ; i < this.tickets.length; i++){
      let currTicket = this.tickets[i];
      let relateOrder = currTicket.orders;
      let currMSID = currTicket.movieShows.id;
      let currOrdStatus = relateOrder.status;
      let currOrdID = relateOrder.id;


      if ((currMSID === chosenMSid) && (currOrdStatus === 0)){
        this.orderService.expireOrders(currOrdID);
      }
    }

    setTimeout(() => {
      alert("EXPIRE THOSE ORDERS WHICH RELATE TO THAT MOVIE SHOW COMPLETED");
      location.reload();
    }, 2000);
    

  }

  doneAction() {
    let newOrder = {
      id: this.id,
      code: this.code,
      ordDate: this.date,
      ordTime: this.time,
      empid: parseInt(this.cookies.get('loginEmployeeID')),
      cusid: this.customerId,
      status: $('#cOrderstatusDDL').val()
    }
    console.log(newOrder);
    this.orderService.updateOrder(newOrder);
  }

  getStringstatus(stt: number) {
    if (stt === 1) {
      return "PAID";
    } else if (stt === 0) {
      return "NEW";
    } else {
      return "EXPIRED";
    }
  }

  getResorder() {
    this.resOrd = [];
    this.total2 = 0;
    let beginDate: string = $('#beginDate').val();
    let endDate: string = $('#endDate').val();

    let ok = true;
    if (beginDate === "") ok = false;
    if (endDate === "") ok = false;

    if (ok === false) {
      alert("YOU MUST ENTER BEGIN DATE AND END DATE!!!")
    } else {

      let bdArr: string[] = beginDate.split("-");

      let bd: Date = new Date();

      bd.setFullYear(parseInt(bdArr[0]));
      bd.setMonth(parseInt(bdArr[1]) - 1);
      bd.setDate(parseInt(bdArr[2]));
      bd.setHours(0);
      bd.setMinutes(0);
      bd.setSeconds(0);
      // console.log(bd);

      let edArr: string[] = endDate.split("-");

      let ed: Date = new Date();

      ed.setFullYear(parseInt(edArr[0]));
      ed.setMonth(parseInt(edArr[1]) - 1);
      ed.setDate(parseInt(edArr[2]));
      ed.setHours(0);
      ed.setMinutes(0);
      ed.setSeconds(0);
      // console.log(ed);

      for (let i = 0; i < this.revenueOrd.length; i++) {
        let odArr: string[] = this.revenueOrd[i].ordDate.split("-");

        let od: Date = new Date();

        od.setFullYear(parseInt(odArr[0]));
        od.setMonth(parseInt(odArr[1]) - 1);
        od.setDate(parseInt(odArr[2]));

        if ((od <= ed) && (od >= bd)) {
          this.resOrd.push(this.revenueOrd[i]);
          this.total2 += this.revenueOrd[i].sumCost;
        }

      }

      console.log(this.resOrd);
    }
  }

}
