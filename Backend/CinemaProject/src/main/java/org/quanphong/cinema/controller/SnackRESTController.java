package org.quanphong.cinema.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.quanphong.cinema.model.Customer;
import org.quanphong.cinema.model.Employee;
import org.quanphong.cinema.model.Order;
import org.quanphong.cinema.model.Snack;
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
public class SnackRESTController {

	@Autowired
	SnackService snackService;

	@RequestMapping(value = "/getAllSnacksREST", method = RequestMethod.GET, produces = "application/json")

	public List<Snack> getSnacks(Model model) {
		List<Snack> listOfSnacks = snackService.getAllSnacks();
		for (int i = 0; i < listOfSnacks.size(); i++) {
			listOfSnacks.get(i).setOrderDetailSnacks(null);
		}
		return listOfSnacks;
	}

	// --------------------------------------------------------------------------------------------------------

	@RequestMapping(value = "/addSnackREST", method = RequestMethod.POST, headers = "Accept=application/json")
	public String addSnackREST(@RequestParam(value = "name", required = true) String Name,
			@RequestParam(value = "imagelink", required = true) String imageLink,
			@RequestParam(value = "cost", required = true) Integer Cost,
			@RequestParam(value = "status", required = true) Integer status) {

		
		Snack temp = new Snack(Name, imageLink, Cost, status);
		Snack newSnack = snackService.addSnackREST(temp);
		return "";

	}

	@RequestMapping(value = "/deleteSnackREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public String deleteSnackREST(@PathVariable("id") int id) {

		Snack p = snackService.getSnack(id);

		snackService.deleteSnack(id);
		return "Delete customer successful!";

	}

	@RequestMapping(value = "/updateSnackREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Snack updateSnackREST(@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "name", required = true) String Name,
			@RequestParam(value = "imagelink", required = true) String imageLink,
			@RequestParam(value = "cost", required = true) Integer Cost,
			@RequestParam(value = "status", required = true) Integer status) {

		Snack newSnack = new Snack(id, Name, imageLink, Cost, status);

		newSnack.setOrderDetailSnacks(null);
		

		return snackService.updateSnackREST(newSnack);
	}

	@RequestMapping(value = "/getSnackREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public Snack getSnackREST(@PathVariable("id") int id) {

		Snack temp = snackService.getSnack(id);

		temp.setOrderDetailSnacks(null);

		return temp;
	}

}