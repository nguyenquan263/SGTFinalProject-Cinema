package org.quanphong.cinema.dao;

import java.util.List;

import org.quanphong.cinema.model.Customer;
import org.hibernate.SessionFactory;

public interface iCustomerdao {
	public void setSessionFactory(SessionFactory sf);
	public List<Customer> getAllCustomers();
	public Customer getCustomer(int id);
	public Customer addCustomer(Customer customer);
	public void updateCustomer(Customer customer);
	public void deleteCustomer(int id);
}
