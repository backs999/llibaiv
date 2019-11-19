package com.llibaiv.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.llibaiv.data.OrderRepository;
import com.llibaiv.exception.DateRangeNotValidException;
import com.llibaiv.model.Order;
import com.llibaiv.model.Product;
import com.llibaiv.model.TimePeriod;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Order place(Order order) {

		order.setOrderTime(LocalDate.now());
		order.setTotal(calculateOrderTotal(order));

		return orderRepository.save(order);
	}

	@Override
	public List<Order> ordersBetween(TimePeriod timePeriod) throws DateRangeNotValidException {

		if (timePeriod.getEnd().isBefore(timePeriod.getStart())) {
			throw new DateRangeNotValidException();
		}

		return orderRepository.between(timePeriod.getStart(), timePeriod.getEnd().plusDays(1));
	}

	private BigDecimal calculateOrderTotal(final Order order) {

		final Function<Product, BigDecimal> functionAsWebProduct = webProduct -> webProduct.getPrice();

		return order.getProducts().stream().filter(Objects::nonNull).map(functionAsWebProduct).reduce(BigDecimal.ZERO,
				BigDecimal::add);
	}
}
