package org.quanphong.cinema.service;

import java.util.List;
import org.quanphong.cinema.dao.SeatDAO;
import org.quanphong.cinema.model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("seatService")
public class SeatService {

	@Autowired
	SeatDAO seatDao;
	
	@Transactional
	public List<Seat> getAllSeats() {
		return seatDao.getAllSeats();
	}

	@Transactional
	public Seat getSeat(int id) {
		return seatDao.getSeat(id);
	}

	@Transactional
	public void addSeat(Seat seat) {
		seatDao.addSeat(seat);
	}
	
	@Transactional
	public Seat addSeatREST(Seat seat) {
		return seatDao.addSeat(seat);
	}

	@Transactional
	public void updateSeat(Seat seat) {
		seatDao.updateSeat(seat);

	}
	
	@Transactional
	public Seat updateSeatREST(Seat seat) {
		return seatDao.updateSeatREST(seat);

	}

	@Transactional
	public void deleteSeat(int id) {
		seatDao.deleteSeat(id);
	}
}