import { Component, OnInit } from '@angular/core';
import { MovieService } from '../movie.service';
import { Http, RequestOptions } from '@angular/http';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import 'rxjs/add/operator/map';

declare var $: any;
@Component({
  selector: 'app-movie-panel',
  templateUrl: './movie-panel.component.html',
  styleUrls: ['./movie-panel.component.css'],
  providers: [MovieService,CookieService]
})
export class MoviePanelComponent implements OnInit {
  movies: any;



  //-------------------------------------------------
  id = '';
  name: '';
  language: '';
  category: '';
  dateReleased: '';
  mainStory: '';
  ageAllow: '';
  duration: '';
  byStudio: '';
  isHot= '';
  youtubeLink: '';
  imdbLink: '';
  posterLink: '';
  imageLink1: '';
  actors: '';
  directors: '';
  status= '';
  empName = '';

  //-------------------------------------------------


  constructor(private movieService: MovieService,  private cookies: CookieService, private http: Http, private router: Router) { }

  ngOnInit() {
    this.empName=this.cookies.get('loginEmployee');
    $(document).ready(function () {
      $('table').DataTable();
      $('#aMovdayTF').daterangepicker({
        parentEl:'#movieAddingModal',
        singleDatePicker: true,
        showDropdowns: true,
        locale: {
          format: 'YYYY-MM-DD'
        }
      });

      $('#aMovageDDL').select2({
        minimumResultsForSearch: 12
      });

      //-----------------------------
      $('#cMovdayTF').daterangepicker({
        parentEl:'#movieUpdateModal',
        singleDatePicker: true,
        showDropdowns: true,
        locale: {
          format: 'YYYY-MM-DD'
        }
      });
      $('#cMovageDDL').select2({
        minimumResultsForSearch: 12
      });
      $('#cMovstatusDDL').select2({
        minimumResultsForSearch: -1
      });

      $('#cMovhotDDL').select2({
        minimumResultsForSearch: -1
      });

    });

    //------------------------------------------------------------------------
    this.movieService.getMovies()
      .then(movies => {
        this.movies = movies;
        console.log(movies);
      })
      .catch(err => console.log(err));

  }
  openaddMovie(){
 
    this.name='';
    this.language='';
    this.category='';
    this.dateReleased='';
    this.mainStory='';
    this.ageAllow='';
    this.duration='';
    this.byStudio=''; 
    this.actors='';
    this.directors='';
    this.status='';
  }
  addMovie() {
    let inputMovie = {
      name: this.name,
      category: this.category,
      dateReleased: $('#aMovdayTF').val(),
      mainStory: this.mainStory,
      ageAllow: $('#aMovageDDL :selected').val(),
      duration: this.duration,
      byStudio: this.byStudio,
      isHot: $('#aMovhotDDL :selected').val(),
      language: this.language,
      youtubeLink: this.youtubeLink,
      imdbLink: this.imdbLink,
      posterLink: this.posterLink,
      imageLink1: this.imageLink1,
      actors: this.actors,
      directors: this.directors
    };

    this.movieService.addMovie(inputMovie);
    

  }

  openEditmovie(id: number) {

    //---------------------------------------------------------------------


    $('#cMovidTF').prop('disabled', true);
 


    let i: number
    let targetMovie: any;
    for (i = 0; i < this.movies.length; i++) {
      if (id === this.movies[i].id) {
        targetMovie = this.movies[i];
        break;
      }
    }
  
    //---------------------------------------------------------------------
  
    $('#cMovidTF').val(targetMovie.id);
    $('#cMovnameTF').val(targetMovie.name);
    $('#cMovcateTF').val(targetMovie.category);
    $('#cMovdayTF').val(targetMovie.dateReleased);
    $('#cMovstoryTF').val(targetMovie.mainStory);
    $('#cMovageDDL').val(targetMovie.ageAllow);
    $('#cMovageDDL').trigger('change');
    $('#cMovdurationTF').val(targetMovie.duration);
    $('#cMovstudioTF').val(targetMovie.byStudio);
    $('#cMovhotDDL').val(targetMovie.isHot +"");
    $('#cMovhotDDL').trigger('change');
    $('#cMovyoutubeTF').val(targetMovie.youtubeLink);
    $('#cMovimbTF').val(targetMovie.imdbLink);
    $('#cMovlangTF').val(targetMovie.language);
    $('#cMovposterTF').val(targetMovie.posterLink);
    $('#cMovimageTF').val(targetMovie.imageLink1);
    $('#cMovactorTF').val(targetMovie.actors);
    $('#cMovdirectorTF').val(targetMovie.directors);
    $('#cMovstatusDDL').val(targetMovie.status);
    $('#cMovstatusDDL').trigger('change');

    this.id=targetMovie.id;
    this.name=targetMovie.name;
    this.language=targetMovie.language;
    this.category=targetMovie.category;
    this.dateReleased=targetMovie.dateReleased;
    this.mainStory=targetMovie.mainStory;
    this.ageAllow=targetMovie.ageAllow;
    this.duration=targetMovie.duration;
    this.byStudio=targetMovie.byStudio;
    if((targetMovie.isHot +"") == 'false')
      this.isHot = 'No';
    else
     this.isHot = 'Yes';
  
    this.actors=targetMovie.actors;
    this.directors=targetMovie.directors;
    if(targetMovie.status==0)
    this.status = 'Active' ;
      else
    this.status = 'Deactive'

  }

  editMovie() {
    

      //-----------------------------------------------------
      let newMovie = {
        id: $('#cMovidTF').val(),
       

        status: $('#cMovstatusDDL :selected').val(),
        name: $('#cMovnameTF').val(),
        category: $('#cMovcateTF').val(),
        language: $('#cMovlangTF').val(),
        datereleased: $('#cMovdayTF').val(),
        mainstory: $('#cMovstoryTF').val(),
        ageallow: $('#cMovageDDL :selected').val() + "",
        duration: $('#cMovdurationTF').val(),
        bystudio: $('#cMovstudioTF').val(),
        ishot: $('#cMovhotDDL :selected').val(),
        youtubelink: $('#cMovyoutubeTF').val(),
        imdblink: $('#cMovimbTF').val(),
        posterlink: $('#cMovposterTF').val(),
        imagelink1: $('#cMovimageTF').val(),
        actors: $('#cMovactorTF').val(),
        directors: $('#cMovdirectorTF').val(),

      }
      this.movieService.updateMovie(newMovie);
     
      //-----------------------------------------------------
 
    
  }
  deleteMovie(){
    let selectedID = $('#cMovidTF').val();
    this.movieService.deleteMovie(selectedID);
    
  }
  signOut(){
    this.cookies.set('loginEmployeeID', '');
    this.cookies.set('loginEmployee', '');
    this.router.navigate(['./']);
  }
  
 
}
