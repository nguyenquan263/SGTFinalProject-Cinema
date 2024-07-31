import { Injectable } from '@angular/core';
import { Http,Response } from '@angular/http';
import 'rxjs/add/operator/toPromise';
@Injectable()
export class TicketService {

  constructor(private http: Http) { }

  getTickets() {
    return this.http.get('http://localhost:8080/QuanPhongGroup/getAllTicketsREST')
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }

  

}