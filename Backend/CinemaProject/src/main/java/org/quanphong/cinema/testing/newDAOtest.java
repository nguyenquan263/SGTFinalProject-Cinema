package org.quanphong.cinema.testing;

import static org.testng.Assert.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.quanphong.cinema.dao.EmployeeDAO;
import org.quanphong.cinema.dao.NewDAO;
import org.quanphong.cinema.model.Employee;
import org.quanphong.cinema.model.New;
import org.testng.annotations.*;

public class newDAOtest {
	private static final String EXISTING_NEW_ID = "88888888";
	private static final String NEW_NEW_ID = "99999999";

	private New existingNew;
	private New newNew;

	private NewDAO newDao = new NewDAO();
	private EmployeeDAO employeeDAO = new EmployeeDAO();

	private SessionFactory sessionFactory;

	@BeforeMethod
	public void init() {
		Configuration configuration = new Configuration()
				.configure(newDAOtest.class.getResource("/spring-servlet.xml"));

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
		newDao.setSessionFactory(sessionFactory);
		employeeDAO.setSessionFactory(sessionFactory);
		Employee targetEmployee = employeeDAO.getEmployee(1);
		newNew = new New(targetEmployee, "abc", "xyz", new Date(2017, 12, 12), new Time(11, 11, 11), 0);

		newDao = new NewDAO();

	}

	@Test
	public void createNewnew() {
		newDao.setSessionFactory(sessionFactory);

		newNew = newDao.addNew(newNew);

		assertEquals(newDao.getNew(newNew.getId()).getId(), newNew.getId());
		
		newDao.deleteNew(newNew.getId());
	}

	@Test
	public void deleteNew() {
		newDao.setSessionFactory(sessionFactory);
		int countNewB, countNewA;

		newNew = newDao.addNew(newNew);
		countNewB = newDao.getAllNews().size();

		newDao.deleteNew(newNew.getId());
		countNewA = newDao.getAllNews().size();
		assertEquals(countNewB - 1, countNewA);

	}

	@Test
	public void updateNew() {

		
		newDao.setSessionFactory(sessionFactory);

		Employee targetEmployee = employeeDAO.getEmployee(1);
		New temp = new New(newNew.getId(), targetEmployee, "abc", "xyz", new Date(2017, 12, 12), new Time(11, 11, 11),
				0);

		New before = newDao.addNew(temp);
		before.setDetail("abcv");
		before.setTitle("quan");
		New after = newDao.updateNewREST(before);
		System.out.println(after.getDetail()+" "+before.getDetail());
		assertEquals(before.getDetail(), after.getDetail());

	}

	@Test
	public void getAllnew() {
		newDao.setSessionFactory(sessionFactory);

		List<New> newArr = newDao.getAllNews();

		Session session = this.sessionFactory.getCurrentSession();

		List<New> newArr1 = session.createQuery("from New").list();

		assertEquals(newArr.size(), newArr1.size());
	}

	@Test
	public void getNew() {
		newDao.setSessionFactory(sessionFactory);
		
		newNew = newDao.addNew(newNew);
		
		Integer targetID = newNew.getId();
		
		assertEquals(targetID, newDao.getNew(targetID).getId());
		
		newDao.deleteNew(targetID);

	}

}
