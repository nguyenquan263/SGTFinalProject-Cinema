package org.quanphong.cinema.dao;

import java.util.List;

import org.quanphong.cinema.model.MovieShow;
import org.hibernate.SessionFactory;

public interface iMovieShowdao {
	public void setSessionFactory(SessionFactory sf);
	public List<MovieShow> getAllMovieShows();
	public MovieShow getMovieShow(int id);
	public MovieShow addMovieShow(MovieShow movieshow);
	public void updateMovieShow(MovieShow movieshow);
	public void deleteMovieShow(int id);
}
