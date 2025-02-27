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
 * OrderDetailSnack generated by hbm2java
 */
@Entity
@Table(name = "order_detail_snack", catalog = "cinema")
public class OrderDetailSnack implements java.io.Serializable {

	private Integer id;
	private Order orders;
	private Snack snacks;
	private int quantity;
	private int cost;
	private int status;

	public OrderDetailSnack() {
	}

	public OrderDetailSnack(Order orders, Snack snacks, int quantity, int cost, int status) {
		this.orders = orders;
		this.snacks = snacks;
		this.quantity = quantity;
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
	@JoinColumn(name = "ordID", nullable = false)
	public Order getOrders() {
		return this.orders;
	}

	public void setOrders(Order orders) {
		this.orders = orders;
	}

	@ManyToOne
	@JoinColumn(name = "snackID", nullable = false)
	public Snack getSnacks() {
		return this.snacks;
	}

	public void setSnacks(Snack snacks) {
		this.snacks = snacks;
	}

	@Column(name = "Quantity", nullable = false)
	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

	public OrderDetailSnack(Integer id, Order orders, Snack snacks, int quantity, int cost, int status) {
		super();
		this.id = id;
		this.orders = orders;
		this.snacks = snacks;
		this.quantity = quantity;
		this.cost = cost;
		this.status = status;
	}
	
	
}
