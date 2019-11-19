package com.llibaiv.service;

import java.util.List;

import com.llibaiv.model.Product;

public interface ProductService {

	/**
	 * Creates a new <code>Product</code> object assigning an ID to the returned
	 * object
	 * 
	 * @param product The product to create
	 * 
	 * @return The new Product
	 */
	Product create(final Product product);

	/**
	 * Returns all the <code>Product</code> objects currently stored within the
	 * application
	 * 
	 * @return The list of the Products
	 */
	List<Product> all();

	/**
	 * Update an existing <code>WebProduct</code>
	 * 
	 * @param product The Product to update
	 * 
	 * @return The Product with the updated values
	 */
	Product update(final Product product);

	/**
	 * Returns all the <code>Product</code> objects currently stored within the
	 * application fetched by a list of ids
	 * 
	 * @return The list of the Products
	 */
	List<Product> findByIds(final List<String> ids);
}
