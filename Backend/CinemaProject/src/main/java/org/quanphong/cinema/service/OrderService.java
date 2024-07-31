package org.quanphong.cinema.service;
import java.util.List;
import org.quanphong.cinema.dao.OrderDAO;
import org.quanphong.cinema.model.Order;
import org.quanphong.cinema.model.revenueBytime;
import org.quanphong.cinema.model.revenueMovieShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("orderService")
public class OrderService {

	@Autowired
	OrderDAO orderDao;
	
	@Transactional
	public List<Order> getAllOrders() {
		return orderDao.getAllOrders();
	}
	
	@Transactional
	public List<Order> getAllOrdersNew() {
		return orderDao.getAllOrdersNew();
	}
	
	@Transactional
	public List<Order> getAllOrdersbyTime() {
		return orderDao.getAllOrdersbyTime();
	}

	@Transactional
	public Order getOrder(int id) {
		return orderDao.getOrder(id);
	}

	@Transactional
	public void addOrder(Order order) {
		orderDao.addOrder(order);
	}
	
	@Transactional
	public Order addOrderREST(Order order) {
		return orderDao.addOrder(order);
	}

	@Transactional
	public void updateOrder(Order order) {
		orderDao.updateOrder(order);

	}
	
	@Transactional
	public Order updateOrderREST(Order order) {
		return orderDao.updateOrderREST(order);

	}

	@Transactional
	public void deleteOrder(int id) {
		orderDao.deleteOrder(id);
	}
	
	@Transactional
	public List<revenueBytime> getAllrevenueBytime() {
		return orderDao.getRevenuebyTime();
	}
}