package org.quanphong.cinema.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.quanphong.cinema.model.Customer;
import org.quanphong.cinema.model.Employee;

import org.quanphong.cinema.model.Order;
import org.quanphong.cinema.model.revenueBytime;
import org.quanphong.cinema.model.revenueMovieShow;
import org.quanphong.cinema.service.CustomerService;
import org.quanphong.cinema.service.EmployeeService;
import org.quanphong.cinema.service.OrderDetailSnackService;
import org.quanphong.cinema.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class OrderRESTController {

	@Autowired
	OrderService orderService;

	@Autowired
	EmployeeService empService;

	@Autowired
	CustomerService cusService;

	@RequestMapping(value = "/getAllOrdersREST", method = RequestMethod.GET, produces = "application/json")

	public List<Order> getOrders(Model model) {
		List<Order> listOfOrders = orderService.getAllOrders();
		for (int i = 0; i < listOfOrders.size(); i++) {

			listOfOrders.get(i).getCustomers().setOrderses(null);
			if (listOfOrders.get(i).getEmployees() != null) {
				listOfOrders.get(i).getEmployees().setOrderses(null);
				listOfOrders.get(i).getEmployees().setNewses(null);
			}
			listOfOrders.get(i).setTicketses(null);
			listOfOrders.get(i).setOrderDetailSnacks(null);
		}
		return listOfOrders;
	}
	
	@RequestMapping(value = "/getAllOrdersNewREST", method = RequestMethod.GET, produces = "application/json")

	public List<Order> getOrdersNew(Model model) {
		List<Order> listOfOrders = orderService.getAllOrdersNew();
		for (int i = 0; i < listOfOrders.size(); i++) {

			listOfOrders.get(i).getCustomers().setOrderses(null);
			if (listOfOrders.get(i).getEmployees() != null) {
				listOfOrders.get(i).getEmployees().setOrderses(null);
				listOfOrders.get(i).getEmployees().setNewses(null);
			}
			listOfOrders.get(i).setTicketses(null);
			listOfOrders.get(i).setOrderDetailSnacks(null);
		}
		return listOfOrders;
	}
	
	@RequestMapping(value = "/getAllOrdersbyTimeREST", method = RequestMethod.GET, produces = "application/json")

	public List<Order> getOrdersbyTime(Model model) {
		List<Order> listOfOrders = orderService.getAllOrdersbyTime();
		for (int i = 0; i < listOfOrders.size(); i++) {

			listOfOrders.get(i).getCustomers().setOrderses(null);
			if (listOfOrders.get(i).getEmployees() != null) {
				listOfOrders.get(i).getEmployees().setOrderses(null);
				listOfOrders.get(i).getEmployees().setNewses(null);
			}
			listOfOrders.get(i).setTicketses(null);
			listOfOrders.get(i).setOrderDetailSnacks(null);
		}
		return listOfOrders;
	}

	// --------------------------------------------------------------------------------------------------------

	@RequestMapping(value = "/addOrderREST", method = RequestMethod.POST, headers = "Accept=application/json")
	public int addOrderREST(@RequestParam(value = "code", required = true) String Code,
			@RequestParam(value = "orddate", required = true) Date ordDate,
			@RequestParam(value = "ordtime", required = true) Time ordTime,
			@RequestParam(value = "empid", required = false) Integer empID,
			@RequestParam(value = "cusid", required = true) Integer cusID,
			@RequestParam(value = "status", required = true) Integer status) {
		Employee targetEmployee;
		if (empID != null) {
			targetEmployee = empService.getEmployee(empID);
		} else
			targetEmployee = null;
		Customer targetCustomer = cusService.getCustomer(cusID);
		Order temp = new Order(targetCustomer, targetEmployee, Code, ordDate, ordTime, status);
		Order newOrder = orderService.addOrderREST(temp);
		return newOrder.getId();

	}

	@RequestMapping(value = "/deleteOrderREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public String deleteOrderREST(@PathVariable("id") int id) {

		Order p = orderService.getOrder(id);

		orderService.deleteOrder(id);
		return "Delete customer successful!";

	}

	@RequestMapping(value = "/updateOrderREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Order updateOrderREST(@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "code", required = true) String Code,
			@RequestParam(value = "orddate", required = true) Date ordDate,
			@RequestParam(value = "ordtime", required = true) Time ordTime,
			@RequestParam(value = "empid", required = false) Integer empID,
			@RequestParam(value = "cusid", required = true) Integer cusID,
			@RequestParam(value = "status", required = true) Integer status) {
		Employee targetEmployee;
		if (empID != null) {
			targetEmployee = empService.getEmployee(empID);
			targetEmployee.setOrderses(null);
			targetEmployee.setNewses(null);
		} else {
			targetEmployee = null;
		}

		Customer targetCustomer = cusService.getCustomer(cusID);
		targetCustomer.setOrderses(null);

		Order newOrder = new Order(id, targetCustomer, targetEmployee, Code, ordDate, ordTime, status);

		newOrder.setTicketses(null);
		newOrder.setOrderDetailSnacks(null);

		return orderService.updateOrderREST(newOrder);
	}

	@RequestMapping(value = "/getOrderREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public Order getOrderREST(@PathVariable("id") int id) {

		Order temp = orderService.getOrder(id);

		temp.getCustomers().setOrderses(null);
		temp.getEmployees().setOrderses(null);
		temp.getEmployees().setNewses(null);
		temp.setTicketses(null);
		temp.setOrderDetailSnacks(null);

		return temp;
	}
	
	@RequestMapping(value = "/expireOrder/{id}", method = RequestMethod.GET, produces = "application/json")
	public void expireOrderREST(@PathVariable("id") int id) {

		Order temp = orderService.getOrder(id);

		temp.setStatus(-1);
		
		orderService.updateOrderREST(temp);

		
	}
	
	@RequestMapping(value = "/getAllorderRevenue",method=RequestMethod.GET,produces = "application/json")
	public List<revenueBytime> getAllorderRevenue(Model model) {
		List<revenueBytime> listOfRevenue = orderService.getAllrevenueBytime();
		return listOfRevenue;
	}

}