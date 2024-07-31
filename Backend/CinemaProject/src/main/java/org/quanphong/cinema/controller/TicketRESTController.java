package org.quanphong.cinema.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.quanphong.cinema.model.Customer;
import org.quanphong.cinema.model.MovieShow;
import org.quanphong.cinema.model.Order;
import org.quanphong.cinema.model.Seat;
import org.quanphong.cinema.model.Ticket;
import org.quanphong.cinema.service.MovieShowService;
import org.quanphong.cinema.service.OrderService;
import org.quanphong.cinema.service.SeatService;
import org.quanphong.cinema.service.TicketService;
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
public class TicketRESTController {

	@Autowired
	TicketService ticketService;
	@Autowired
	OrderService ordService;
	@Autowired
	MovieShowService movShowService;
	@Autowired
	SeatService seatService;

	@RequestMapping(value = "/getAllTicketsREST", method = RequestMethod.GET, produces = "application/json")

	public List<Ticket> getTickets(Model model) {
		List<Ticket> listOfTickets = ticketService.getAllTickets();
		System.out.println(listOfTickets.size());
		for (int i = 0; i < listOfTickets.size(); i++) {
			listOfTickets.get(i).getMovieShows().setTicketses(null);
			listOfTickets.get(i).getOrders().setTicketses(null);
			listOfTickets.get(i).getSeats().setTicketses(null);
			listOfTickets.get(i).getMovieShows().getMovies().setMovieShowses(null);
			listOfTickets.get(i).getMovieShows().getRooms().setMovieShowses(null);
			listOfTickets.get(i).getMovieShows().getRooms().setSeatses(null);
			listOfTickets.get(i).getOrders().getCustomers().setOrderses(null);
			if (listOfTickets.get(i).getOrders().getEmployees() != null) {
				listOfTickets.get(i).getOrders().getEmployees().setNewses(null);
				listOfTickets.get(i).getOrders().getEmployees().setOrderses(null);
			}
			else listOfTickets.get(i).getOrders().setEmployees(null);

			listOfTickets.get(i).getOrders().setOrderDetailSnacks(null);
		}

		return listOfTickets;
	}

	// --------------------------------------------------------------------------------------------------------

	@RequestMapping(value = "/addTicketREST", method = RequestMethod.POST, headers = "Accept=application/json")
	public String addTicketREST(@RequestParam(value = "ordid", required = true) Integer ordID,
			@RequestParam(value = "seatid", required = true) Integer seatID,
			@RequestParam(value = "mshowid", required = true) Integer mshowID,
			@RequestParam(value = "cost", required = true) Integer Cost,
			@RequestParam(value = "status", required = true) Integer status) {

		Order targetOrder = ordService.getOrder(ordID);
		Seat targetSeat = seatService.getSeat(seatID);
		MovieShow targetMovShow = movShowService.getMovieShow(mshowID);

		Ticket temp = new Ticket(targetMovShow, targetOrder, targetSeat, Cost, status);
		Ticket newTicket = ticketService.addTicketREST(temp);
		return "";

	}

	@RequestMapping(value = "/deleteTicketREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public String deleteTicket(@PathVariable("id") int id) {
		Ticket p = ticketService.getTicket(id);

		ticketService.deleteTicket(id);
		return "Delete customer successful!";

	}

	@RequestMapping(value = "/updateTicketREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Ticket updateTicketREST(@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "ordid", required = true) Integer ordID,
			@RequestParam(value = "seatid", required = true) Integer seatID,
			@RequestParam(value = "mshowid", required = true) Integer mshowID,
			@RequestParam(value = "cost", required = true) Integer Cost,
			@RequestParam(value = "status", required = true) Integer status) {

		Order targetOrder = ordService.getOrder(ordID);
		targetOrder.getCustomers().setOrderses(null);
		targetOrder.getEmployees().setNewses(null);
		targetOrder.getEmployees().setOrderses(null);
		targetOrder.setTicketses(null);
		targetOrder.setOrderDetailSnacks(null);
		Seat targetSeat = seatService.getSeat(seatID);
		targetSeat.getRooms().setSeatses(null);
		targetSeat.getRooms().setMovieShowses(null);
		targetSeat.setTicketses(null);
		MovieShow targetMovShow = movShowService.getMovieShow(mshowID);
		targetMovShow.getMovies().setMovieShowses(null);
		targetMovShow.getRooms().setMovieShowses(null);
		targetMovShow.getRooms().setSeatses(null);
		targetMovShow.setTicketses(null);
		Ticket temp = new Ticket(id, targetMovShow, targetOrder, targetSeat, Cost, status);

		return ticketService.updateTicketREST(temp);
	}

	@RequestMapping(value = "/getTicketREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public Ticket getTicketREST(@PathVariable("id") int id) {

		Ticket temp = ticketService.getTicket(id);

		temp.getMovieShows().setTicketses(null);
		temp.getOrders().setTicketses(null);
		temp.getSeats().setTicketses(null);
		temp.getMovieShows().getMovies().setMovieShowses(null);
		temp.getMovieShows().getRooms().setMovieShowses(null);
		temp.getMovieShows().getRooms().setSeatses(null);
		temp.getOrders().getCustomers().setOrderses(null);

		if (temp.getOrders().getEmployees() != null) {
			temp.getOrders().getEmployees().setNewses(null);
			temp.getOrders().getEmployees().setOrderses(null);
		}
		else temp.getOrders().setEmployees(null);
		temp.getOrders().setOrderDetailSnacks(null);
		return temp;
	}

	@RequestMapping(value = "/getTicketsRESTbyMovieShowID/{msid}", method = RequestMethod.GET, produces = "application/json")
	public List<Ticket> getTicketsRESTbyMovieShowID(@PathVariable("msid") int msid) {
		List<Ticket> targetTickets = new ArrayList<>();
		List<Ticket> allTicket = ticketService.getAllTickets();
		Ticket temp;
		for (int i = 0; i < allTicket.size(); i++) {
			if ((allTicket.get(i).getMovieShows().getId() == msid) 
			&& (allTicket.get(i).getOrders().getEmployees() != null)
			&& (allTicket.get(i).getOrders().getStatus() == 1)) {
				temp = allTicket.get(i);

				temp.getMovieShows().setTicketses(null);
				temp.getOrders().setTicketses(null);
				temp.getSeats().setTicketses(null);
				temp.getMovieShows().getMovies().setMovieShowses(null);
				temp.getMovieShows().getRooms().setMovieShowses(null);
				temp.getMovieShows().getRooms().setSeatses(null);
				temp.getOrders().getCustomers().setOrderses(null);
//				if (temp.getOrders().getEmployees() != null) {
//					temp.getOrders().getEmployees().setNewses(null);
//					temp.getOrders().getEmployees().setOrderses(null);
//				}
				temp.getOrders().getEmployees().setNewses(null);
				temp.getOrders().getEmployees().setOrderses(null);
				temp.getOrders().setOrderDetailSnacks(null);

				targetTickets.add(temp);

			}
		}

		return targetTickets;
	}
	
	@RequestMapping(value = "/getTicketsRESTbyOrderID/{ordid}", method = RequestMethod.GET, produces = "application/json")
	public List<Ticket> getTicketsRESTbyOrderID(@PathVariable("ordid") int ordid) {
		List<Ticket> targetTickets = new ArrayList<>();
		List<Ticket> allTicket = ticketService.getAllTickets();
		Ticket temp;
		for (int i = 0; i < allTicket.size(); i++) {
			if (allTicket.get(i).getOrders().getId() == ordid) {
				temp = allTicket.get(i);

				temp.getMovieShows().setTicketses(null);
				temp.getOrders().setTicketses(null);
				temp.getSeats().setTicketses(null);
				temp.getMovieShows().getMovies().setMovieShowses(null);
				temp.getMovieShows().getRooms().setMovieShowses(null);
				temp.getMovieShows().getRooms().setSeatses(null);
				temp.getOrders().getCustomers().setOrderses(null);
				if (temp.getOrders().getEmployees() != null) {
					temp.getOrders().getEmployees().setNewses(null);
					temp.getOrders().getEmployees().setOrderses(null);
				}
				temp.getOrders().setOrderDetailSnacks(null);

				targetTickets.add(temp);

			}
		}

		return targetTickets;
	}

}