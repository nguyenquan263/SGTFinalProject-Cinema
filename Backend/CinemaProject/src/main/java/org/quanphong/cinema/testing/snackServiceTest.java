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
import org.quanphong.cinema.dao.SnackDAO;
import org.quanphong.cinema.model.Employee;
import org.quanphong.cinema.model.Movie;
import org.quanphong.cinema.model.New;
import org.quanphong.cinema.model.Snack;
import org.quanphong.cinema.service.EmployeeService;
import org.quanphong.cinema.service.MovieService;
import org.quanphong.cinema.service.NewService;
import org.quanphong.cinema.service.SnackService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class snackServiceTest {
	private static final String EXISTING_NEW_ID = "88888888";
	private static final String NEW_NEW_ID = "99999999";

	
	private Snack newSnack;

	private SnackService snackService = new SnackService();

	private SnackDAO snackDao = new SnackDAO();

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
		
		
		snackDao.setSessionFactory(sessionFactory);

		snackDao = new SnackDAO();
		
		snackService.setSnackDao(snackDao);

	}

	@Test
	public void addMovie() {
		
		newSnack = new Snack("Oshi", "http",4, 0);
		snackService.getSnackDao().setSessionFactory(sessionFactory);
		newSnack = snackService.addSnack(newSnack);

		assertEquals(snackDao.getSnack(newSnack.getId()).getId(), newSnack.getId());
		snackService.deleteSnack(newSnack.getId());
	}

	@Test
	public void deleteMovie() {
		snackService.getSnackDao().setSessionFactory(sessionFactory);
		int countNewB, countNewA;
		
		newSnack =  new Snack("Oshi", "http",4, 0);
		newSnack = snackService.addSnack(newSnack);
		countNewB = snackService.getAllSnacks().size();
		
		snackService.deleteSnack(newSnack.getId());
		countNewA = snackService.getAllSnacks().size();
		assertEquals(countNewB - 1, countNewA);

	}

	@Test
	public void updateMovie() {
		snackService.getSnackDao().setSessionFactory(sessionFactory);
		
		newSnack = new Snack("Oshi", "http",4, 0);
		newSnack = snackService.addSnack(newSnack);
		Snack temp = new Snack(newSnack.getId(),"Poca", "http",3, 0); 
		temp = snackService.updateSnackREST(temp);

		assertFalse(newSnack.equals(temp));
		snackService.deleteSnack(newSnack.getId());
	}

	@Test
	public void getAllSnack() {
		snackService.getSnackDao().setSessionFactory(sessionFactory);

		List<Snack> snackArr = snackService.getAllSnacks();

		Session session = this.sessionFactory.getCurrentSession();

		List<Snack> snackArr1 = session.createQuery("from Snack").list();

		assertEquals(snackArr.size(), snackArr1.size());
	}

	@Test
	public void getSnack() {
		snackService.getSnackDao().setSessionFactory(sessionFactory);
		
		newSnack =  new Snack("Chuppy", "http",4, 0);
		newSnack = snackService.addSnack(newSnack);
		
		Integer targetID = newSnack.getId();
		
		assertEquals(targetID, snackService.getSnack(targetID).getId());
		snackService.deleteSnack(newSnack.getId());
	}

}
