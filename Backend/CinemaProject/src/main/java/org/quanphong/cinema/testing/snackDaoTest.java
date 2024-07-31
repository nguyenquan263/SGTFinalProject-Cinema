package org.quanphong.cinema.testing;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.quanphong.cinema.model.Snack;
import org.quanphong.cinema.dao.SnackDAO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class snackDaoTest {

	private Snack snack;
	
	private  SnackDAO snackDAO;
	private  SessionFactory sessionFactory;
	@BeforeMethod
	public void createMovie() {
		snack = new Snack("Poca", "http", 5, 0);				
		snackDAO=new SnackDAO();
		
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
        
		snackDAO.setSessionFactory(sessionFactory);
	}
	@Test
	public void addSnack() {
		snackDAO.setSessionFactory(sessionFactory);
	
		snackDAO.addSnack(snack);

		assertEquals(snackDAO.getSnack(snack.getId()).getId(),snack.getId());
		snackDAO.deleteSnack(snack.getId());
	}
	
	
	@Test
	public void deleteSnack() {
		snackDAO.setSessionFactory(sessionFactory);
		int countNewB, countNewA;

		snack = snackDAO.addSnack(snack);
		countNewB = snackDAO.getAllSnacks().size();

		snackDAO.deleteSnack(snack.getId());
		countNewA = snackDAO.getAllSnacks().size();
		assertEquals(countNewB - 1, countNewA);

	}
	
	
	@Test
	public void updateSnack() {
		snackDAO.setSessionFactory(sessionFactory);
		snack = snackDAO.addSnack(snack);
		Snack temp = new  Snack(snack.getId(),"Oshi", "http",4, 0);

		temp = snackDAO.updateSnackREST(temp);

		assertFalse(snack.equals(temp));
		snackDAO.deleteSnack(temp.getId());
	}
	
	
	@Test
	public void getAllsnack() {
		snackDAO.setSessionFactory(sessionFactory);

		List<Snack> newArr = snackDAO.getAllSnacks();

		Session session = this.sessionFactory.getCurrentSession();

		List<Snack> newArr1 = session.createQuery("from Snack").list();

		assertEquals(newArr.size(), newArr1.size());
	}

	@Test
	public void getSnack() {
		snackDAO.setSessionFactory(sessionFactory);
		
		snack = snackDAO.addSnack(snack);
		
		Integer targetID = snack.getId();
		
		assertEquals(targetID, snackDAO.getSnack(targetID).getId());
		
		snackDAO.deleteSnack(targetID);

	}
}
