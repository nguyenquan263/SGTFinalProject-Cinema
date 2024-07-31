import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
@Injectable()
export class MovieshowService {

  constructor(private http: Http) { }

  getMovieShows() {
    return this.http.get('http://localhost:8080/QuanPhongGroup/getAllMovieShowsActiveREST')
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }

  getMovieShowsbyMovieID(id: number){
    return this.http.get('http://localhost:8080/QuanPhongGroup/getMovieShowRESTbyMovieID/'+id)
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }

}
