import { Component, OnInit } from '@angular/core';
import { ViewEncapsulation } from '@angular/core';
import { MovieService } from '../movie.service';
import { MovieshowService } from '../movie-show.service';
import { TicketService } from '../ticket.service';
import { CookieService } from 'ngx-cookie-service';
import { SeatService } from '../seat.service';
import { Http, Response, Headers } from '@angular/http';
import 'rxjs/add/operator/map';
import { Router } from '@angular/router';
declare var $: any;
@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrls: ['./ticket.component.css'],
  encapsulation: ViewEncapsulation.None,
  providers: [MovieService, MovieshowService, CookieService, TicketService, SeatService]
})
export class TicketComponent implements OnInit {
  movies: any;
  seats: any;
  currentMovieID: any;
  currentMovie: any;
  movieshows: any;
  currentMovieShow: any;
  chosenMovShowTime: string = '';
  chosenMovRoom: string = '';
  chosenMovShowCost: string = '';
  chosenMovShowType: string = '';
  chosenMovRoomID: string = '';
  chosenMovShowID: string = '';
  chosenMovDuration: string='';
  //----------------------------------------
  price = 0;
  sc: any;
  total: number = 0;
  counter: number = 0;
  cart: any;
  seat: string = '';
  //----------------------------------------
  clickedMS = false;
  clickedGC = false;
  constructor(private movieService: MovieService, private movieShowService: MovieshowService, private cookies: CookieService, private http: Http
    , private ticketService: TicketService, private seatService: SeatService, private router:Router) {
    // alert(this.cookies.get('clickedMovieID'));
    this.currentMovieID = this.cookies.get('clickedMovieID');

    this.currentMovie = this.http.get('http://localhost:8080/QuanPhongGroup/getMovieREST/' + this.cookies.get('clickedMovieID'))
      .map((res: Response) => res.json())
      .subscribe(currentMovie => {
        this.currentMovie = currentMovie;
        //console.log(this.currentMovie);
      });

    this.movieShowService.getMovieShowsbyMovieID(this.currentMovieID)
      .then(mvs => {
        this.movieshows = mvs;
        for (let i = 0 ; i < this.movieshows.length;i++)
          if (this.isOld(this.movieshows[i].dateShow))
            this.movieshows.splice(i,1);
          
        console.log(this.movieshows);
      })
      .catch(err => console.log(err));
    
    this.http.get('http://localhost:8080/QuanPhongGroup/getAllSeatsREST')
    .map((res: Response) => res.json())
    .subscribe(seat => {
      this.seats = seat;
    });


  }

  ngOnInit() {
    //alert(this.isOld("2018-12-22"));
    let signedCusID = this.cookies.get("signedCusID");
    if (signedCusID === "") {
      alert("Please Log In");
      this.router.navigate(['./movie']);
    } 

    let getPrice = this.price;
    let getTotal = this.total;
    this.sc = $('#seat-map').seatCharts({
      map: [  //Seating chart
        'aa_aaaaaaa_aa',
        'aa_aaaaaaa_aa',
        'aa_aaaaaaa_aa',
        '_____________',
        'aa_aaaaaaa_aa',
        'aa_aaaaaaa_aa',
        'aa_aaaaaaa_aa',
        'aa_aaaaaaa_aa',
        'aa_aaaaaaa_aa',
        'aa_aaaaaaa_aa',
        '_____________',
        'aa_aaaaaaa_aa',
        'aa_aaaaaaa_aa',
      ],
      naming: {
        top: false,
        getLabel: function (character, row, column) {
          return column;
        }
      },
      legend: {
        node: $('#legend'),
        items: [
          ['a', 'available', 'Available'],
          ['a', 'unavailable', 'Sold'],
          ['a', 'selected', 'Selected']
        ]
      },
      click: function () {

        if (this.status() === 'available') {
          return 'selected';
        } else if (this.status() === 'selected') { //Checked

          return 'available';
        } else if (this.status() === 'unavailable') { //sold
          alert('The seat is chosen by someone!');
          return 'unavailable';

        } else {
          return this.style();
        }
      }
    });





  }
  recalculateTotal(sc) {

    let total = 0;
    let pr = this.price;

    sc.find('selected').each(function () {

      total += pr;
    });

    return total;
  }

  recalculateCount(sc) {

    let count = 0;


    sc.find('selected').each(function () {

      count++;
    });

    return count;
  }

  recalculateSeat(sc) {

    let s = '';
    let arr = sc.find('selected').seatIds;
    for (let i = 0; i < arr.length; i++)
      s += arr[i] + ",";
    return s;
  }

  getSeatunavail(sc) {

    return sc.find('unavailable').seatIds;
  }

  getInfo(ms: any) {
    let tArr: string[] = [];
    let tickets: any;
    this.clickedMS = true;
    this.sc.find('selected').status('available');
    this.sc.find('unavailable').status('available');
    this.currentMovieShow = ms;
    this.chosenMovShowTime = ms.dateShow + " -- " + ms.beginTime
    this.chosenMovRoom = ms.rooms.name;
    this.chosenMovRoomID = ms.rooms.id;
    this.chosenMovDuration = ms.movies.duration;
    this.chosenMovShowCost = ms.cost + " VND";
    this.chosenMovShowType = ms.showType;
    this.chosenMovShowID = ms.id;
    this.price = ms.cost;

    this.http.get('http://localhost:8080/QuanPhongGroup/getTicketsRESTbyMovieShowID/' + ms.id)
      .map((res: Response) => res.json())
      .subscribe(currentMovie => {
        tickets = currentMovie;
        //console.log(tickets);
        for (let i = 0; i < tickets.length; i++) {
          let temp = tickets[i].seats.roww + "_" + tickets[i].seats.columnn;
          tArr.push(temp);
        }
        for (let i = 0; i < tArr.length; i++)
          this.sc.get(tArr[i]).status('unavailable');
      });
  }

  getCost() {
    if (this.clickedMS) {
      this.counter = this.recalculateCount(this.sc);
      this.total = this.recalculateTotal(this.sc);
      this.seat = this.recalculateSeat(this.sc);
      if (this.total > 0)
        this.clickedGC = true;
      else this.clickedGC = false;
    } else alert("Please choose a movie show and click the 'GET COST' button!!");
    //alert(this.clickedGC);
  }

  goBook() {
    if (this.clickedGC) {
      // alert(this.currentMovie.name);
      // alert(this.chosenMovRoom);
      // alert(this.chosenMovShowTime);
      // alert(this.chosenMovShowType);
      // alert(this.counter);
      // alert(this.total);
      // alert(this.seat);
      //-nhet vao cookie
      let eachPrice = this.total / this.counter;// gia cho tung ve
      //alert(eachPrice);
      this.seat = this.seat.substring(0, this.seat.length);
      let eachTicketSeat = this.seat.split(','); // list ve da chon

      //console.log(eachTicketSeatArr);
      let eachTicketPrice = [];
      let eachTicketSeatID = [];
      let eachTicketMovieShowID = [];
      for (let i = 0; i < eachTicketSeat.length - 1; i++) {
        //lay id cua seat
        let currentSeat = eachTicketSeat[i];
        let currentSeatRow = currentSeat.split("_")[0];
        let currentSeatColumn = currentSeat.split("_")[1];

        //alert("dong "+currentSeatRow+" cot "+currentSeatColumn+" phong id "+this.chosenMovRoomID);


        // TIM SEAT O DAY
        //console.log(this.seats);
       
        for (let k = 0 ; k < this.seats.length; k ++){
          let indexSeat = this.seats[k];
          let ok = true;
          if (indexSeat.rooms.id !== this.chosenMovRoomID) ok = false;
          if (indexSeat.roww != currentSeatRow) ok = false;
          if (indexSeat.columnn != currentSeatColumn) ok = false;
          if (ok){
            eachTicketSeatID.push(indexSeat.id);
            //alert(indexSeat.id);
            break;
          }
        }


        //lay id cua movie show
        //alert(this.chosenMovShowID);


        eachTicketSeat[i] = this.currentMovie.name+" "+this.chosenMovRoom + " " + this.chosenMovShowTime + " " + eachTicketSeat[i];
        eachTicketPrice.push(eachPrice);
        eachTicketMovieShowID.push(this.currentMovieShow.id);
        console.log(eachTicketSeatID);
        console.log(eachTicketSeat);
        console.log(eachTicketPrice);
        console.log(eachTicketMovieShowID);
        
      }
      //console.log(eachTicketSeatArr); 
      //console.log(eachTicketPriceArr);

      // alert(eachTicketseatID.length);

      //console.log(eachTicketMovieShowID);
      //------------------------------------------------------------
      let eachTicketString: string = "";
      let eachPriceString: string = "";
      let eachSeatIDString: string = "";
      let eachMovieShowIDString: string ="";
      for (let i = 0; i < eachTicketSeat.length - 1; i++) {
        eachTicketString += eachTicketSeat[i] + "|";
        eachPriceString += eachTicketPrice[i] + "|";
        eachSeatIDString += eachTicketSeatID[i] + "|";
        eachMovieShowIDString += eachTicketMovieShowID[i] +"|";
      }
      // alert(eachTicketString);
      // alert(eachPriceString);
      //------------------------------------------------------------

      //kiem tra hai cai array tren da ton tai hay chua ???
      let existTicketSeats = this.cookies.get('TicketSeats');
      let existTicketPrices = this.cookies.get('TicketPrices');
      let existTicketSeatIDs = this.cookies.get('TicketSeatIDs');
      let existTicketMovieShowIDs = this.cookies.get('TicketMovieShowIDs');
      if ((existTicketSeats === "") && (existTicketPrices == "")
      && (existTicketSeatIDs === "") && (existTicketMovieShowIDs === "")) {
        this.cookies.set('TicketSeats', eachTicketString);
        this.cookies.set('TicketPrices', eachPriceString);
        this.cookies.set('TicketSeatIDs', eachSeatIDString);
        this.cookies.set('TicketMovieShowIDs', eachMovieShowIDString);
      }
      else {
        this.cookies.set('TicketSeats', existTicketSeats + eachTicketString);
        this.cookies.set('TicketPrices', existTicketPrices + eachPriceString);
        this.cookies.set('TicketSeatIDs', existTicketSeatIDs + eachSeatIDString);
        this.cookies.set('TicketMovieShowIDs', existTicketMovieShowIDs + eachMovieShowIDString);
      }

      //alert(this.cookies.get('TicketSeats'));
      //alert(this.cookies.get('TicketPrices'));
      // console.log(this.cookies.get('existTicketPrices'));
      this.router.navigate(['./checkout']);
    }
    else alert("Please choose a seat!!");
  }

  isOld(dateString: string): boolean{
    let dateArr = dateString.split("-");
    let thatDate = new Date();
    thatDate.setDate(parseInt(dateArr[2]));
    thatDate.setMonth(parseInt(dateArr[1]));
    thatDate.setFullYear(parseInt(dateArr[0]));

    let currDate = new Date();

    if (thatDate < currDate) return true;
    else return false;
  }

 
}
