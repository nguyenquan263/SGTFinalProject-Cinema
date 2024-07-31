package org.quanphong.cinema.dao;

import java.util.List;

import org.quanphong.cinema.model.Employee;
import org.hibernate.SessionFactory;

public interface iEmployeedao {
	public void setSessionFactory(SessionFactory sf);
	public List<Employee> getAllEmployees();
	public Employee getEmployee(int id);
	public Employee addEmployee(Employee employee);
	public void updateEmployee(Employee employee);
	public void deleteEmployee(int id);
}
