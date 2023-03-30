package com.maxtrain.bootcamp.salesapi.order;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderRepository ordRepo;
	
	private final String statusReview = "REVIEW";
	private final String statusApproved = "APPROVED";
	private final String statusRejected = "REJECTED";
	
	@GetMapping
	public ResponseEntity<Iterable<Order>> getOrders() {
		Iterable<Order> orders = ordRepo.findAll();
		return new ResponseEntity<Iterable<Order>>(orders, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Order> getOrder(@PathVariable int id) {
		Optional<Order> order = ordRepo.findById(id);
		if(order.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Order>(order.get(), HttpStatus.OK);
	}
	
	@GetMapping("reviews")
	public ResponseEntity<Iterable<Order>> getOrdersInReview() {
		Iterable<Order> ordersInReview = ordRepo.findByStatus(statusReview);
		return new ResponseEntity<Iterable<Order>>(ordersInReview, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Order> postOrder(@RequestBody Order order) {
		Order newOrder = ordRepo.save(order);
		return new ResponseEntity<Order>(newOrder, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putOrder(@PathVariable int id, @RequestBody Order order) {
		if(order.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ordRepo.save(order);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("review/{id}")
	public ResponseEntity reviewOrder(@PathVariable int id, @RequestBody Order order) {
		String changeStatus = order.getTotal() <= 100 ? statusApproved : statusReview;
		order.setStatus(changeStatus);
		return putOrder(id, order);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("approve/{id}")
	public ResponseEntity approveOrder(@PathVariable int id, @RequestBody Order order) {
		order.setStatus(statusApproved);
		return putOrder(id, order);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("reject/{id}")
	public ResponseEntity rejectOrder(@PathVariable int id, @RequestBody Order order) {
		order.setStatus(statusRejected);
		return putOrder(id, order);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteOrder(@PathVariable int id) {
		Optional<Order> order = ordRepo.findById(id);
		if(order.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		ordRepo.delete(order.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
