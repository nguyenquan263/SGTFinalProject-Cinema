package org.quanphong.cinema.dao;

import java.util.List;

import org.quanphong.cinema.model.Room;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class RoomDAO implements iRoomdao {
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Room> getAllRooms() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Room> roomList = session.createQuery("from Room").list();
		return roomList;
	}

	public Room getRoom(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Room room = (Room) session.get(Room.class, new Integer(id));
		return room;
	}

	public Room addRoom(Room room) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(room);
		return room;
	}

	public void updateRoom(Room room) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(room);
	}
	
	public Room updateRoomREST(Room room) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(room);
		return room;
	}

	public void deleteRoom(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Room p = (Room) session.load(Room.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}	
}
