package org.quanphong.cinema.dao;

import java.util.List;

import org.quanphong.cinema.model.Movie;
import org.hibernate.SessionFactory;

public interface iMoviedao {
	public void setSessionFactory(SessionFactory sf);
	public List<Movie> getAllMovies();
	public Movie getMovie(int id);
	public Movie addMovie(Movie movie);
	public void updateMovie(Movie movie);
	public void deleteMovie(int id);
}
