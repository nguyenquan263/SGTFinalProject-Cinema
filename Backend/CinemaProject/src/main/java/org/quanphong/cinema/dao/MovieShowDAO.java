package org.quanphong.cinema.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.quanphong.cinema.model.MovieShow;
import org.quanphong.cinema.model.movieRevevue;
import org.quanphong.cinema.model.revenueMovieShow;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MovieShowDAO implements iMovieShowdao {
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<MovieShow> getAllMovieShows() {
		Session session = this.sessionFactory.getCurrentSession();
		List<MovieShow> movieshowList = session.createQuery("from MovieShow").list();
		return movieshowList;
	}
	
	public List<MovieShow> getAllMovieShowsafterToday() {
		Session session = this.sessionFactory.getCurrentSession();
		List<MovieShow> movieshowList = session.createQuery("from MovieShow ms where ms.dateShow > sysdate()").list();
		return movieshowList;
	}
	
	public List<MovieShow> getAllMovieShowsActive() {
		Session session = this.sessionFactory.getCurrentSession();
		List<MovieShow> movieshowList = session.createQuery("select ms from MovieShow ms where ms.status = 0").list();
		return movieshowList;
	}
	
	public MovieShow getMovieShow(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		MovieShow movieshow = (MovieShow) session.get(MovieShow.class, new Integer(id));
		return movieshow;
	}

	public MovieShow addMovieShow(MovieShow movieshow) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(movieshow);
		return movieshow;
	}

	public void updateMovieShow(MovieShow movieshow) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(movieshow);
	}
	
	public MovieShow updateMovieShowREST(MovieShow movieshow) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(movieshow);
		return movieshow;
	}

	public void deleteMovieShow(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		MovieShow p = (MovieShow) session.load(MovieShow.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}	
	
	public List<revenueMovieShow> getRevenueMShow() {
		List<revenueMovieShow> list = new ArrayList();
		Session session = this.sessionFactory.getCurrentSession();

		
		Query myQuery = session.createQuery("select ms.movies.name, ms.rooms.name, ms.dateShow, ms.beginTime, sum(t.cost)"
				+"from Ticket t, MovieShow ms, Order o "
				+"where t.movieShows = ms.id and t.orders.id = o.id and o.status = 1 "
				+"GROUP BY ms.movies.name, ms.dateShow, ms.beginTime "
				+"ORDER BY sum(t.cost) desc");
		

		
		List l = myQuery.list();
		for (int i = 0; i<l.size(); i++) {
			Object[] singleRowValues = (Object[])l.get(i);
			
			String movieName = (String)singleRowValues[0];
			String roomName = (String)singleRowValues[1];
			Date dateShowstring = (Date)singleRowValues[2];
			Time beginTimestring = (Time)singleRowValues[3];
			Long sumCost = (Long) singleRowValues[4];

			
			list.add(new revenueMovieShow(movieName, roomName, dateShowstring, beginTimestring, sumCost));
		}
		return list;
	}
}
