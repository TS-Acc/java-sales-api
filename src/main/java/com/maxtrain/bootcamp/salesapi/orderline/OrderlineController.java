package com.maxtrain.bootcamp.salesapi.orderline;

import com.maxtrain.bootcamp.salesapi.item.Item;
import com.maxtrain.bootcamp.salesapi.item.ItemRepository;
import com.maxtrain.bootcamp.salesapi.order.Order;
import com.maxtrain.bootcamp.salesapi.order.OrderRepository;
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
@RequestMapping("/api/orderlines")
public class OrderlineController {
	
	@Autowired
	private OrderlineRepository ordlineRepo;
	
	@Autowired
	private OrderRepository ordRepo;
	
	@Autowired
	private ItemRepository itemRepo;
	
	private boolean recalculateOrderTotal(int orderId) {
		Optional<Order> orderToRecalc = ordRepo.findById(orderId);
		if(orderToRecalc.isEmpty()) {
			return false;
		}
		Iterable<Orderline> orderlinesByOrderId = ordlineRepo.findByOrderId(orderToRecalc.get().getId());
		double total = 0;
		for(Orderline orderline : orderlinesByOrderId) {
			if(orderline.getItem().getName() == null) {
				Optional<Item> item = itemRepo.findById(orderline.getItem().getId());
				orderline.setItem(item.get());
			}
			double addToTotal = orderline.getItem().getPrice() * orderline.getQuantity();
			total += addToTotal;
		}
		orderToRecalc.get().setTotal(total);
		ordRepo.save(orderToRecalc.get());
		return true;
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Orderline>> getOrderlines() {
		Iterable<Orderline> orderlines = ordlineRepo.findAll();
		return new ResponseEntity<Iterable<Orderline>>(orderlines, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Orderline> getOrderline(@PathVariable int id) {
		Optional<Orderline> orderline = ordlineRepo.findById(id);
		if(orderline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Orderline>(orderline.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Orderline> postOrderline(@RequestBody Orderline orderline) {
		Orderline newOrderline = ordlineRepo.save(orderline);
		recalculateOrderTotal(orderline.getOrder().getId());
		return new ResponseEntity<Orderline>(newOrderline, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putOrderline(@PathVariable int id, @RequestBody Orderline orderline) {
		if(orderline.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ordlineRepo.save(orderline);
		recalculateOrderTotal(orderline.getOrder().getId());
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteOrderline(@PathVariable int id) {
		Optional<Orderline> orderline = ordlineRepo.findById(id);
		if(orderline.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		ordlineRepo.delete(orderline.get());
		recalculateOrderTotal(orderline.get().getOrder().getId());
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
}
