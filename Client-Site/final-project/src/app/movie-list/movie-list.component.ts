import { Component, OnInit } from '@angular/core';
import { movieListService } from './movie-list.service';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css'],
  providers: [movieListService, CookieService]
})
export class MovieListComponent implements OnInit {
  movies: any;
  clickedID = null;
  constructor(private movieService: movieListService, private router: Router, private cookies: CookieService) {
    this.movieService.getMovies()
      .then(movies => {
        this.movies = movies;
        console.log(movies);
      })
      .catch(err => console.log(err));

  }

  ngOnInit() {
  }

  goDetail() {


    this.cookies.set('clickedMovieID', this.clickedID);
    this.router.navigate(['./movieDetail']);

  }

}
