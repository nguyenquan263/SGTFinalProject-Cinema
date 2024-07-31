package org.quanphong.cinema.service;
import java.util.List;
import org.quanphong.cinema.dao.RoomDAO;
import org.quanphong.cinema.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("roomService")
public class RoomService {

	@Autowired
	RoomDAO roomDao;
	
	@Transactional
	public List<Room> getAllRooms() {
		return roomDao.getAllRooms();
	}

	@Transactional
	public Room getRoom(int id) {
		return roomDao.getRoom(id);
	}

	@Transactional
	public void addRoom(Room room) {
		roomDao.addRoom(room);
	}

	@Transactional
	public void updateRoom(Room room) {
		roomDao.updateRoom(room);

	}

	@Transactional
	public void deleteRoom(int id) {
		roomDao.deleteRoom(id);
	}
	
	@Transactional
	public Room addRoomREST(Room room) {
		return roomDao.addRoom(room);
	}
	
	@Transactional
	public Room updateRoomREST(Room room) {
		return roomDao.updateRoomREST(room);

	}
}