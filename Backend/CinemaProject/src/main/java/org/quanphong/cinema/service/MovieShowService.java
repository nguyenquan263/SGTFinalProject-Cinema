package org.quanphong.cinema.service;
import java.util.List;
import org.quanphong.cinema.dao.MovieShowDAO;
import org.quanphong.cinema.model.MovieShow;
import org.quanphong.cinema.model.movieRevevue;
import org.quanphong.cinema.model.revenueMovieShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("movieShowService")
public class MovieShowService {

	@Autowired
	MovieShowDAO movieShowDao;
	
	@Transactional
	public List<MovieShow> getAllMovieShows() {
		return movieShowDao.getAllMovieShows();
	}
	
	@Transactional
	public List<MovieShow> getAllMovieShowsafterToday() {
		return movieShowDao.getAllMovieShowsafterToday();
	}
	
	@Transactional
	public List<MovieShow> getAllMovieShowsActive() {
		return movieShowDao.getAllMovieShowsActive();
	}

	@Transactional
	public MovieShow getMovieShow(int id) {
		return movieShowDao.getMovieShow(id);
	}

	@Transactional
	public void addMovieShow(MovieShow movieShow) {
		movieShowDao.addMovieShow(movieShow);
	}
	
	@Transactional
	public MovieShow addMovieShowREST(MovieShow movieShow) {
		return movieShowDao.addMovieShow(movieShow);
	}

	@Transactional
	public void updateMovieShow(MovieShow movieShow) {
		movieShowDao.updateMovieShow(movieShow);

	}
	
	@Transactional
	public MovieShow updateMovieShowREST(MovieShow movieShow) {
		return movieShowDao.updateMovieShowREST(movieShow);

	}

	@Transactional
	public void deleteMovieShow(int id) {
		movieShowDao.deleteMovieShow(id);
	}
	
	@Transactional
	public List<revenueMovieShow> getAllrevenueMovieShow() {
		return movieShowDao.getRevenueMShow();
	}
}