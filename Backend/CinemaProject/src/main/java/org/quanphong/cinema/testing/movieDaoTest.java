package org.quanphong.cinema.testing;
import static org.testng.Assert.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.quanphong.cinema.dao.MovieDAO;
import org.quanphong.cinema.model.Movie;
import org.testng.annotations.*;
public class movieDaoTest {
	private static final String EXISTING_ACCOUNT_ID="88888888";
	private static final String NEW_ACCOUNT_ID="99999999";

	private Movie movie;
	
	private MovieDAO movieDAO;
	private  SessionFactory sessionFactory;
	@BeforeMethod
	public void createMovie() {
		movie = new Movie("Teo Ti","horror", new Date(2015, 5, 5), 12, "Funny he he", 120, "vn", "phongStd", true, "http://", "http://", "http://", "http://", "only me", "Phong", -1);				
		movieDAO=new MovieDAO();
		
		Configuration configuration = new Configuration().configure(movieDaoTest.class.getResource("/spring-servlet.xml"));
		
		configuration.addAnnotatedClass(org.quanphong.cinema.model.New.class);
		configuration.addAnnotatedClass(org.quanphong.cinema.model.Employee.class);
		configuration.addAnnotatedClass(org.quanphong.cinema.model.Order.class);
		configuration.addAnnotatedClass(org.quanphong.cinema.model.Customer.class);
		configuration.addAnnotatedClass(org.quanphong.cinema.model.Movie.class);
		configuration.addAnnotatedClass(org.quanphong.cinema.model.MovieShow.class);
		configuration.addAnnotatedClass(org.quanphong.cinema.model.OrderDetailSnack.class);
		configuration.addAnnotatedClass(org.quanphong.cinema.model.Room.class);
		configuration.addAnnotatedClass(org.quanphong.cinema.model.Seat.class);
		configuration.addAnnotatedClass(org.quanphong.cinema.model.Ticket.class);
		configuration.addAnnotatedClass(org.quanphong.cinema.model.Snack.class);

        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        
        sessionFactory.openSession();
        
		movieDAO.setSessionFactory(sessionFactory);
	}
	@Test
	public void addMovie() {
		movieDAO.setSessionFactory(sessionFactory);
	
		movieDAO.addMovie(movie);

		assertEquals(movieDAO.getMovie(movie.getId()).getId(),movie.getId());
		movieDAO.deleteMovie(movie.getId());
	}
	
	
	@Test
	public void deleteMovie() {
		movieDAO.setSessionFactory(sessionFactory);
		int countNewB, countNewA;

		movie = movieDAO.addMovie(movie);
		countNewB = movieDAO.getAllMovies().size();

		movieDAO.deleteMovie(movie.getId());
		countNewA = movieDAO.getAllMovies().size();
		assertEquals(countNewB - 1, countNewA);

	}
	
	
	@Test
	public void updateMovie() {
		movieDAO.setSessionFactory(sessionFactory);
		movie = movieDAO.addMovie(movie);
		Movie temp = new Movie(movie.getId(),"Teo Ti Tun","cartoon", new Date(2015, 10, 5), 12, "Funny ", 155, "vn", "phongStd", true, "http://", "http://", "http://", "http://", "only me", "Phong", -1);	
		assertTrue(true);
//		temp = movieDAO.updateMovieREST(temp);
//
//		movieDAO.deleteMovie(temp.getId());
	}
	
	
	@Test
	public void getAllmovie() {
		movieDAO.setSessionFactory(sessionFactory);

		List<Movie> newArr = movieDAO.getAllMovies();

		Session session = this.sessionFactory.getCurrentSession();

		List<Movie> newArr1 = session.createQuery("from Movie").list();

		assertEquals(newArr.size(), newArr1.size());
	}

	@Test
	public void getMovie() {
		movieDAO.setSessionFactory(sessionFactory);
		
		movie = movieDAO.addMovie(movie);
		
		Integer targetID = movie.getId();
		
		assertEquals(targetID, movieDAO.getMovie(targetID).getId());
		movieDAO.deleteMovie(targetID);
	}
}
