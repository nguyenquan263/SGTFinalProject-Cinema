import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
declare var $: any;
@Injectable()
export class MovieshowService {

  constructor(private http: Http) { }

  getMovieShows() {
    return this.http.get('http://localhost:8080/QuanPhongGroup/getAllMovieShowsREST')
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }

  getMovieShowsafterToday(){
    return this.http.get('http://localhost:8080/QuanPhongGroup/getAllMovieShowsafterTodayREST')
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }
 
  getTicketsbyMS(msid: number) {
    return this.http.get('http://localhost:8080/QuanPhongGroup/getTicketsRESTbyMovieShowID/'+msid)
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }

  addMovieShow(movieShow: any){
    var ok = true;
    
    
    var bt = movieShow.beginTime.trim();
    var et = movieShow.endTime.trim();
    var co = movieShow.cost.trim();
    if (bt === '') ok = false;
    if (et === '') ok = false;
    if (co === '') ok = false;

    if (isNaN(co)) ok = false;
    else {
      if (parseInt(co) < 0) ok = false;
    }

    //   alert(ok);
    if (ok === true) {
      let newMovieShow = {
        movieID: movieShow.movieID.trim(),
        roomID: movieShow.roomID.trim(),
        dateShow: movieShow.dateShow.trim(),
        beginTime: bt,
        endTime: et,
        showType: movieShow.showType.trim(),
        cost: co,
        status: '0'
      };

      console.log(newMovieShow);
      $.ajax({
        url: 'http://localhost:8080/QuanPhongGroup/addMovieShowREST',
        type: 'POST',
        dataType: 'html',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: newMovieShow,
        success: function (result) {
          alert('Add successfully!');
          setTimeout(() => {
            location.reload();
          }, 2000);
        },
        error: function (result) {
          alert('Something wrong when adding!!');
        }
      });
    } else alert('Please fill all necessary field or check the format of each field');
  }

  updateMovieShow(movieShow: any) {
    var ok = true;
    var bt = movieShow.beginTime.trim();
    var et = movieShow.endTime.trim();
    var co = movieShow.cost.trim();
    if (bt === '') ok = false;
    if (et === '') ok = false;
    if (co === '') ok = false;

    if (isNaN(co)) ok = false;
    else {
      if (parseInt(co) < 0) ok = false;
    }
    if (ok === true) {
      let updateMovieShow = {
        id: movieShow.id,
        movieID: movieShow.movieID.trim(),
        roomID: movieShow.roomID.trim(),
        dateShow: movieShow.dateShow.trim(),
        beginTime: bt,
        endTime: et,
        showType: movieShow.showType.trim(),
        cost: co,
        status: movieShow.status
      };
      console.log(updateMovieShow);
      $.ajax({
        url: 'http://localhost:8080/QuanPhongGroup/updateMovieShowREST',
        type: 'POST',
        dataType: 'html',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: updateMovieShow,
        success: function (result) {
          alert('Update successfully!');
          setTimeout(() => {
            location.reload();
          }, 2000);
        },
        error: function (result) {
          alert('SOMETHING WRONG WHILE UPDATING!!');
        }
      });

    } else alert('Please fill all necessary field or check the format of each field');
  }

  deleteMovieShow(id: number) {
    this.http.get('http://localhost:8080/QuanPhongGroup/deleteMovieShowREST/'+id).toPromise()
    .then(function (response) {
        console.log('get',response)
        setTimeout(() => {
          location.reload();
        }, 800);
    })
    .catch(function (data) {
     alert('You cannot delete this movie show right now')
    });
  }
}
