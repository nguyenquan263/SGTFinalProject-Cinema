import { Component, OnInit } from '@angular/core';
import { MovieshowService } from '../movieshow.service';
import { MovieService } from '../movie.service';
import { Http, RequestOptions } from '@angular/http';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { ViewEncapsulation } from '@angular/core';
import 'rxjs/add/operator/map';
declare var $: any;
@Component({
  selector: 'app-movie-showpanel',
  templateUrl: './movie-showpanel.component.html',
  styleUrls: ['./movie-showpanel.component.css'],
  encapsulation: ViewEncapsulation.None,
  providers: [MovieshowService, CookieService, MovieService]
})
export class MovieShowpanelComponent implements OnInit {
  movieshows: any;
  movies: any;
  empName = '';
  sc: any;
  //------------------------------------------------------
  movieID: string = '';
  id:'';
  movieName:'';
  dateShow: string = '';
  beginTime: string = '';
  endTime: string = '';
  showRoom: string = '';
  showType: string = '';
  Cost: string = '';
  status='';

  currMSID: string;
  //------------------------------------------------------
  constructor(private movieshowService: MovieshowService, private cookies: CookieService, private http: Http, private router: Router, private movieService: MovieService) {
    this.movieshowService.getMovieShows()
      .then(movieshows => {
        this.movieshows = movieshows;
        console.log(movieshows);
      })
      .catch(err => console.log(err));

    this.movieService.getMoviesActive()
      .then(movies => {
        this.movies = movies;
        console.log(movies);
      })
      .catch(err => console.log(err));
  }

  ngOnInit() {
    this.empName = this.cookies.get('loginEmployee');
    $(document).ready(function () {
      $('table').DataTable();
    });

    $('#addMovID').select2({
      minimumResultsForSearch: -1,
      dropdownAutoWidth : true,
      width: '100%'
    });

    $('#addShowRoom').select2({
      minimumResultsForSearch: -1
    });

    $('#addShowType').select2({
      minimumResultsForSearch: -1
    });

    $('#editMovID').select2({
      minimumResultsForSearch: -1,
      dropdownAutoWidth : true,
      width: '100%'
    });

    $('#editShowRoom').select2({
      minimumResultsForSearch: -1
    });

    $('#editShowType').select2({
      minimumResultsForSearch: -1
    });

    $('#editStatus').select2({
      minimumResultsForSearch: -1
    });

    $('#addDateShow').daterangepicker({
      singleDatePicker: true,
      showDropdowns: true,
      locale: {
        format: 'YYYY-MM-DD'
      }
    });

    //----------------------------------------------------------------------
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

    });
    //----------------------------------------------------------------------
  }
  openaddMovieShow(){
    this.movieName='';
    this.beginTime='';
    this.endTime='';
    this.dateShow='';
    this.showRoom='' 
    this.Cost='';
  }
  addMovieShow() {
    let inputMovieShow = {
      movieID: $("#addMovID").val(),
      roomID: $("#addShowRoom").val(),
      dateShow: $("#addDateShow").val(),
      beginTime: this.beginTime,
      endTime: this.endTime,
      showType: $("#addShowType").val(),
      cost: this.Cost,
      status: 0
    };
    // console.log(inputMovieShow);
    this.movieshowService.addMovieShow(inputMovieShow);


  }

  openEditmovieShow(id: number) {
    
    $("#editID").prop('disabled', true);


    let i: number
    let targetMovieShow: any;
    for (i = 0; i < this.movieshows.length; i++) {
      if (id === this.movieshows[i].id) {
        targetMovieShow = this.movieshows[i];
        break;
      }
    }

    this.currMSID = id + "";
    $('#editID').val(targetMovieShow.id);
    $('#editMovID').val(targetMovieShow.movies.id);
    $('#editMovID').trigger('change');

    $('#editDateShow').val(targetMovieShow.dateShow);
    $('#editBeginTime').val(targetMovieShow.beginTime);
    $('#editEndTime').val(targetMovieShow.endTime);
    $('#editDateShow').val(targetMovieShow.dateShow);

    $('#editShowRoom').val(targetMovieShow.rooms.id);
    $('#editShowRoom').trigger('change');

    $('#editShowType').val(targetMovieShow.showType);
    $('#editShowType').trigger('change');
    $('#editCost').val(targetMovieShow.cost);
    $('#editStatus').val(targetMovieShow.status);
    $('#editStatus').trigger('change');
    this.id=targetMovieShow.id;
    this.movieName=targetMovieShow.movies.name;
    this.beginTime=targetMovieShow.beginTime;
    this.endTime=targetMovieShow.endTime;
    this.dateShow=targetMovieShow.dateShow;
    this.showRoom=targetMovieShow.rooms.id;
    
    this.showType=targetMovieShow.showType;
    this.Cost=targetMovieShow.cost;
    if(targetMovieShow.status==0)
    this.status = 'Active' ;
    else
    this.status = 'Deactive'
  }


  signOut() {
    this.cookies.set('loginEmployeeID', '');
    this.cookies.set('loginEmployee', '');
    this.router.navigate(['./']);
  }

  editMovieShow() {
    if ($('#editEditBTN').text() === 'EDIT') {
      $('input[name = "msedit"]').prop('disabled', false);
      $('select[name = "msedit"]').select2("enable");
      $('#editEditBTN').text('FINISH');
      $('#deleteEditBTN').prop('disabled', true);
    } else {
      let updatedMovieShow = {
        id: this.currMSID,
        movieID: $("#editMovID").val(),
        roomID: $("#editShowRoom").val(),
        dateShow: $("#editDateShow").val(),
        beginTime: $('#editBeginTime').val(),
        endTime: $('#editEndTime').val(),
        showType: $("#editShowType").val(),
        cost: $('#editCost').val(),
        status: $('#editStatus').val()
      };

      this.movieshowService.updateMovieShow(updatedMovieShow);


    }
  }

  deleteMovieShow() {
    let selectedID = $('#editID').val();

    this.movieshowService.deleteMovieShow(selectedID);
  }

  showMap() {
    let msid = $('#editID').val();

    
    let tickets: any;
    this.movieshowService.getTicketsbyMS(msid)
      .then(t => {
        tickets = t;
        console.log(tickets);
      })
      .catch(err => console.log(err));
    setTimeout(() => {
      let arraySeat: string[] = [];
      for (let i = 0 ; i < tickets.length;i++){
        let currTicket = tickets[i];
        let currSeat = currTicket.seats.roww+"_"+currTicket.seats.columnn;
        arraySeat.push(currSeat);
      }
      console.log(arraySeat);
      this.sc.find('selected').status('available');
      this.sc.get(arraySeat).status("selected");

    }, 2000);  
    
   
  }

}
