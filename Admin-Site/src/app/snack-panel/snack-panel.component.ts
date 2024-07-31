import { Component, OnInit } from '@angular/core';
import { SnackService } from '../snack.service';
import { Http, RequestOptions } from '@angular/http';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import 'rxjs/add/operator/map';
declare var $: any;
@Component({
  selector: 'app-snack-panel',
  templateUrl: './snack-panel.component.html',
  styleUrls: ['./snack-panel.component.css'],
  providers: [SnackService,CookieService]
})
export class SnackPanelComponent implements OnInit {
  snacks: any;
 //-------------------------------------------------
  id:''; 
  name: '';
  imageLink: '';
  cost: '';
  status= '';
  empName = '';
  //-------------------------------------------------


  constructor(private snackService: SnackService, private cookies: CookieService, private http: Http, private router: Router) { }

  ngOnInit() {
    this.empName=this.cookies.get('loginEmployee');
    $(document).ready(function () {
      $('table').DataTable();

      $('#cSnacstatusDDL').select2({
        minimumResultsForSearch: -1
      });
    });

    //------------------------------------------------------------------------
    this.snackService.getSnacks()
      .then(snacks => {
        this.snacks = snacks;
        console.log(snacks);
      })
      .catch(err => console.log(err));

  }
  openaddSnack(){
    this.name='';
    this.cost= '';
    this.status = '' ;
  }
  addSnack() {
    let inputSnack = {
      name: this.name,
      imageLink : this.imageLink,
      cost : this.cost
    };

    this.snackService.addSnack(inputSnack);
  


  }

  openEditsnack(id: number) {

    $('input[name = "cdetail"]').prop('disabled', true);
    $('#cSnacstatusDDL').select2('enable', false)
    $('#cSnaidTF').prop('disabled', true);


    let i: number
    let targetSnack: any;
    for (i = 0; i < this.snacks.length; i++) {
      if (id === this.snacks[i].id) {
        targetSnack = this.snacks[i];
        break;
      }
    }
  
    //---------------------------------------------------------------------
    // alert(targetSnack.firstName);
    $('#cSnaidTF').val(targetSnack.id);
    $('#cSnanameTF').val(targetSnack.name);
    $('#cSnacostTF').val(targetSnack.cost);
    $('#cSnaimageTF').val(targetSnack.imageLink);
    $('#cSnacstatusDDL').val(targetSnack.status+"");
    $('#cSnacstatusDDL').trigger('change');
    this.id=targetSnack.id;
    this.name= targetSnack.name;
    this.cost= targetSnack.cost;
    if(targetSnack.status==0)
    this.status = 'Active' ;
       else
    this.status = 'Deactive'
  }

  editSnack() {


      //-----------------------------------------------------
      let newSnack = {
        id: $('#cSnaidTF').val(),

        name : $('#cSnanameTF').val(),
        cost : $('#cSnacostTF').val(),
        imageLink: $('#cSnaimageTF').val(),
        status: $('#cSnacstatusDDL :selected').val(),
      }
     

      this.snackService.updateSnack(newSnack);
      
      //-----------------------------------------------------
   
    
  }
  deleteSnack(){
    let selectedID = $('#cSnaidTF').val();
    this.snackService.deleteSnack(selectedID);
    
  }
  signOut(){
    this.cookies.set('loginEmployeeID', '');
    this.cookies.set('loginEmployee', '');
    this.router.navigate(['./']);
  }

}
