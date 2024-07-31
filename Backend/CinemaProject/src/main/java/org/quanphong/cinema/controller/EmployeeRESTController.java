package org.quanphong.cinema.controller;
import java.sql.Date;
import java.util.List;

import org.quanphong.cinema.model.Customer;
import org.quanphong.cinema.model.Employee;
import org.quanphong.cinema.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
public class EmployeeRESTController {


	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping(value = "/getAllEmployeesREST",method=RequestMethod.GET,produces = "application/json")

	public List<Employee> getEmployees(Model model) {
		List<Employee> listOfEmployees = employeeService.getAllEmployees();
		for(int i = 0; i<listOfEmployees.size(); i++)
			listOfEmployees.get(i).setOrderses(null);
		for (int i = 0; i < listOfEmployees.size();i++)
			listOfEmployees.get(i).setNewses(null);
		return listOfEmployees;
	}

	// --------------------------------------------------------------------------------------------------------

		@RequestMapping(value = "/addEmployeeREST", method = RequestMethod.POST, headers = "Accept=application/json")
		public String addEmployeeREST(@RequestParam(value = "code", required = true) String code,
				@RequestParam(value = "firstName", required = true) String firstname,
				@RequestParam(value = "lastName", required = true) String lastname,
				@RequestParam(value = "dob", required = true) Date dob,
				@RequestParam(value = "address", required = false) String address,
				@RequestParam(value = "phoneNumber", required = true) String phonenumber,
				@RequestParam(value = "email", required = false) String email,
				@RequestParam(value = "userName", required = true) String username,
				@RequestParam(value = "password", required = true) String password,
				@RequestParam(value = "role", required = true) String role,
				@RequestParam(value = "status", required = true) Integer status) {

			Employee temp = new Employee(code, firstname, lastname, dob, address, phonenumber, email, username, password,
					role, status);
			Employee newEmployee = employeeService.addEmployeeREST(temp);
			return "";

		}

		@RequestMapping(value = "/deleteEmployeeREST/{id}", method = RequestMethod.GET, produces = "application/json")
		public String deleteEmployee(@PathVariable("id") int id) {
			Employee p = employeeService.getEmployee(id);

			employeeService.deleteEmployee(id);
			return "Delete customer successful!";

		}

		@RequestMapping(value = "/updateEmployeeREST", method = RequestMethod.POST, produces = "application/json", consumes = {
				"application/x-www-form-urlencoded", "multipart/form-data" })
		public Employee updateEmployeeREST(@RequestParam(value = "id", required = true) Integer id,
				@RequestParam(value = "code", required = true) String code,
				@RequestParam(value = "firstName", required = true) String firstname,
				@RequestParam(value = "lastName", required = true) String lastname,
				@RequestParam(value = "dob", required = true) Date dob,
				@RequestParam(value = "address", required = false) String address,
				@RequestParam(value = "phoneNumber", required = true) String phonenumber,
				@RequestParam(value = "email", required = false) String email,
				@RequestParam(value = "userName", required = true) String username,
				@RequestParam(value = "password", required = true) String password,
				@RequestParam(value = "role", required = true) String role,
				@RequestParam(value = "status", required = true) Integer status) {

			Employee targetEmployee = employeeService.getEmployee(id);
			Employee newEmployee = new Employee(id, code, firstname, lastname, dob, address, phonenumber, email, username,
					password, role, status);

			return employeeService.updateEmployeeREST(newEmployee);
		}

		@RequestMapping(value = "/getEmployeeREST/{id}", method = RequestMethod.GET, produces = "application/json")
		public Employee getCustomerREST(@PathVariable("id") int id) {

			Employee temp = employeeService.getEmployee(id);
			temp.setOrderses(null);
			temp.setNewses(null);
			return temp;
		}

}