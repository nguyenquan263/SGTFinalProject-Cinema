package org.quanphong.cinema.testing;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.quanphong.cinema.dao.EmployeeDAO;
import org.quanphong.cinema.dao.MovieDAO;
import org.quanphong.cinema.dao.NewDAO;
import org.quanphong.cinema.model.Employee;
import org.quanphong.cinema.model.Movie;
import org.quanphong.cinema.model.New;
import org.quanphong.cinema.service.EmployeeService;
import org.quanphong.cinema.service.MovieService;
import org.quanphong.cinema.service.NewService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class movieServiceTest {
	private static final String EXISTING_NEW_ID = "88888888";
	private static final String NEW_NEW_ID = "99999999";

	
	private Movie newMovie;

	private MovieService movieService = new MovieService();

	private MovieDAO movieDao = new MovieDAO();

	private SessionFactory sessionFactory;

	@BeforeMethod
	public void init() {
		Configuration configuration = new Configuration()
				.configure(movieDaoTest.class.getResource("/spring-servlet.xml"));

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
		
		
		movieDao.setSessionFactory(sessionFactory);

		movieDao = new MovieDAO();
		
		movieService.setMovieDao(movieDao);

	}

	@Test
	public void addMovie() {
		
		newMovie = new Movie("Teo Ti","horror", new Date(2015, 5, 5), 12, "Funny he he", 120, "vn", "phongStd", true, "http://", "http://", "http://", "http://", "only me", "Phong", -1);
		movieService.getMovieDao().setSessionFactory(sessionFactory);
		newMovie = movieService.addMovie(newMovie);

		assertEquals(movieDao.getMovie(newMovie.getId()).getId(), newMovie.getId());
		movieService.deleteMovie(newMovie.getId());
	}

	@Test
	public void deleteMovie() {
		movieService.getMovieDao().setSessionFactory(sessionFactory);
		int countNewB, countNewA;
		
		newMovie =  new Movie("Teo Ti","horror", new Date(2015, 5, 5), 12, "Funny he he", 120, "vn", "phongStd", true, "http://", "http://", "http://", "http://", "only me", "Phong", -1);
		newMovie = movieService.addMovie(newMovie);
		countNewB = movieService.getAllMovies().size();
		
		movieService.deleteMovie(newMovie.getId());
		countNewA = movieService.getAllMovies().size();
		assertEquals(countNewB - 1, countNewA);

	}

	@Test
	public void updateMovie() {
		movieService.getMovieDao().setSessionFactory(sessionFactory);
		
		newMovie = new Movie("Teo Ti","horror", new Date(2015,5, 5), 12, "Funny he he", 120, "vn", "phongStd", true, "http://", "http://", "http://", "http://", "only me", "Phong", -1);
		newMovie = movieService.addMovie(newMovie);
		Movie temp = new Movie(newMovie.getId(),"Teo em","drama", new Date(2015,15, 5), 12, "Funny he he", 120, "vn", "phongStd", true, "http://", "http://", "http://", "http://", "only me", "Phong", -1);
		temp = movieService.updateMovieREST(temp);

		assertFalse(newMovie.equals(temp));
		movieService.deleteMovie(newMovie.getId());
	}

	@Test
	public void getAllMovie() {
		movieService.getMovieDao().setSessionFactory(sessionFactory);

		List<Movie> movieArr = movieService.getAllMovies();

		Session session = this.sessionFactory.getCurrentSession();

		List<Movie> movieArr1 = session.createQuery("from Movie").list();

		assertEquals(movieArr.size(), movieArr1.size());
	}

	@Test
	public void getMovie() {
		movieService.getMovieDao().setSessionFactory(sessionFactory);
		
		newMovie = new Movie("sieu nhan gao","horror", new Date(2015,5, 5), 12, "Funny ", 120, "vn", "phongStd", true, "http://", "http://", "http://", "http://", "only me", "Phong", -1);
		newMovie = movieService.addMovie(newMovie);
		
		Integer targetID = newMovie.getId();
		
		assertEquals(targetID, movieService.getMovie(targetID).getId());
		movieService.deleteMovie(newMovie.getId());
	}

}
