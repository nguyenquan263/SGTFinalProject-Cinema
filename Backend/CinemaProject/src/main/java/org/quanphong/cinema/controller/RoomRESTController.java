package org.quanphong.cinema.controller;

import java.lang.annotation.Repeatable;
import java.util.List;

import org.quanphong.cinema.model.Room;
import org.quanphong.cinema.model.Room;
import org.quanphong.cinema.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomRESTController {

	@Autowired
	RoomService roomService;

	@RequestMapping(value = "/getAllRoomsREST", method = RequestMethod.GET, produces = "application/json")

	public List<Room> getRooms(Model model) {
		List<Room> listOfRooms = roomService.getAllRooms();
		for (int i = 0; i < listOfRooms.size(); i++) {
			listOfRooms.get(i).setSeatses(null);
			listOfRooms.get(i).setMovieShowses(null);

		}
		return listOfRooms;
	}

	@RequestMapping(value = "/addRoomsREST", method = RequestMethod.POST, headers = "Accept=application/json")
	public String addRoomsREST(@RequestParam(value = "status", required = true) Integer status,
			@RequestParam(value = "name", required = true) String name) {

		Room temp = new Room(name, status);
		Room newRoom = roomService.addRoomREST(temp);
		return "";

	}

	@RequestMapping(value = "/deleteRoomREST/{id}", method = RequestMethod.GET, produces = "application/json")
	public String deleteRoom(@PathVariable("id") int id) {
		Room p = roomService.getRoom(id);

		roomService.deleteRoom(id);
		return "Delete room successful!";

	}

	@RequestMapping(value = "/updateRoomREST", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	public Room updateRoomREST(@RequestParam(value = "id", required = true) Integer id, @RequestParam(value = "status", required = true) Integer status,
			@RequestParam(value = "name", required = true) String name) {
		
		
		Room targetRoom = roomService.getRoom(id);
		Room newRoom = new Room(id, name, status);
	
		return roomService.updateRoomREST(newRoom);
	}
	
	@RequestMapping(value = "/getRoomREST/{id}", method = RequestMethod.GET
			, produces = "application/json")
	public Room getRoomREST(@PathVariable("id") int id) {
		Room temp = roomService.getRoom(id);
		temp.setSeatses(null);
		temp.setMovieShowses(null);
		return temp;
	}

}