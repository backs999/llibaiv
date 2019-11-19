package com.llibaiv.service;

import java.util.List;

import com.llibaiv.exception.DateRangeNotValidException;
import com.llibaiv.model.Order;
import com.llibaiv.model.TimePeriod;

public interface OrderService {

	/**
	 * Place an order based on a list of products
	 * 
	 * @param order The order to place
	 * 
	 * @return The placed order
	 */
	Order place(final Order order);

	/**
	 * Find a list of {@link Order} objects between a given time period
	 * 
	 * @param timePeriod The period to search for
	 * 
	 * @return The list of orders
	 * 
	 * @throws DateRangeNotValidException 
	 */
	List<Order> ordersBetween(final TimePeriod timePeriod) throws DateRangeNotValidException;
}
