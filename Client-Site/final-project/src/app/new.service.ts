import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
@Injectable()
export class NewService {

  constructor(private http: Http) { }

  getNews() {
    return this.http.get('http://localhost:8080/QuanPhongGroup/getAllNewsREST')
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }



}
