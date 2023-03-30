package com.maxtrain.bootcamp.salesapi.order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.maxtrain.bootcamp.salesapi.customer.Customer;
import com.maxtrain.bootcamp.salesapi.orderline.Orderline;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name="Orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false)
	private Date date;
	
	@Column(length=150, nullable=true)
	private String description;
	
	@Column(columnDefinition="varchar(20) NOT NULL DEFAULT 'NEW'")
	private String status;
	
	@Column(columnDefinition="decimal(9,2) NOT NULL DEFAULT 0")
	private double total;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="customerId", columnDefinition="int")
	private Customer customer;
	
	@JsonManagedReference
	@OneToMany(mappedBy="order")
	private List<Orderline> orderlines;
	
	public Order() {}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public List<Orderline> getOrderlines() {
		return orderlines;
	}
	
	public void setOrderline(List<Orderline> orderlines) {
		this.orderlines = orderlines;
	}
	
}
