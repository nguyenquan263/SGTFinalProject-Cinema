import { Component, OnInit } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { NewService } from '../new.service';
import { MovieService } from '../movie.service';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
declare var $: any;
@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css'],
  providers: [NewService, MovieService, CookieService]
})
export class IndexComponent implements OnInit {
  data: any[];
  news: any[];
  movies: any[];

  hotMovies: any[] = [];
  constructor(private _http: Http, private newService: NewService, private movieService: MovieService, private router: Router, private cookie: CookieService) {

  }
  ngOnInit() {



    this.newService.getNews()
      .then(news => {
        this.news = news;
        console.log(news);
      })
      .catch(err => console.log(err));

    this.movieService.getMovies()
      .then(movies => {
        this.movies = movies;
        console.log(movies);
      })
      .catch(err => console.log(err));

    setTimeout(() => {
      for (let i = 0; i < this.movies.length; i++) {
        let currMovie = this.movies[i];
        if (currMovie.isHot === true) {
          this.hotMovies.push(currMovie);
        }
      }
      console.log(this.hotMovies);
    }, 2000);
    setTimeout(() => {
      $(function () {
        $('.pgwSlider').pgwSlider();
      });
    }, 4000);
    
  }

  goTicket(){
    this.router.navigate(['/ticket']);
  }

  goDetail(id){
    this.cookie.set('clickedMovieID', id);
    this.router.navigate(['/movieDetail']);
  }

  goNewDetail(id){
    this.cookie.set('clickedNewID', id);
    this.router.navigate(['/news']);
  }




}
