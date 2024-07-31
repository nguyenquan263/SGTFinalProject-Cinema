package org.quanphong.cinema.dao;

import java.util.List;

import org.quanphong.cinema.model.OrderDetailSnack;
import org.hibernate.SessionFactory;

public interface iOrderDetailSnackdao {
	public void setSessionFactory(SessionFactory sf);
	public List<OrderDetailSnack> getAllOrderDetailSnacks();
	public OrderDetailSnack getOrderDetailSnack(int id);
	public OrderDetailSnack addOrderDetailSnack(OrderDetailSnack orderdetailsnack);
	public void updateOrderDetailSnack(OrderDetailSnack orderdetailsnack);
	public void deleteOrderDetailSnack(int id);
}
