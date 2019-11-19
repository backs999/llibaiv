package com.llibaiv.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.llibaiv.model.Product;
import com.llibaiv.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping
	public ResponseEntity<Product> create(@Valid @RequestBody final Product product) {
		return ResponseEntity.<Product>ok(productService.create(product));
	}

	@PutMapping
	public ResponseEntity<Product> update(@Valid @RequestBody final Product product) {
		return ResponseEntity.<Product>ok(productService.update(product));
	}

	@GetMapping
	public ResponseEntity<List<Product>> all() {
		return ResponseEntity.<List<Product>>ok(productService.all());
	}
}
