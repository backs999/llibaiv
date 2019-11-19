package com.llibaiv.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

import com.llibaiv.data.OrderRepository;
import com.llibaiv.exception.DateRangeNotValidException;
import com.llibaiv.model.Order;
import com.llibaiv.model.Product;
import com.llibaiv.model.TimePeriod;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

	@InjectMocks
	private OrderServiceImpl target;

	@Mock
	private OrderRepository orderRepository;

	@Test
	void testPlaceOrder() {

		Order order = order();
		givenOrderIsSaved(order);

		Order savedOrder = target.place(order);

		verifyOrder(savedOrder);

		Mockito.verify(orderRepository).save(Mockito.isA(Order.class));
	}

	@Test
	void testFindOrdersBetweenTwoDates() throws DateRangeNotValidException {

		TimePeriod timePeriod = TimePeriod.builder().start(LocalDate.now()).end(LocalDate.now().plusDays(1)).build();

		givenFindOrdersByDateRange(timePeriod);

		List<Order> savedOrder = target.ordersBetween(timePeriod);

		savedOrder.forEach(order -> verifyOrder(order));

		Mockito.verify(orderRepository).between(Mockito.isA(LocalDate.class), Mockito.isA(LocalDate.class));
	}

	@Test
	void testFindOrdersBetweenTwoDatesFailsAsRangeIsIncorrectEndBeforeStart() throws DateRangeNotValidException {

		Assertions.assertThrows(DateRangeNotValidException.class, () -> {
			target.ordersBetween(TimePeriod.builder().start(LocalDate.now()).end(LocalDate.now().minusDays(1)).build());
		});
	}

	@Test
	void testFindOrdersBetweenTwoDatesFailsAsRangeIsIncorrectStartAfterEnd() throws DateRangeNotValidException {

		Assertions.assertThrows(DateRangeNotValidException.class, () -> {
			target.ordersBetween(TimePeriod.builder().start(LocalDate.now().plusDays(1)).end(LocalDate.now()).build());
		});
	}

	private Order order() {

		Product product = Product.builder().id("1").name("test").price(BigDecimal.valueOf(0.95)).build();

		return Order.builder().emailAddress("test@test.com").products(Arrays.asList(product)).build();
	}

	private void givenOrderIsSaved(final Order order) {

		order.setId("id");

		Mockito.when(orderRepository.save(Mockito.isA(Order.class))).thenReturn(order);
	}

	private void givenFindOrdersByDateRange(final TimePeriod timePeriod) {

		Order order = order();
		order.setId("id");
		order.setOrderTime(LocalDate.now());
		order.setTotal(BigDecimal.valueOf(0.95));

		Mockito.when(orderRepository.between(Mockito.isA(LocalDate.class), Mockito.isA(LocalDate.class)))
				.thenReturn(Arrays.asList(order));
	}

	private void verifyOrder(final Order order) {

		Assertions.assertNotNull(order.getId());
		Assertions.assertNotNull(order.getOrderTime());
		Assertions.assertEquals("test@test.com", order.getEmailAddress());
		Assertions.assertTrue(!CollectionUtils.isEmpty(order.getProducts()));
		Assertions.assertEquals(BigDecimal.valueOf(0.95), order.getTotal());
	}
}
