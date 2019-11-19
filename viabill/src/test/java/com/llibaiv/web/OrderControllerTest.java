package com.llibaiv.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.llibaiv.model.Order;
import com.llibaiv.model.Product;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderControllerTest {

	private ObjectMapper mapper = new ObjectMapper();

	@LocalServerPort
	private int port;

	@Test
	void testPlacingAnOrderIsSuccess() throws JsonProcessingException, IOException {

		RequestHelper.post("http://localhost:" + port + "/product",
				mapper.writeValueAsString(RequestHelper.product("test", 0.95)));
		RequestHelper.post("http://localhost:" + port + "/product",
				mapper.writeValueAsString(RequestHelper.product("test1", 0.94)));
		RequestHelper.post("http://localhost:" + port + "/product",
				mapper.writeValueAsString(RequestHelper.product("test2", 0.93)));

		@SuppressWarnings("unchecked")
		List<Product> products = mapper.readValue(RequestHelper.get("http://localhost:" + port + "/product"),
				List.class);

		Order order = mapper.readValue(
				RequestHelper.post("http://localhost:" + port + "/order", mapper.writeValueAsString(order(products))),
				Order.class);

		Assertions.assertNotNull(order);
		Assertions.assertNotNull(order.getId());
		Assertions.assertNotNull(order.getOrderTime());
		Assertions.assertEquals("test@test.com", order.getEmailAddress());
		Assertions.assertTrue(order.getProducts().size() == 3);
		Assertions.assertEquals(BigDecimal.valueOf(2.82), order.getTotal());
	}

	@Test
	void testFindOrderBetweenTimePeriodIsSuccess() throws JsonProcessingException, IOException {

		RequestHelper.post("http://localhost:" + port + "/product",
				mapper.writeValueAsString(RequestHelper.product("test", 0.95)));
		RequestHelper.post("http://localhost:" + port + "/product",
				mapper.writeValueAsString(RequestHelper.product("test1", 0.94)));
		RequestHelper.post("http://localhost:" + port + "/product",
				mapper.writeValueAsString(RequestHelper.product("test2", 0.93)));

		@SuppressWarnings("unchecked")
		List<Product> products = mapper.readValue(RequestHelper.get("http://localhost:" + port + "/product"),
				List.class);

		RequestHelper.post("http://localhost:" + port + "/order", mapper.writeValueAsString(order(products)));

		@SuppressWarnings("unchecked")
		List<Order> betweenPeriod = mapper.readValue(
				RequestHelper.get("http://localhost:" + port + "/order?end=2022-10-30&start=2019-10-31"), List.class);

		Assertions.assertNotNull(betweenPeriod);
		Assertions.assertTrue(betweenPeriod.size() == 1);
	}

	private Order order(final List<Product> products) {

		return Order.builder().emailAddress("test@test.com").products(products).build();
	}

}
