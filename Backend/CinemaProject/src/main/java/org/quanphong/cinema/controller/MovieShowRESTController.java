package org.quanphong.cinema.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.quanphong.cinema.model.Employee;
import org.quanphong.cinema.model.Movie;
import org.quanphong.cinema.model.MovieShow;
import org.quanphong.cinema.model.Room;
import org.quanphong.cinema.model.movieRevevue;
import org.quanphong.cinema.model.revenueMovieShow;
import org.quanphong.cinema.service.MovieService;
import org.quanphong.cinema.service.MovieShowService;
import org.quanphong.cinema.service.RoomService;
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
public class MovieShowRESTController {

	@Autowired
	MovieShowService movieShowService;

	@Autowired
	MovieService movieService;

	@Autowired
	RoomService roomService;

	@Autowired
	private ServletContext servletContext;
	@CrossOrigin
	@RequestMapping(value = "/getAllMovieShowsREST", method = RequestMethod.GET, produces = "application/json")

	public List<MovieShow> getMovieShows(Model model) {
		List<MovieShow> movieshowList = movieShowService.getAllMovieShows();
		for (int i = 0; i < movieshowList.size(); i++) {
			movieshowList.get(i).setTicketses(null);
			movieshowList.get(i).getMovies().setMovieShowses(null);
			movieshowList.get(i).getRooms().setMovieShowses(null);
			movieshowList.get(i).getRooms().setSeatses(null);
		}

		return movieshowList;
	}
	
	@RequestMapping(value = "/getAllMovieShowsafterTodayREST", method = RequestMethod.GET, produces = "application/json")

	public List<MovieShow> getMovieShowsafterToday(Model model) {
		List<MovieShow> movieshowList = movieShowService.getAllMovieShowsafterToday();
		for (int i = 0; i < movieshowList.size(); i++) {
			movieshowList.get(i).setTicketses(null);
			movieshowList.get(i).getMovies().setMovieShowses(null);
			movieshowList.get(i).getRooms().setMovieShowses(null);
			movieshowList.get(i).getRooms().setSeatses(null);
		}

		return movieshowList;
	}
	
	@RequestMapping(value = "/getAllMovieShowsActiveREST", method = RequestMethod.GET, produces = "application/json")

	public List<MovieShow> getMovieShowsActive(Model model) {
		List<MovieShow> movieshowList = movieShowService.getAllMovieShowsActive();
		for (int i = 0; i < movieshowList.size(); i++) {
			movieshowList.get(i).setTicketses(null);
			movieshowList.get(i).getMovies().setMovieShowses(null);
			movieshowList.get(i).getRooms().setMovieShowses(null);
			movieshowList.get(i).getRooms().setSeatses(null);
		}

		return movieshowList;
	}

	// --------------------------------------------------------------------------------------------------------

	@RequestMapping(value = "/addMovieShowREST", method = RequestMethod.POST, headers = "Accept=application/json")
	public String addMovieShowREST(@RequestParam(value = "movieID", required = true) int movieid,
			@RequestParam(value = "roomID", required = true) int roomid,
			@RequestParam(value = "dateShow", required = true) Date dateshow,
			@RequestParam(value = "beginTime", required = true) Time timebegin,
			@RequestParam(value = "endTime", required = false) Time timeend,
			@RequestParam(value = "showType", required = true) String showtype,
			@RequestParam(value = "cost", required = false) Integer cost,
			@RequestParam(value = "status", required = true) Integer status) {

		Movie targetMovie = movieService.getMovie(movieid);
		Room targetRoom = roomService.getRoom(roomid);
		MovieShow temp = new MovieShow(targetMovie, targetRoom, dateshow, timebegin, timeend, showtype, cost, status);
		MovieShow newMovieShow = movieShowService.addMovieShowREST(temp);
		return "";

	}

	@RequestMapping(value = "/deleteMovieShowREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public String deleteMovieShowREST(@PathVariable("id") int id) {
		MovieShow p = movieShowService.getMovieShow(id);

		movieShowService.deleteMovieShow(id);
		return "Delete customer successful!";

	}

	@RequestMapping(value = "/updateMovieShowREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public MovieShow updateMovieShowREST(@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "movieID", required = true) int movieid,
			@RequestParam(value = "roomID", required = true) int roomid,
			@RequestParam(value = "dateShow", required = true) Date dateshow,
			@RequestParam(value = "beginTime", required = true) Time timebegin,
			@RequestParam(value = "endTime", required = false) Time timeend,
			@RequestParam(value = "showType", required = true) String showtype,
			@RequestParam(value = "cost", required = false) Integer cost,
			@RequestParam(value = "status", required = true) Integer status) {

		MovieShow targetMovieShow = movieShowService.getMovieShow(id);
		Movie targetMovie = movieService.getMovie(movieid);
		targetMovie.setMovieShowses(null);
		Room targetRoom = roomService.getRoom(roomid);
		targetRoom.setMovieShowses(null);
		targetRoom.setSeatses(null);
		MovieShow newMovieShow = new MovieShow(id, targetMovie, targetRoom, dateshow, timebegin, timeend, showtype,
				cost, status);

		return movieShowService.updateMovieShowREST(newMovieShow);
	}

	@RequestMapping(value = "/getMovieShowREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public MovieShow getMovieShowREST(@PathVariable("id") int id) {

		MovieShow temp = movieShowService.getMovieShow(id);
		temp.setTicketses(null);
		temp.getMovies().setMovieShowses(null);
		temp.getRooms().setMovieShowses(null);
		temp.getRooms().setSeatses(null);
		return temp;
	}
	
	@RequestMapping(value = "/getMovieShowRESTbyMovieID/{movieid}", method = RequestMethod.GET, produces = "application/json")
	public List<MovieShow> getMovieShowRESTbyMovieID(@PathVariable("movieid") int movieid) {
		
		List<MovieShow> allMovieshow = movieShowService.getAllMovieShowsActive();
		List<MovieShow> resMovieShow = new ArrayList<>();
		for (int i = 0 ; i < allMovieshow.size();i++) {
			MovieShow currMS = allMovieshow.get(i);
			if (currMS.getMovies().getId() == movieid) {
				currMS.setTicketses(null);
				currMS.getMovies().setMovieShowses(null);
				currMS.getRooms().setMovieShowses(null);
				currMS.getRooms().setSeatses(null);
				
				resMovieShow.add(currMS);
			}
		}
		
		return resMovieShow;
	}
	
	@RequestMapping(value = "/getAllmovieshowRevenue",method=RequestMethod.GET,produces = "application/json")
	public List<revenueMovieShow> getAllmovieshowRevenue(Model model) {
		List<revenueMovieShow> listOfRevenue = movieShowService.getAllrevenueMovieShow();
		return listOfRevenue;
	}

}