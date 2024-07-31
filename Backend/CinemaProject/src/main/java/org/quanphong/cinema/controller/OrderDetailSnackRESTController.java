package org.quanphong.cinema.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.quanphong.cinema.model.Employee;
import org.quanphong.cinema.model.OrderDetailSnack;
import org.quanphong.cinema.model.Order;
import org.quanphong.cinema.model.OrderDetailSnack;
import org.quanphong.cinema.model.Snack;
import org.quanphong.cinema.service.OrderDetailSnackService;
import org.quanphong.cinema.service.OrderService;
import org.quanphong.cinema.service.SnackService;
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
public class OrderDetailSnackRESTController {


	@Autowired
	OrderDetailSnackService orddsService;
	@Autowired
	OrderService ordService;
	@Autowired
	SnackService snackService;
	
	@RequestMapping(value = "/getAllOrderDetailSnacksREST",method=RequestMethod.GET,produces = "application/json")

	public List<OrderDetailSnack> getOrderDetailSnacks(Model model) {
		List<OrderDetailSnack> listOfOrderDetailSnacks = orddsService.getAllOrderDetailSnacks();
		for (int i = 0 ; i < listOfOrderDetailSnacks.size() ; i++) {
				listOfOrderDetailSnacks.get(i).getSnacks().setOrderDetailSnacks(null);
				listOfOrderDetailSnacks.get(i).getOrders().setOrderDetailSnacks(null);
				listOfOrderDetailSnacks.get(i).getOrders().getCustomers().setOrderses(null);
				if (listOfOrderDetailSnacks.get(i).getOrders().getEmployees() != null) {
					listOfOrderDetailSnacks.get(i).getOrders().getEmployees().setNewses(null);
					listOfOrderDetailSnacks.get(i).getOrders().getEmployees().setOrderses(null);
				}
				else listOfOrderDetailSnacks.get(i).getOrders().setEmployees(null);
				listOfOrderDetailSnacks.get(i).getOrders().setTicketses(null);
		}
		return listOfOrderDetailSnacks;
	}
	
	// --------------------------------------------------------------------------------------------------------

		@RequestMapping(value = "/addOrderDetailSnackREST", method = RequestMethod.POST, headers = "Accept=application/json")
		public String addOrderDetailSnackREST(@RequestParam(value = "ordid", required = true) Integer ordID,
				@RequestParam(value = "snackid", required = true) Integer snackID,
				@RequestParam(value = "quantity", required = true) Integer Quantity,
				@RequestParam(value = "cost", required = true) Integer Cost,
				@RequestParam(value = "status", required = true) Integer status) {
			
			Snack targetSnack = snackService.getSnack(snackID);
			
			Order targetOrder = ordService.getOrder(ordID);

			OrderDetailSnack temp = new OrderDetailSnack(targetOrder, targetSnack, Quantity, Cost, status);
			OrderDetailSnack newOrdds = orddsService.addOrderDetailSnackREST(temp);
			return "";

		}

		@RequestMapping(value = "/deleteOrderDetailSnackREST/{id}", method = RequestMethod.GET, produces = "application/json")
		public String deleteOrderDetailSnackREST(@PathVariable("id") int id) {
			OrderDetailSnack ordds = orddsService.getOrderDetailSnack(id);

			orddsService.deleteOrderDetailSnack(id);
			return "Delete order detail snack successful!";

		}

		@RequestMapping(value = "/updateOrderDetailSnackREST", method = RequestMethod.POST, produces = "application/json", consumes = {
				"application/x-www-form-urlencoded", "multipart/form-data" })
		public OrderDetailSnack updateOrderDetailSnackREST(@RequestParam(value = "id", required = true) Integer id,
				@RequestParam(value = "ordid", required = true) Integer ordID,
				@RequestParam(value = "snackid", required = true) Integer snackID,
				@RequestParam(value = "quantity", required = true) Integer Quantity,
				@RequestParam(value = "cost", required = true) Integer Cost,
				@RequestParam(value = "status", required = true) Integer status) {
			System.out.println(id);
			OrderDetailSnack ordds= orddsService.getOrderDetailSnack(id);
			
			Order targetOrd = ordService.getOrder(ordID);
			
			targetOrd.setOrderDetailSnacks(null);
			targetOrd.getCustomers().setOrderses(null);
			targetOrd.getEmployees().setNewses(null);
			targetOrd.getEmployees().setOrderses(null);
			targetOrd.setTicketses(null);
			
			Snack targetSnack = snackService.getSnack(snackID);
			
			targetSnack.setOrderDetailSnacks(null);

			OrderDetailSnack OrderDetailSnackOrdds = new OrderDetailSnack(id, targetOrd, targetSnack, Quantity, Cost, status);

			return orddsService.updateOrderDetailSnackREST(OrderDetailSnackOrdds);
		}

		@RequestMapping(value = "/getOrderDetailSnackREST/{id}", method = RequestMethod.GET, produces = "application/json")
		public OrderDetailSnack getOrderDetailSnackREST(@PathVariable("id") int id) {

			OrderDetailSnack temp = orddsService.getOrderDetailSnack(id);
			temp.getSnacks().setOrderDetailSnacks(null);
			temp.getOrders().setOrderDetailSnacks(null);
			temp.getOrders().getCustomers().setOrderses(null);
			temp.getOrders().getEmployees().setNewses(null);
			temp.getOrders().getEmployees().setOrderses(null);
			temp.getOrders().setTicketses(null);
			return temp;
		}

	

}