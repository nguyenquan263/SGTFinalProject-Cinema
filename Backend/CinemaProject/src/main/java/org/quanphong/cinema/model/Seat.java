package org.quanphong.cinema.model;
// Generated Dec 12, 2017 1:20:26 AM by Hibernate Tools 5.1.6.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Seats generated by hbm2java
 */
@Entity
@Table(name = "seats", catalog = "cinema")
public class Seat implements java.io.Serializable {

	private Integer id;
	private Room rooms;
	private char roww;
	private int columnn;
	private String typee;
	private int cost;
	private int status;
	private Set<Ticket> ticketses = new HashSet<Ticket>(0);

	public Seat() {
	}

	public Seat(Room rooms, char roww, int columnn, String typee, int cost, int status) {
		this.rooms = rooms;
		this.roww = roww;
		this.columnn = columnn;
		this.typee = typee;
		this.cost = cost;
		this.status = status;
	}

	public Seat(Room rooms, char roww, int columnn, String typee, int cost, int status, Set<Ticket> ticketses) {
		this.rooms = rooms;
		this.roww = roww;
		this.columnn = columnn;
		this.typee = typee;
		this.cost = cost;
		this.status = status;
		this.ticketses = ticketses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "roomID", nullable = false)
	public Room getRooms() {
		return this.rooms;
	}

	public void setRooms(Room rooms) {
		this.rooms = rooms;
	}

	@Column(name = "Roww", nullable = false, length = 1)
	public char getRoww() {
		return this.roww;
	}

	public void setRoww(char roww) {
		this.roww = roww;
	}

	@Column(name = "Columnn", nullable = false)
	public int getColumnn() {
		return this.columnn;
	}

	public void setColumnn(int columnn) {
		this.columnn = columnn;
	}

	@Column(name = "Typee", nullable = false, length = 10)
	public String getTypee() {
		return this.typee;
	}

	public void setTypee(String typee) {
		this.typee = typee;
	}

	@Column(name = "Cost", nullable = false)
	public int getCost() {
		return this.cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	@Column(name = "Status", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@OneToMany(mappedBy = "seats")
	public Set<Ticket> getTicketses() {
		return this.ticketses;
	}

	public void setTicketses(Set<Ticket> ticketses) {
		this.ticketses = ticketses;
	}

	public Seat(Integer id, Room rooms, char roww, int columnn, String typee, int cost, int status) {
		super();
		this.id = id;
		this.rooms = rooms;
		this.roww = roww;
		this.columnn = columnn;
		this.typee = typee;
		this.cost = cost;
		this.status = status;
	}
	
	

}
