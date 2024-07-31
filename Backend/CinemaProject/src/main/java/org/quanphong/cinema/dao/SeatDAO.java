package org.quanphong.cinema.dao;

import java.util.List;

import org.quanphong.cinema.model.Seat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class SeatDAO implements iSeatdao {
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Seat> getAllSeats() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Seat> seatList = session.createQuery("from Seat").list();
		return seatList;
	}

	public Seat getSeat(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Seat seat = (Seat) session.get(Seat.class, new Integer(id));
		return seat;
	}

	public Seat addSeat(Seat seat) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(seat);
		return seat;
	}

	public void updateSeat(Seat seat) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(seat);
	}
	
	public Seat updateSeatREST(Seat seat) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(seat);
		return seat;
	}

	public void deleteSeat(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Seat p = (Seat) session.load(Seat.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}	
}
