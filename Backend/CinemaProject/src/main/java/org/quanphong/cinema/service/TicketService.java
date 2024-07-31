package org.quanphong.cinema.service;

import java.util.List;
import org.quanphong.cinema.dao.TicketDAO;
import org.quanphong.cinema.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ticketService")
public class TicketService {

	@Autowired
	TicketDAO ticketDao;
	
	@Transactional
	public List<Ticket> getAllTickets() {
		return ticketDao.getAllTickets();
	}

	@Transactional
	public Ticket getTicket(int id) {
		return ticketDao.getTicket(id);
	}

	@Transactional
	public void addTicket(Ticket ticket) {
		ticketDao.addTicket(ticket);
	}
	
	@Transactional
	public Ticket addTicketREST(Ticket ticket) {
		return ticketDao.addTicket(ticket);
	}

	@Transactional
	public void updateTicket(Ticket ticket) {
		ticketDao.updateTicket(ticket);

	}
	
	@Transactional
	public Ticket updateTicketREST(Ticket ticket) {
		return ticketDao.updateTicketREST(ticket);

	}

	@Transactional
	public void deleteTicket(int id) {
		ticketDao.deleteTicket(id);
	}
}