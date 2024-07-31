package org.quanphong.cinema.controller;

import java.sql.Date;
import java.util.List;

import org.quanphong.cinema.model.Customer;
import org.quanphong.cinema.model.Movie;
import org.quanphong.cinema.model.Room;
import org.quanphong.cinema.model.movieRevevue;
import org.quanphong.cinema.service.CustomerService;
import org.quanphong.cinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
public class MovieRESTController {


	@Autowired
	MovieService movieService;
	
	@RequestMapping(value = "/getAllMoviesREST",method=RequestMethod.GET,produces = "application/json")

	public List<Movie> getMovies(Model model) {
		List<Movie> listOfMovies = movieService.getAllMovies();
		for(int i = 0; i<listOfMovies.size(); i++)
			listOfMovies.get(i).setMovieShowses(null);
		return listOfMovies;
	}
	
	@RequestMapping(value = "/getAllMoviesActiveREST",method=RequestMethod.GET,produces = "application/json")

	public List<Movie> getMoviesActive(Model model) {
		List<Movie> listOfMovies = movieService.getAllMoviesActive();
		for(int i = 0; i<listOfMovies.size(); i++)
			listOfMovies.get(i).setMovieShowses(null);
		return listOfMovies;
	}
	
	//-----------------------------------------------
	
	@RequestMapping(value = "/addMovieREST", method = RequestMethod.POST, headers = "Accept=application/json")
	public String addMovieREST(@RequestParam(value = "name", required = true) String name,
								@RequestParam(value = "category", required = true) String category
								, @RequestParam(value = "datereleased", required = true) Date datereleased
								, @RequestParam(value = "ageallow", required = true) Integer ageallow
								, @RequestParam(value = "mainstory", required = true) String mainstory
								, @RequestParam(value = "duration", required = true) Integer duration
								, @RequestParam(value = "language", required = true) String language
								, @RequestParam(value = "bystudio", required = true) String bystudio
								, @RequestParam(value = "ishot", required = true) Boolean isHot
								, @RequestParam(value = "youtubelink", required = true) String youtubelink
								, @RequestParam(value = "imdblink", required = true) String imdblink
								, @RequestParam(value = "posterlink", required = true) String posterlink
								, @RequestParam(value = "imagelink1", required = false) String imagelink1
								, @RequestParam(value = "actors", required = true) String actors
								, @RequestParam(value = "directors", required = true) String directors
								, @RequestParam(value = "status", required = true) Integer status) {

		Movie temp = new Movie(name, category, datereleased, ageallow, mainstory, duration, language, bystudio
				, isHot, youtubelink, imdblink, posterlink, imagelink1, actors, directors, status);
		System.out.println(temp.toString());
		Movie newMovie = movieService.addMovieREST(temp);
		return "";

	}

	@RequestMapping(value = "/deleteMovieREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public String deleteRoom(@PathVariable("id") int id) {
		Movie temp = movieService.getMovie(id);

		movieService.deleteMovie(id);
		return "Delete room successful!";

	}

	@RequestMapping(value = "/updateMovieREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Movie updateMovieREST(@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "category", required = true) String category
			, @RequestParam(value = "datereleased", required = true) Date datereleased
			, @RequestParam(value = "ageallow", required = true) Integer ageallow
			, @RequestParam(value = "mainstory", required = true) String mainstory
			, @RequestParam(value = "duration", required = true) Integer duration
			, @RequestParam(value = "language", required = true) String language
			, @RequestParam(value = "bystudio", required = true) String bystudio
			, @RequestParam(value = "ishot", required = true) Boolean isHot
			, @RequestParam(value = "youtubelink", required = true) String youtubelink
			, @RequestParam(value = "imdblink", required = true) String imdblink
			, @RequestParam(value = "posterlink", required = true) String posterlink
			, @RequestParam(value = "imagelink1", required = false) String imagelink1
			, @RequestParam(value = "actors", required = true) String actors
			, @RequestParam(value = "directors", required = true) String directors
			, @RequestParam(value = "status", required = true) Integer status
			) {
		
		
			System.out.println(id);
		Movie targetMovie = movieService.getMovie(id);
		Movie newMovie = new Movie(id, name, category, datereleased, ageallow, mainstory, duration, language, bystudio
				, isHot, youtubelink, imdblink, posterlink, imagelink1, actors, directors, status);
	
		return movieService.updateMovieREST(newMovie);
		
	}
	
	@RequestMapping(value = "/getMovieREST/{id}", method = RequestMethod.GET
			, produces = "application/json")
	public Movie getRoomREST(@PathVariable("id") int id) {
		Movie temp = movieService.getMovie(id);
		temp.setMovieShowses(null);
		return temp;
	}

	@RequestMapping(value = "/getAllmovieRevenue",method=RequestMethod.GET,produces = "application/json")
	public List<movieRevevue> getAllmovieRevenue(Model model) {
		List<movieRevevue> listOfRevenue = movieService.getAllCountriesStatistic();
		return listOfRevenue;
	}

}