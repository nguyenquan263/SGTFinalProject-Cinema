import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
@Injectable()
export class MovieService {

  constructor(private http: Http) { }

  getMovies() {
    return this.http.get('http://localhost:8080/QuanPhongGroup/getAllMoviesActiveREST')
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }

}

