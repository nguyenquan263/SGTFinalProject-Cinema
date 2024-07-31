package org.quanphong.cinema.dao;

import java.util.List;

import org.quanphong.cinema.model.Room;
import org.hibernate.SessionFactory;

public interface iRoomdao {
	public void setSessionFactory(SessionFactory sf);
	public List<Room> getAllRooms();
	public Room getRoom(int id);
	public Room addRoom(Room room);
	public void updateRoom(Room room);
	public void deleteRoom(int id);
}
