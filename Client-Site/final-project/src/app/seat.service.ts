import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import 'rxjs/add/operator/toPromise';
@Injectable()
export class SeatService {

  constructor(private http: Http) { }

  getSeatIDByRCRID(row: string, column: string, roomid: string) {
    this.http.get('http://localhost:8080/QuanPhongGroup//getSeatRESTbyRowColumnRoom/'+row+'&'+column+'&'+roomid)
    .map((res: Response) => res.json())
    .subscribe(seat => {return seat});
  }

}
