package com.llibaiv.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

import com.llibaiv.data.ProductRepository;
import com.llibaiv.model.Product;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

	@InjectMocks
	private ProductServiceImpl target;

	@Mock
	private ProductRepository productRepository;

	@Test
	void testCreateProductSuccessfully() {

		givenProduct(true);

		Product product = target.create(Product.builder().name("test").price(BigDecimal.valueOf(0.95)).build());

		verifyProduct(product, "test", 0.95);
		verifySaveCalled(1);
		Mockito.verifyNoMoreInteractions(productRepository);
	}

	@Test
	void testCreateProductFailsDueToIdBeingPassed() {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			target.create(Product.builder().id("test").name("test").price(BigDecimal.valueOf(0.95)).build());
		});

		Mockito.verifyNoMoreInteractions(productRepository);
	}

	@Test
	void testReturnAllProducts() {

		givenProductMultipleFindAll();

		Assertions.assertTrue(!CollectionUtils.isEmpty(target.all()));

		Mockito.verify(productRepository).findAll();
		Mockito.verifyNoMoreInteractions(productRepository);
	}

	@Test
	void testUpdateProductSuccessfully() {

		givenProduct(true);

		Product product = target
				.update(Product.builder().id("id").name("test").price(BigDecimal.valueOf(0.95)).build());

		verifyProduct(product, "test", 0.95);
		verifySaveCalled(1);
		Mockito.verifyNoMoreInteractions(productRepository);
	}

	@Test
	void testUpdateProductFailsDueToNoBeingPassed() {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			target.update(Product.builder().name("test").price(BigDecimal.valueOf(0.95)).build());
		});

		Mockito.verifyNoMoreInteractions(productRepository);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testFindProductsById() {

		List<String> ids = java.util.Arrays.asList("2", "3", "7");

		givenProductMultipleFindByIds(ids);

		List<Product> products = target.findByIds(ids);

		Assertions.assertTrue(products.size() == 3);

		Mockito.verify(productRepository).findByIds(Mockito.isA(List.class));
		Mockito.verifyNoMoreInteractions(productRepository);
	}

	private void givenProduct(final boolean withId) {

		Product.ProductBuilder mock = Product.builder().name("test").price(BigDecimal.valueOf(0.95));
		if (withId) {
			mock.id("id");
		}

		Mockito.when(productRepository.save(Mockito.isA(Product.class))).thenReturn(mock.build());
	}

	private void givenProductMultipleFindAll() {

		List<Product> products = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			products.add(Product.builder().id(String.valueOf(i)).name("test").price(BigDecimal.valueOf(0.95)).build());
		}

		Mockito.when(productRepository.findAll()).thenReturn(products);
	}

	private void givenProductMultipleFindByIds(final List<String> ids) {

		List<Product> products = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			products.add(Product.builder().id(String.valueOf(i)).name("test").price(BigDecimal.valueOf(0.95)).build());
		}

		List<Product> filtered = products.stream()
				.filter(product -> ids.stream().anyMatch(id -> id.equals(product.getId())))
				.collect(Collectors.toList());

		Mockito.when(productRepository.findByIds(ids)).thenReturn(filtered);
	}

	private void verifyProduct(final Product product, final String name, final double value) {

		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(product.getName(), name);
		Assertions.assertEquals(product.getPrice(), BigDecimal.valueOf(value));
	}

	private void verifySaveCalled(final int times) {
		Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.isA(Product.class));
	}
}
