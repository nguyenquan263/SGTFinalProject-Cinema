import { Component, OnInit } from '@angular/core';
import { NewService } from '../new.service';
import { Http, RequestOptions } from '@angular/http';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import 'rxjs/add/operator/map';
declare var $: any;
@Component({
  selector: 'app-new-panel',
  templateUrl: './new-panel.component.html',
  styleUrls: ['./new-panel.component.css'],
  providers: [NewService,CookieService]
})
export class NewPanelComponent implements OnInit {

  news: any;
  id='';
  Newday = '';
  title = '';
  imageLink = '';
  detail = '';
  time = '';
  de = '';
  empName = '';
  constructor(private newService: NewService, private cookies: CookieService, private http: Http, private router: Router) { }
  ngOnInit() {
    this.empName=this.cookies.get('loginEmployee');
    $(document).ready(function () {
      $('table').DataTable();

      //-------------------------------------------
      $('#NewdayTF').daterangepicker({
        singleDatePicker: true,
        showDropdowns: true,
        locale: {
          format: 'YYYY-MM-DD'
        }
      });
      $('#cNewdayTF').daterangepicker({
        singleDatePicker: true,
        showDropdowns: true,
        locale: {
          format: 'YYYY-MM-DD'
        }
      });
    });
      //--------------------------------------------

      this.newService.getNews()
        .then(news => {
          this.news = news;
          console.log(news);
        })
        .catch(err => console.log(err));

    //---------------------------------------------------
    $('#editText').on('froalaEditor.contentChanged froalaEditor.initialized', function (e, editor) {
      $('#preview').text(editor.codeBeautifier.run(editor.html.get()));
    }).froalaEditor();
    $('#ceditText').on('froalaEditor.contentChanged froalaEditor.initialized', function (e, editor) {
      $('#cpreview').text(editor.codeBeautifier.run(editor.html.get()));
    }).froalaEditor();
  }
  openaddNew(){
    this.title = '';
    $('div#editText').froalaEditor('html.set', '');
  }
  addNews() {
      let SysTime = new Date();
      let inputNew = {
        title : this.title,
        imagelink1: this.imageLink,
        postdate : SysTime.getFullYear() + '-' + (SysTime.getMonth()+1) + '-' + SysTime.getDate(),
        posttime : SysTime.getHours() + ':' + SysTime.getMinutes() + ':' + SysTime.getMinutes(),
        detail : $('div#editText').froalaEditor('html.get') + '',
        employeeId: this.cookies.get('loginEmployeeID'),
        status : 0,
      };
      console.log(inputNew);
    this.newService.addNews(inputNew);
    
  }
  openEditnew(id: number) {

    //---------------------------------------------------------------------

    var i: number
    var targetNew: any;

    for (i = 0; i < this.news.length; i++) {
      if (id === this.news[i].id) {
        targetNew = this.news[i];
        break;

      }
    }
  
    //---------------------------------------------------------------------
    $('#cNewidTF').val(targetNew.id);
    $('#cNewtitlebTF').val(targetNew.title);
    $('#cNewstatusDDL').val(targetNew.status);
    
    $('#cNewimageLinkTF').val(targetNew.imageLink1);
    $('#cCusstatusDDL').trigger('change')
    $('div#ceditText').froalaEditor('html.set', targetNew.detail);
    this.title = targetNew.title;
    this.id = targetNew.id;
  }
  updateNew() {
  let SysTime = new Date();
  let newNew = {
    id: $('#cNewidTF').val(),
    imageLink1:$('#cNewimageLinkTF').val(),
    title : $('#cNewtitlebTF').val(),
    postdate : SysTime.getFullYear() + '-' + (SysTime.getMonth()+1) + '-' + SysTime.getDay(),
    posttime : SysTime.getHours() + ':' + SysTime.getMinutes() + ':' + SysTime.getMinutes(),
    detail : $('div#ceditText').froalaEditor('html.get') + '',
    employeeId: 1,
    status: $('#cNewstatusDDL :selected').val()
  };
    this.newService.updateNews(newNew);
    
  }

  deleteNew() {
    let selectedID = $('#cNewidTF').val();
    this.newService.deleteNew(selectedID);
    
    
  }
  signOut(){
    this.cookies.set('loginEmployeeID', '');
    this.cookies.set('loginEmployee', '');
    this.router.navigate(['./']);
  }
}
