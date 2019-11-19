package com.llibaiv.data;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.llibaiv.model.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

	@Query("{'orderTime' : { $gte: ?0, $lte: ?1 } }")
	public List<Order> between(LocalDate from, LocalDate to);
}
