package org.quanphong.cinema.service;

import java.util.List;
import org.quanphong.cinema.dao.CustomerDAO;
import org.quanphong.cinema.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("customerService")
public class CustomerService {

	@Autowired
	CustomerDAO customerDao;
	
	@Transactional
	public List<Customer> getAllCustomers() {
		return customerDao.getAllCustomers();
	}

	@Transactional
	public Customer getCustomer(int id) {
		return customerDao.getCustomer(id);
	}

	@Transactional
	public void addCustomer(Customer customer) {
		customerDao.addCustomer(customer);
	}
	
	@Transactional
	public Customer addCustomerREST(Customer customer) {
		return customerDao.addCustomer(customer);
	}

	@Transactional
	public void updateCustomer(Customer customer) {
		customerDao.updateCustomer(customer);

	}
	
	@Transactional
	public Customer updateCustomerREST(Customer customer) {
		return customerDao.updateCustomerREST(customer);

	}

	@Transactional
	public void deleteCustomer(int id) {
		customerDao.deleteCustomer(id);
	}
}