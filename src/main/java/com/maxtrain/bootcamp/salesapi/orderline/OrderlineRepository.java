package com.maxtrain.bootcamp.salesapi.orderline;

import org.springframework.data.repository.CrudRepository;

public interface OrderlineRepository extends CrudRepository<Orderline, Integer> {
	Iterable<Orderline> findByOrderId(int orderId);
}
