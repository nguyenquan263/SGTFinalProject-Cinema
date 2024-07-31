import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Http, Response, Headers } from '@angular/http';
import { Router } from '@angular/router';
import 'rxjs/add/operator/map';
@Component({
  selector: 'app-movie-detail',
  templateUrl: './movie-detail.component.html',
  styleUrls: ['./movie-detail.component.css'],
  providers: [CookieService,]
})
export class MovieDetailComponent implements OnInit {
  currentID = null;
  currentMovie: any;
  constructor(private cookies: CookieService, private http: Http, private router: Router) {

  }

  ngOnInit() {
    // this.currentMovie = this.MDS.getMovie(this.cookies.get('clickedMovieID'));
    // console.log(this.currentMovie);
    return this.http.get('http://localhost:8080/QuanPhongGroup/getMovieREST/' + this.cookies.get('clickedMovieID'))
      .map((res: Response) => res.json())
      .subscribe(currentMovie => {
        this.currentMovie = currentMovie;
      });
  }

  goTicket() {
    let signedCusID = this.cookies.get("signedCusID");
    if (signedCusID !== "") {
      this.router.navigate(['./ticket']);
    } else alert("Please Log In");
  }

}
