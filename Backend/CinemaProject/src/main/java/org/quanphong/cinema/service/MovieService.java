package org.quanphong.cinema.service;
import java.util.List;
import org.quanphong.cinema.dao.MovieDAO;
import org.quanphong.cinema.model.Movie;
import org.quanphong.cinema.model.movieRevevue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("movieService")
public class MovieService {

	@Autowired
	MovieDAO movieDao;
	
	
	
	public MovieDAO getMovieDao() {
		return movieDao;
	}

	public void setMovieDao(MovieDAO movieDao) {
		this.movieDao = movieDao;
	}

	@Transactional
	public List<Movie> getAllMovies() {
		return movieDao.getAllMovies();
	}
	
	@Transactional
	public List<Movie> getAllMoviesActive() {
		return movieDao.getAllMoviesActive();
	}

	@Transactional
	public Movie getMovie(int id) {
		return movieDao.getMovie(id);
	}

	@Transactional
	public Movie addMovie(Movie movie) {
		return movieDao.addMovie(movie);
	}
	
	@Transactional
	public Movie addMovieREST(Movie movie) {
		return movieDao.addMovie(movie);
	}

	@Transactional
	public void updateMovie(Movie movie) {
		movieDao.updateMovie(movie);

	}
	
	@Transactional
	public Movie updateMovieREST(Movie movie) {
		return movieDao.updateMovieREST(movie);

	}

	@Transactional
	public void deleteMovie(int id) {
		movieDao.deleteMovie(id);
	}
	
	@Transactional
	public List<movieRevevue> getAllCountriesStatistic() {
		return movieDao.getAllmovieRevenue();
	}

}