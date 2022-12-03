package com.starking.dscatalog.tests;

import java.math.BigDecimal;
import java.time.Instant;

import com.starking.dscatalog.domain.Category;
import com.starking.dscatalog.domain.Product;
import com.starking.dscatalog.domain.dtos.ProductDTO;

public class Factory {
	
	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Good Phone", BigDecimal.valueOf(800.0), Instant.parse("2020-10-20T03:00:00Z"), "https://img.com/img.png");
		product.getCategories().add(new Category(2L, "Electronics"));
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
}
