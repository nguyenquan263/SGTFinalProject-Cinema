package org.quanphong.cinema.controller;

import java.sql.Date;
import java.util.List;

import org.quanphong.cinema.model.Customer;
import org.quanphong.cinema.model.Room;
import org.quanphong.cinema.service.CustomerService;
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
public class CustomerRESTController {

	@Autowired
	CustomerService customerService;

	@RequestMapping(value = "/getAllCustomersREST", method = RequestMethod.GET, produces = "application/json")

	public List<Customer> getCustomers(Model model) {
		List<Customer> listOfCustomers = customerService.getAllCustomers();
		for (int i = 0; i < listOfCustomers.size(); i++)
			listOfCustomers.get(i).setOrderses(null);
		return listOfCustomers;
	}
	// --------------------------------------------------------------------------------------------------------

	@RequestMapping(value = "/addCustomersREST", method = RequestMethod.POST, headers = "Accept=application/json")
	public String addCustomerREST(@RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "firstName", required = true) String firstname,
			@RequestParam(value = "lastName", required = true) String lastname,
			@RequestParam(value = "dob", required = true) Date dob,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "phoneNumber", required = true) String phonenumber,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "userName", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "level", required = true) String level,
			@RequestParam(value = "status", required = true) Integer status) {

		Customer temp = new Customer(code, firstname, lastname, dob, address, phonenumber, email, username, password,
				level, status);
		Customer newCustomer = customerService.addCustomerREST(temp);
		return "";

	}

	@RequestMapping(value = "/deleteCustomerREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public String deleteCustomer(@PathVariable("id") int id) {
		Customer p = customerService.getCustomer(id);

		customerService.deleteCustomer(id);
		return "Delete customer successful!";

	}

	@RequestMapping(value = "/updateCustomerREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Customer updateCustomerREST(@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "firstName", required = true) String firstname,
			@RequestParam(value = "lastName", required = true) String lastname,
			@RequestParam(value = "dob", required = true) Date dob,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "phoneNumber", required = true) String phonenumber,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "userName", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "level", required = true) String level,
			@RequestParam(value = "status", required = true) Integer status) {

		Customer targetCustomer = customerService.getCustomer(id);
		Customer newCustomer = new Customer(id, code, firstname, lastname, dob, address, phonenumber, email, username,
				password, level, status);

		return customerService.updateCustomerREST(newCustomer);
	}

	@RequestMapping(value = "/getCustomerREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public Customer getCustomerREST(@PathVariable("id") int id) {

		Customer temp = customerService.getCustomer(id);
		temp.setOrderses(null);

		return temp;
	}

}