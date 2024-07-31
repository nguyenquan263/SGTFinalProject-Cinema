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
import org.quanphong.cinema.dao.NewDAO;
import org.quanphong.cinema.model.Employee;
import org.quanphong.cinema.model.New;
import org.quanphong.cinema.service.EmployeeService;
import org.quanphong.cinema.service.NewService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class newServicetest {


	private New existingNew;
	private New newNew;

	private NewService newService = new NewService();
	private EmployeeService employeeService = new EmployeeService();
	
	private NewDAO newDao = new NewDAO();
	private EmployeeDAO employeeDao = new EmployeeDAO();

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
		
		
	
		employeeDao.setSessionFactory(sessionFactory);


		employeeDao = new EmployeeDAO();
		
		newService.setNewDao(newDao);
		employeeService.setEmployeeDao(employeeDao);
				

	}

	@Test
	public void createNewnew() {
		employeeService.getEmployeeDao().setSessionFactory(sessionFactory);
		Employee targetEmployee = employeeService.getEmployee(1);
		newNew = new New(targetEmployee, "abc", "xyz", new Date(2017, 12, 12), new Time(11, 11, 11), 0);
		newService.getNewDao().setSessionFactory(sessionFactory);
		newNew = newService.addNew(newNew);

		assertEquals(newService.getNew(newNew.getId()).getId(), newNew.getId());
		
		newService.deleteNew(newNew.getId());
	}

	@Test
	public void deleteNew() {
		newService.getNewDao().setSessionFactory(sessionFactory);
		employeeService.getEmployeeDao().setSessionFactory(sessionFactory);
		int countNewB, countNewA;
		Employee targetEmployee = employeeService.getEmployee(1);
		newNew = new New(targetEmployee, "abc", "xyz", new Date(2017, 12, 12), new Time(11, 11, 11), 0);
		newNew = newService.addNew(newNew);
		countNewB = newService.getAllNews().size();
		
		newService.deleteNew(newNew.getId());
		countNewA = newService.getAllNews().size();
		assertEquals(countNewB - 1, countNewA);

	}

	@Test
	public void updateNew() {
		newService.getNewDao().setSessionFactory(sessionFactory);
		employeeService.getEmployeeDao().setSessionFactory(sessionFactory);
		Employee targetEmployee = employeeService.getEmployee(1);
		newNew = new New(targetEmployee, "abc", "xyz", new Date(2017, 12, 12), new Time(11, 11, 11),0);
		newNew = newService.addNew(newNew);
		New temp = new New(newNew.getId(), targetEmployee, "abcanc", "xyz", new Date(2017, 12, 12), new Time(11, 11, 11),
				0);

		temp = newService.updateNewREST(temp);

		assertFalse(newNew.equals(temp));
		newService.deleteNew(newNew.getId());

	}

	@Test
	public void getAllnew() {
		newService.getNewDao().setSessionFactory(sessionFactory);

		List<New> newArr = newService.getAllNews();

		Session session = this.sessionFactory.getCurrentSession();

		List<New> newArr1 = session.createQuery("from New").list();

		assertEquals(newArr.size(), newArr1.size());
	}

	@Test
	public void getNew() {
		newService.getNewDao().setSessionFactory(sessionFactory);
		employeeService.getEmployeeDao().setSessionFactory(sessionFactory);
		Employee targetEmployee = employeeService.getEmployee(1);
		newNew = new New(targetEmployee, "abc", "xyz", new Date(2017, 12, 12), new Time(11, 11, 11),0);
		newNew = newService.addNew(newNew);
		
		Integer targetID = newNew.getId();
		
		assertEquals(targetID, newService.getNew(targetID).getId());
		
		newService.deleteNew(targetID);

	}

}
