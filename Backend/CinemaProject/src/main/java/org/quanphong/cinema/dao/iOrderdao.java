package org.quanphong.cinema.dao;

import java.util.List;

import org.quanphong.cinema.model.Order;
import org.hibernate.SessionFactory;

public interface iOrderdao {
	public void setSessionFactory(SessionFactory sf);
	public List<Order> getAllOrders();
	public Order getOrder(int id);
	public Order addOrder(Order order);
	public void updateOrder(Order order);
	public void deleteOrder(int id);
}
