package com.maxtrain.bootcamp.salesapi.orderline;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.maxtrain.bootcamp.salesapi.item.Item;
import com.maxtrain.bootcamp.salesapi.order.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Orderlines")
public class Orderline {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(columnDefinition="INT NOT NULL DEFAULT 1")
	private int quantity;
	
	@JsonBackReference
	@ManyToOne(optional=false)
	@JoinColumn(name="orderId", columnDefinition="int")
	private Order order;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="itemId", columnDefinition="int")
	private Item item;
	
	public Orderline() {}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	
}
