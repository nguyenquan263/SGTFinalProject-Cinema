package org.quanphong.cinema.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.quanphong.cinema.model.Employee;
import org.quanphong.cinema.model.Movie;
import org.quanphong.cinema.model.MovieShow;
import org.quanphong.cinema.model.New;
import org.quanphong.cinema.model.Room;
import org.quanphong.cinema.service.EmployeeService;
import org.quanphong.cinema.service.NewService;
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
public class NewRESTController {

	@Autowired
	NewService newService;

	@Autowired
	EmployeeService empService;
	@CrossOrigin
	@RequestMapping(value = "/getAllNewsREST", method = RequestMethod.GET, produces = "application/json")

	public List<New> getNews(Model model) {
		List<New> listOfNews = newService.getAllNews();
		for (int i = 0; i < listOfNews.size(); i++) {
			listOfNews.get(i).getEmployees().setNewses(null);
			listOfNews.get(i).getEmployees().setOrderses(null);
		}
		return listOfNews;
	}

	// --------------------------------------------------------------------------------------------------------

	@RequestMapping(value = "/addNewREST", method = RequestMethod.POST, headers = "Accept=application/json")
	public String addNewREST(@RequestParam(value = "title", required = true) String Title,
			@RequestParam(value = "detail", required = true) String Detail,
			@RequestParam(value = "postdate", required = true) Date postDate,
			@RequestParam(value = "posttime", required = true) Time postTime,
			@RequestParam(value = "empid", required = false) Integer empID,
			@RequestParam(value = "imagelink1", required = false) String imageLink1,
			@RequestParam(value = "imagelink2", required = false) String imageLink2,
			@RequestParam(value = "imagelink3", required = false) String imageLink3,
			@RequestParam(value = "status", required = true) Integer status) {
		Employee targetEmp = empService.getEmployee(empID);

		New temp = new New(targetEmp, Title, Detail, postDate, postTime, imageLink1, imageLink2, imageLink3, status);

		New newNew = newService.addNewREST(temp);
		return "";

	}

	@RequestMapping(value = "/deleteNewREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public String deleteNewREST(@PathVariable("id") int id) {
		New p = newService.getNew(id);

		newService.deleteNew(id);
		return "Delete customer successful!";

	}

	@RequestMapping(value = "/updateNewREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public String updateNewREST(@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "title", required = true) String Title,
			@RequestParam(value = "detail", required = true) String Detail,
			@RequestParam(value = "postdate", required = true) Date postDate,
			@RequestParam(value = "posttime", required = true) Time postTime,
			@RequestParam(value = "empid", required = false) Integer empID,
			@RequestParam(value = "imagelink1", required = false) String imageLink1,
			@RequestParam(value = "imagelink2", required = false) String imageLink2,
			@RequestParam(value = "imagelink3", required = false) String imageLink3,
			@RequestParam(value = "status", required = true) Integer status) {
		Employee targetEmployee;
		New targetNew = newService.getNew(id);
		if (empID != null) {
		targetEmployee = empService.getEmployee(empID);
		
		targetEmployee.setNewses(null);
		targetEmployee.setOrderses(null);
		} else targetEmployee = null;

		New newNew = new New(id, targetEmployee, Title, Detail, postDate, postTime, imageLink1, imageLink2,
				imageLink3, status);
		System.out.println(newNew.getId());
		newService.updateNewREST(newNew);
		return "";
	}

	@RequestMapping(value = "/getNewREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public New getMovieShowREST(@PathVariable("id") int id) {

		New temp = newService.getNew(id);
		temp.getEmployees().setNewses(null);
		temp.getEmployees().setOrderses(null);
		return temp;
	}

}