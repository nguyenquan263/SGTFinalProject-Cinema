import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
declare var $: any;
@Injectable()

export class NewService {

  constructor(private http: Http) { }

  getNews() {
    return this.http.get('http://localhost:8080/QuanPhongGroup/getAllNewsREST')
      .toPromise()
      .then(res => res.json())
      .then(resJson => resJson);
  }
  addNews(news: any) {
    var ok = true;
    var tl = news.title.trim();
    var de = news.detail.trim();
    var im = news.imagelink1.trim();
    var da = news.postdate;
    var ti = news.posttime;
    var em = news.employeeId;

     if (tl === '') ok = false;
     if (im === '') ok = false;
     if (da === '') ok = false;
     if (ti === '') ok = false;
     if (em === '') ok = false;

    //   alert(ok);
    alert(da);
    if (ok === true) {
      let newsNew = {
        title : tl,
        imagelink1: im,
        detail : de,
        postdate : da,
        posttime : ti,
        empid : em,
        status : 0,
      };
      $.ajax({
        url: 'http://localhost:8080/QuanPhongGroup/addNewREST',
        type: 'POST',
        dataType: 'html',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: newsNew,
        success: function (result) {
          alert('Add successfully!');
          setTimeout(() => {
            location.reload();
          }, 2000);
        },
        error: function (result) {
          alert('Duplicate username or code!');
         
        }
      });
    } else alert('Please fill all necessary field!');
  }
  updateNews(news: any) {
    var ok = true;
    var tl = news.title.trim();
    var im = news.imageLink1.trim();
    var de = news.detail.trim();
    var da = news.postdate;
    var ti = news.posttime;
    var em = news.employeeId;

    if (tl === '') ok = false;
    if (im === '') ok = false;
    if (da === '') ok = false;
    if (ti === '') ok = false;
    if (em === '') ok = false;
    // alert(tl);
    // alert(im);
    // alert(da);
    // alert(ti);
    // alert(em);
    if (ok === true) {
      let newsNew = {
        id: news.id,
        imageLink1: im,
        title : tl,
        detail : de,
        postdate : da,
        posttime : ti,
        empid : em,
        status: news.status
      };
      alert(newsNew.id);
      alert(newsNew.imageLink1);
      alert(newsNew.title);
      alert(newsNew.detail);
      alert(newsNew.postdate);
      alert(newsNew.posttime);
      alert(newsNew.empid);
      alert(newsNew.status);

      $.ajax({
        url: 'http://localhost:8080/QuanPhongGroup/updateNewREST',
        type: 'POST',
        dataType: 'html',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: newsNew,
        success: function (result) {
          alert('Update successfully!');
          setTimeout(() => {
            location.reload();
          }, 2000);
        },
        error: function (result) {
          alert('Duplicate username!');
        }
      });

    } else alert('Please fill all necessary field!');
  }
  deleteNew(id: number) {
    return this.http.get('http://localhost:8080/QuanPhongGroup/deleteNewREST/'+id).subscribe(data => {
      alert("Delete successfully!");
      setTimeout(() => {
        location.reload();
      }, 2000);
    });
  }
}
