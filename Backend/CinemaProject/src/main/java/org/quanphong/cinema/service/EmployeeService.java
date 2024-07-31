package org.quanphong.cinema.service;

import java.util.List;
import org.quanphong.cinema.dao.EmployeeDAO;
import org.quanphong.cinema.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("employeeService")
public class EmployeeService {

	@Autowired
	EmployeeDAO employeeDao;
	
	public EmployeeDAO getEmployeeDao() {
		return employeeDao;
	}

	public void setEmployeeDao(EmployeeDAO employeeDao) {
		this.employeeDao = employeeDao;
	}

	@Transactional
	public List<Employee> getAllEmployees() {
		return employeeDao.getAllEmployees();
	}

	@Transactional
	public Employee getEmployee(int id) {
		return employeeDao.getEmployee(id);
	}

	@Transactional
	public void addEmployee(Employee employee) {
		employeeDao.addEmployee(employee);
	}
	
	@Transactional
	public Employee addEmployeeREST(Employee employee) {
		return employeeDao.addEmployee(employee);
	}

	@Transactional
	public void updateEmployee(Employee employee) {
		employeeDao.updateEmployee(employee);

	}
	
	@Transactional
	public Employee updateEmployeeREST(Employee employee) {
		return employeeDao.updateEmployeeREST(employee);

	}

	@Transactional
	public void deleteEmployee(int id) {
		employeeDao.deleteEmployee(id);
	}
}