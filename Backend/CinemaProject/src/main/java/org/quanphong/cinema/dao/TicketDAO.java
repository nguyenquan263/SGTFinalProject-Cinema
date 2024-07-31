package org.quanphong.cinema.dao;

import java.util.List;

import org.quanphong.cinema.model.Ticket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class TicketDAO implements iTicketdao {
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Ticket> getAllTickets() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Ticket> ticketList = session.createQuery("from Ticket").list();
		return ticketList;
	}

	public Ticket getTicket(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Ticket ticket = (Ticket) session.get(Ticket.class, new Integer(id));
		return ticket;
	}

	public Ticket addTicket(Ticket ticket) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(ticket);
		return ticket;
	}

	public void updateTicket(Ticket ticket) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(ticket);
	}
	
	public Ticket updateTicketREST(Ticket ticket) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(ticket);
		return ticket;
	}

	public void deleteTicket(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Ticket p = (Ticket) session.load(Ticket.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}	
}
