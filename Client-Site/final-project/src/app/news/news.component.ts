import { Component, OnInit } from '@angular/core';
import { Http, Response } from '@angular/http';
import { CookieService } from 'ngx-cookie-service';
declare var $: any;
@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit {
  currNew: any;
  constructor(private http: Http, private cookies: CookieService) { }

  ngOnInit() {
      this.http.get('http://localhost:8080/QuanPhongGroup/getNewREST/' + this.cookies.get('clickedNewID'))
      .map((res: Response) => res.json())
      .subscribe(currNew => {
        this.currNew = currNew;
        console.log(this.currNew);
      });

    setTimeout(() => {
      $('#newdetail').append(this.currNew.detail);
    }, 1000);
  }

}
