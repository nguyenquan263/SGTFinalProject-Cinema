import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';


declare var $: any;
@Injectable()
export class MovieService {

  constructor(private http: Http) { }

  getMovies() {
    return this.http.get('http://localhost:8080/QuanPhongGroup/getAllMoviesREST')
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }

  getMoviesActive() {
    return this.http.get('http://localhost:8080/QuanPhongGroup/getAllMoviesActiveREST')
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }
  addMovie(movie: any) {
    var ok = true;
    console.log(movie.category)
    var na = movie.name.trim();
    var ca = movie.category.trim();
    var dr = movie.dateReleased;
    var ms = movie.mainStory.trim();
    var du = movie.duration;
    var la = movie.language.trim();
    var st = movie.byStudio.trim();
    var ih = movie.isHot;
    var yl = movie.youtubeLink.trim();
    var il = movie.imdbLink.trim();
    var pl = movie.posterLink.trim();
    var iml = movie.imageLink1.trim();
    var ac = movie.actors.trim();
    var di = movie.directors.trim();
   // alert(dr);
    if (na === '') ok = false;
    if (ca === '') ok = false;
    if (dr === '') ok = false;
    if (ms === '') ok = false;
    if (du === '') ok = false;
    if (la === '') ok = false;
    if (st === '') ok = false;
    if (ih === '') ok = false;
    if (la === '') ok = false;
    if (yl === '') ok = false;
    if (il === '') ok = false;
    if (pl === '') ok = false;
    if (ac === '') ok = false;
    if (di === '') ok = false;

    if (isNaN(du)) ok = false;
    //   alert(ok);
    if (ok === true) {
      let newMovie = {
        name: na,
        category: ca,
        datereleased: dr,
        mainstory : ms,
        ageallow: movie.ageAllow,
        duration: du,
        bystudio: st,
        language: la,
        ishot: ih,
        youtubelink : yl,
        imdblink: il,
        posterlink: pl,
        imagelink1: iml,
        actors: ac,
        directors: di,
        status: '0'
      };
      $.ajax({
        url: 'http://localhost:8080/QuanPhongGroup/addMovieREST',
        type: 'POST',
        dataType: 'html',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: newMovie,
        success: function (result) {
          alert('Add successfully!');
          setTimeout(() => {
            location.reload();
          }, 2000);
        },
        error: function (result) {
          alert('Duplicate name!');
        }
      });
    } else alert('Please fill all necessary field or check the format of each field');
  }

  updateMovie(movie: any) {
    var ok = true;
    var na = movie.name.trim();
    var ca = movie.category.trim();
    var dr = movie.datereleased.trim();
    var ag = movie.ageallow.trim();
    var ms = movie.mainstory.trim();
    var du = movie.duration.trim();
    var la = movie.language;
    var st = movie.bystudio.trim();
    var ih = movie.ishot + "";
    var yl = movie.youtubelink.trim();
    var il = movie.imdblink;
    var pl = movie.posterlink;
    var iml = movie.imagelink1;
    var ac = movie.actors.trim();
    var di = movie.directors.trim();
    //alert(ag);
   
    if (na === '') ok = false;
    if (ca === '') ok = false;
    if (dr === '') ok = false;
    if (ms === '') ok = false;
    if (du === '') ok = false;
    if (la === '') ok = false;
    if (st === '') ok = false;
    if (ih === '') ok = false;
    if (la === '') ok = false;
    if (yl === '') ok = false;
    if (il === '') ok = false;
    if (pl === '') ok = false;
    if (ac === '') ok = false;
    if (di === '') ok = false;

    if (isNaN(du)) ok = false;

    //alert(ok);
    if (ok === true) {
      //alert(ms);
      let newMovie = {
        id: movie.id,
        name: na,
        category: ca,
        language: la,
        datereleased: dr,
        mainstory : ms,
        ageallow: movie.ageallow,
        duration: du,
        bystudio: st,
        ishot: ih,
        youtubelink : yl,
        imdblink: il,
        posterlink: pl,
        imagelink1: iml,
        actors: ac,
        directors: di,
        status: movie.status
      };

      $.ajax({
        url: 'http://localhost:8080/QuanPhongGroup/updateMovieREST',
        type: 'POST',
        dataType: 'html',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: newMovie,
        success: function (result) {
          alert('Update successfully!');
          setTimeout(() => {
            location.reload();
          }, 2000);
        },
        error: function (result) {
          alert('Update fail!');
        }
      });

    } else alert('Please fill all necessary field or check the format of each field');
  }

  deleteMovie(id: number) {
    this.http.get('http://localhost:8080/QuanPhongGroup/deleteMovieREST/'+id).toPromise()
    .then(function (response) {
        console.log('get',response)
        setTimeout(() => {
          location.reload();
        }, 800);
    })
    .catch(function (data) {
     alert('You cannot delete this movie')
    });
  }

}
