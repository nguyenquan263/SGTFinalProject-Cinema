package org.quanphong.cinema.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.quanphong.cinema.model.Order;
import org.quanphong.cinema.model.revenueBytime;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class OrderDAO implements iOrderdao {
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Order> getAllOrders() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Order> orderList = session.createQuery("from Order").list();
		return orderList;
	}
	
	public List<Order> getAllOrdersbyTime() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Order> orderList = session.createQuery("from Order o order by o.ordDate desc, o.ordTime desc").list();
		return orderList;
	}
	
	public List<Order> getAllOrdersNew() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Order> orderList = session.createQuery("from Order o where o.status = 0").list();
		return orderList;
	}

	public Order getOrder(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Order order = (Order) session.get(Order.class, new Integer(id));
		return order;
	}

	public Order addOrder(Order order) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(order);
		return order;
	}

	public void updateOrder(Order order) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(order);
	}
	
	public Order updateOrderREST(Order order) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(order);
		return order;
	}

	public void deleteOrder(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Order p = (Order) session.load(Order.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}	
	
	public List<revenueBytime> getRevenuebyTime() {
		List<revenueBytime> list = new ArrayList();
		Session session = this.sessionFactory.getCurrentSession();

		
		Query myQuery = session.createQuery("select c.firstName, c.lastName, o.ordDate, sum(t.cost) "
		+"from Order o, Ticket t, Customer c "
		+"where o.id = t.orders.id and o.status = 1 and o.customers.id = c.id "
		+"group by c.firstName, c.lastName, o.id");
		

		
		List l = myQuery.list();
		for (int i = 0; i<l.size(); i++) {
			Object[] singleRowValues = (Object[])l.get(i);
			
			String cusFirstName = (String) singleRowValues[0];
			String cusLastName = (String) singleRowValues[1];
			Date ordDate = (Date)singleRowValues[2];
			Long sumCost = (Long) singleRowValues[3];

			
			list.add(new revenueBytime(cusFirstName, cusLastName, ordDate, sumCost));
		}
		return list;
	}
}
