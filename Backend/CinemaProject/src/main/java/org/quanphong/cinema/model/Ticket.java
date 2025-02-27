package org.quanphong.cinema.model;
// Generated Dec 12, 2017 1:20:26 AM by Hibernate Tools 5.1.6.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Tickets generated by hbm2java
 */
@Entity
@Table(name = "tickets", catalog = "cinema")
public class Ticket implements java.io.Serializable {

	private Integer id;
	private MovieShow movieShows;
	private Order orders;
	private Seat seats;
	private int cost;
	private int status;

	public Ticket() {
	}

	public Ticket(MovieShow movieShows, Order orders, Seat seats, int cost, int status) {
		this.movieShows = movieShows;
		this.orders = orders;
		this.seats = seats;
		this.cost = cost;
		this.status = status;
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
	@JoinColumn(name = "mShowid", nullable = false)
	public MovieShow getMovieShows() {
		return this.movieShows;
	}

	public void setMovieShows(MovieShow movieShows) {
		this.movieShows = movieShows;
	}

	@ManyToOne
	@JoinColumn(name = "ordID", nullable = false)
	public Order getOrders() {
		return this.orders;
	}

	public void setOrders(Order orders) {
		this.orders = orders;
	}

	@ManyToOne
	@JoinColumn(name = "seatID", nullable = false)
	public Seat getSeats() {
		return this.seats;
	}

	public void setSeats(Seat seats) {
		this.seats = seats;
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

	public Ticket(Integer id, MovieShow movieShows, Order orders, Seat seats, int cost, int status) {
		super();
		this.id = id;
		this.movieShows = movieShows;
		this.orders = orders;
		this.seats = seats;
		this.cost = cost;
		this.status = status;
	}
	
	

}
