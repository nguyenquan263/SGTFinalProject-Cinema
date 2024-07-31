package org.quanphong.cinema.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.quanphong.cinema.model.Employee;
import org.quanphong.cinema.model.Room;
import org.quanphong.cinema.model.Seat;
import org.quanphong.cinema.model.Seat;
import org.quanphong.cinema.service.RoomService;
import org.quanphong.cinema.service.SeatService;
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
public class SeatRESTController {


	@Autowired
	SeatService seatService;
	
	@Autowired
	RoomService roomService;
	
	@RequestMapping(value = "/getAllSeatsREST",method=RequestMethod.GET,produces = "application/json")

	public List<Seat> getSeats(Model model) {
		List<Seat> listOfSeats = seatService.getAllSeats();
		for(int i = 0; i<listOfSeats.size(); i++) {
			listOfSeats.get(i).setTicketses(null);
			listOfSeats.get(i).getRooms().setSeatses(null);
			listOfSeats.get(i).getRooms().setMovieShowses(null);
		}
		return listOfSeats;
	}

	// --------------------------------------------------------------------------------------------------------

	@RequestMapping(value = "/addSeatREST", method = RequestMethod.POST, headers = "Accept=application/json")
	public String addSeatREST(@RequestParam(value = "roomid", required = true) int roomID,
			@RequestParam(value = "row", required = true) Character Row,
			@RequestParam(value = "column", required = true) Integer Column,
			@RequestParam(value = "type", required = true) String Type,
			@RequestParam(value = "cost", required = false) Integer Cost,
			@RequestParam(value = "status", required = true) Integer status) {
		Room targetRoom = roomService.getRoom(roomID);

		Seat temp = new Seat(targetRoom, Row, Column, Type, Cost, status);

		Seat newSeat = seatService.addSeatREST(temp);
		return "";

	}

	@RequestMapping(value = "/deleteSeatREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public String deleteSeatREST(@PathVariable("id") int id) {
		Seat p = seatService.getSeat(id);

		seatService.deleteSeat(id);
		return "Delete customer successful!";

	}

	@RequestMapping(value = "/updateSeatREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Seat updateSeatREST(@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "roomid", required = true) int roomID,
			@RequestParam(value = "row", required = true) Character Row,
			@RequestParam(value = "column", required = true) Integer Column,
			@RequestParam(value = "type", required = true) String Type,
			@RequestParam(value = "cost", required = false) Integer Cost,
			@RequestParam(value = "status", required = true) Integer status) {

		Room targetRoom = roomService.getRoom(roomID);
		targetRoom.setMovieShowses(null);
		targetRoom.setSeatses(null);
		Seat newSeat = new Seat(id, targetRoom, Row, Column, Type, Cost, status);
		newSeat.setTicketses(null);
		return seatService.updateSeatREST(newSeat);
	}

	@RequestMapping(value = "/getSeatREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public Seat getSeatREST(@PathVariable("id") int id) {

		Seat temp = seatService.getSeat(id);
		temp.setTicketses(null);
		temp.getRooms().setSeatses(null);
		temp.getRooms().setMovieShowses(null);
		return temp;
	}
	
	@RequestMapping(value = "/getSeatRESTbyRowColumnRoom/{roww}&{columnn}&{roomid}", method = RequestMethod.GET, produces = "application/json")
	public Seat getSeatRESTbyRowColumnRoom(@PathVariable("roomid") int roomid
			, @PathVariable("roww") char row
			, @PathVariable("columnn") int column) {

//		Seat temp = seatService.getSeat(id);
//		temp.setTicketses(null);
//		temp.getRooms().setSeatses(null);
//		temp.getRooms().setMovieShowses(null);
//		return temp;
		Seat targetSeat = null;
		List<Seat> seatArr = seatService.getAllSeats();
		for (int i = 0; i < seatArr.size();i++) {
			Seat currSeat = seatArr.get(i);
			if ((currSeat.getRoww() == row) && (currSeat.getColumnn() == column) && (currSeat.getRooms().getId() == roomid)) {
				targetSeat = currSeat;
				break;
			}
		}
		targetSeat.getRooms().setMovieShowses(null);
		targetSeat.getRooms().setSeatses(null);
		targetSeat.setTicketses(null);
		return targetSeat;
	}
	
	

	

}