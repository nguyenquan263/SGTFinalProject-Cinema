package org.quanphong.cinema.dao;

import java.util.List;

import org.quanphong.cinema.model.OrderDetailSnack;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class OrderDetailSnackDAO implements iOrderDetailSnackdao {
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<OrderDetailSnack> getAllOrderDetailSnacks() {
		Session session = this.sessionFactory.getCurrentSession();
		List<OrderDetailSnack> orderdetailsnackList = session.createQuery("from OrderDetailSnack").list();
		return orderdetailsnackList;
	}

	public OrderDetailSnack getOrderDetailSnack(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		OrderDetailSnack orderdetailsnack = (OrderDetailSnack) session.get(OrderDetailSnack.class, new Integer(id));
		return orderdetailsnack;
	}

	public OrderDetailSnack addOrderDetailSnack(OrderDetailSnack orderdetailsnack) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(orderdetailsnack);
		return orderdetailsnack;
	}

	public void updateOrderDetailSnack(OrderDetailSnack orderdetailsnack) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(orderdetailsnack);
	}
	
	public OrderDetailSnack updateOrderDetailSnackREST(OrderDetailSnack orderdetailsnack) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(orderdetailsnack);
		
		return orderdetailsnack;
	}

	public void deleteOrderDetailSnack(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		OrderDetailSnack p = (OrderDetailSnack) session.load(OrderDetailSnack.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}	
}
