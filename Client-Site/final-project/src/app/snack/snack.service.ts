import {Injectable} from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Injectable()

export class snackService {
    constructor(private http: Http){}

    getSnacks(){
        return this.http.get('http://localhost:8080/QuanPhongGroup/getAllSnacksREST')
        .toPromise()
        .then(res => res.json())
        .then(resJson => resJson);
    }

}