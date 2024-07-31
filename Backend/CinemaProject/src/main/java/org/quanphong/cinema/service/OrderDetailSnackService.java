package org.quanphong.cinema.service;

import java.util.List;
import org.quanphong.cinema.dao.OrderDetailSnackDAO;
import org.quanphong.cinema.model.OrderDetailSnack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("oderDetailSnackService")
public class OrderDetailSnackService {

	@Autowired
	OrderDetailSnackDAO oderDetailSnackDao;
	
	@Transactional
	public List<OrderDetailSnack> getAllOrderDetailSnacks() {
		return oderDetailSnackDao.getAllOrderDetailSnacks();
	}

	@Transactional
	public OrderDetailSnack getOrderDetailSnack(int id) {
		return oderDetailSnackDao.getOrderDetailSnack(id);
	}

	@Transactional
	public void addOrderDetailSnack(OrderDetailSnack oderDetailSnack) {
		oderDetailSnackDao.addOrderDetailSnack(oderDetailSnack);
	}
	
	@Transactional
	public OrderDetailSnack addOrderDetailSnackREST(OrderDetailSnack oderDetailSnack) {
		return oderDetailSnackDao.addOrderDetailSnack(oderDetailSnack);
	}

	@Transactional
	public void updateOrderDetailSnack(OrderDetailSnack oderDetailSnack) {
		oderDetailSnackDao.updateOrderDetailSnack(oderDetailSnack);

	}
	
	@Transactional
	public OrderDetailSnack updateOrderDetailSnackREST(OrderDetailSnack oderDetailSnack) {
		return oderDetailSnackDao.updateOrderDetailSnackREST(oderDetailSnack);

	}

	@Transactional
	public void deleteOrderDetailSnack(int id) {
		oderDetailSnackDao.deleteOrderDetailSnack(id);
	}
}