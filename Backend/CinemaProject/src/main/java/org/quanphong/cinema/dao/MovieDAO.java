package org.quanphong.cinema.dao;

import java.util.ArrayList;
import java.util.List;

import org.quanphong.cinema.model.Movie;
import org.quanphong.cinema.model.Room;
import org.quanphong.cinema.model.movieRevevue;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MovieDAO implements iMoviedao {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Movie> getAllMovies() {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		List<Movie> movieList = session.createQuery("from Movie").list();
		return movieList;
	}
	
	public List<Movie> getAllMoviesActive() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Movie> movieList = session.createQuery("select m from Movie m where m.status=0").list();
		return movieList;
	}

	public Movie getMovie(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		Movie movie = (Movie) session.get(Movie.class, new Integer(id));
		return movie;
	}

	public Movie addMovie(Movie movie) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		session.persist(movie);
		if (!trans.getStatus().equals(TransactionStatus.ACTIVE)){
			trans.commit();
		}
		return movie;
	}

	public void updateMovie(Movie movie) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(movie);
	}
	
	public Movie updateMovieREST(Movie movie) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		session.update(movie);
		if (!trans.getStatus().equals(TransactionStatus.ACTIVE)){
			trans.commit();
		}
		return movie;
	}

	public void deleteMovie(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		Movie p = (Movie) session.load(Movie.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
		if (!trans.getStatus().equals(TransactionStatus.ACTIVE)){
			trans.commit();
		}
	}	
	
	public List<movieRevevue> getAllmovieRevenue() {
		List<movieRevevue> list = new ArrayList();
		Session session = this.sessionFactory.getCurrentSession();

		
		Query myQuery = session.createQuery("select m.name, sum(t.cost) "
				+ "from Movie m, MovieShow ms, Ticket t "
				+ "where  (ms.movies.id = m.id) and (t.movieShows.id = ms.id) and (m.status = 0) "
				+ "group by m.name ");
		

		
		List l = myQuery.list();
		for (int i = 0; i<l.size(); i++) {
			Object[] singleRowValues = (Object[])l.get(i);
			String movieName = (String)singleRowValues[0];
			Long Revenue = (Long)singleRowValues[1];
			list.add(new movieRevevue(movieName, Revenue));
		}
		return list;
	}
}
