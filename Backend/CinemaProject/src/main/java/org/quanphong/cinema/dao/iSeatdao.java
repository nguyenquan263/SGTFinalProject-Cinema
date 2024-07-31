package org.quanphong.cinema.dao;

import java.util.List;

import org.quanphong.cinema.model.Seat;
import org.hibernate.SessionFactory;

public interface iSeatdao {
	public void setSessionFactory(SessionFactory sf);
	public List<Seat> getAllSeats();
	public Seat getSeat(int id);
	public Seat addSeat(Seat seat);
	public void updateSeat(Seat seat);
	public void deleteSeat(int id);
}
