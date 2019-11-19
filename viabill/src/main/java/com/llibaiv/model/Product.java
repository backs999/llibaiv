package com.llibaiv.model;

import java.math.BigDecimal;

import javax.annotation.Nonnull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("product")
public class Product {

	@Id
	private String id;
	@Nonnull
	private String name;
	@Nonnull
	private BigDecimal price;
}
