package com.llibaiv.web;

import java.math.BigDecimal;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.llibaiv.model.Product;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductControllerTest {

	private ObjectMapper mapper = new ObjectMapper();

	@LocalServerPort
	private int port;

	@Test
	public void testAddProductToDatabase() throws Exception {

		Product product = mapper.readValue(RequestHelper.post("http://localhost:" + port + "/product",
				mapper.writeValueAsString(RequestHelper.product("test", 0.95))), Product.class);

		verifyProduct(product, "test", 0.95);
	}

	@Test
	public void testUpdateProductToDatabase() throws Exception {

		Product product = mapper.readValue(RequestHelper.post("http://localhost:" + port + "/product",
				mapper.writeValueAsString(RequestHelper.product("test", 0.95))), Product.class);

		product.setName("testchange");
		product.setPrice(BigDecimal.valueOf(0.91));

		Product updated = mapper.readValue(
				RequestHelper.put("http://localhost:" + port + "/product", mapper.writeValueAsString(product)),
				Product.class);

		verifyProduct(updated, "testchange", 0.91);
	}

	@Test
	public void testGetAllProducts() throws Exception {

		RequestHelper.post("http://localhost:" + port + "/product", mapper.writeValueAsString(RequestHelper.product("test", 0.95)));
		RequestHelper.post("http://localhost:" + port + "/product", mapper.writeValueAsString(RequestHelper.product("test1", 0.94)));
		RequestHelper.post("http://localhost:" + port + "/product", mapper.writeValueAsString(RequestHelper.product("test2", 0.93)));

		@SuppressWarnings("unchecked")
		List<Product> products = mapper.readValue(RequestHelper.get("http://localhost:" + port + "/product"),
				List.class);

		org.junit.jupiter.api.Assertions.assertTrue(products.size() == 3);
	}

	private void verifyProduct(final Product product, final String name, final double value) {

		Assertions.assertThat(product.getId()).isNotNull();
		Assertions.assertThat(product.getName()).isEqualTo(name);
		Assertions.assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(value));
	}
}
