package com.llibaiv.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.llibaiv.exception.DateRangeNotValidException;
import com.llibaiv.model.Order;
import com.llibaiv.model.TimePeriod;
import com.llibaiv.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	public ResponseEntity<Order> place(@Valid @RequestBody final Order order) {
		return ResponseEntity.<Order>ok(orderService.place(order));
	}

	@GetMapping
	public ResponseEntity<List<Order>> orders(final TimePeriod timePeriod) throws DateRangeNotValidException {

		return ResponseEntity.<List<Order>>ok(orderService.ordersBetween(timePeriod));
	}
}
