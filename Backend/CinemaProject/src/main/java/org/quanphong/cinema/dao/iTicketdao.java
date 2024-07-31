package org.quanphong.cinema.dao;

import java.util.List;

import org.quanphong.cinema.model.Ticket;
import org.hibernate.SessionFactory;

public interface iTicketdao {
	public void setSessionFactory(SessionFactory sf);
	public List<Ticket> getAllTickets();
	public Ticket getTicket(int id);
	public Ticket addTicket(Ticket ticket);
	public void updateTicket(Ticket ticket);
	public void deleteTicket(int id);
}
