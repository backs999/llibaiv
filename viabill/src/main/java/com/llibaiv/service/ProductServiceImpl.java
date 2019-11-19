package com.llibaiv.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.llibaiv.data.ProductRepository;
import com.llibaiv.model.Product;

import lombok.extern.flogger.Flogger;

@Service
@Flogger
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product create(Product product) {

		log.atFine().log("attempting to create a new product");

		Assert.isTrue(StringUtils.isBlank(product.getId()), "You can not pass in an existing product");

		return productRepository.save(product);
	}

	@Override
	public List<Product> all() {

		log.atFine().log("returning all products");

		return productRepository.findAll();
	}

	@Override
	public Product update(Product product) {

		log.atFine().log("attempting to update a product");

		Assert.isTrue(StringUtils.isNotBlank(product.getId()), "You must pass in an existing product to update");

		return productRepository.save(product);
	}

	@Override
	public List<Product> findByIds(List<String> ids) {

		log.atFine().log("returning all products with ids [{}]", ids);

		return productRepository.findByIds(ids);
	}
}
