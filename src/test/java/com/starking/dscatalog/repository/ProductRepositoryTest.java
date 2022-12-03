package com.starking.dscatalog.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.starking.dscatalog.domain.Product;

@DataJpaTest
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		long exintigId = 1L;
		
		this.productRepository.deleteById(exintigId);
		
		Optional<Product> product = this.productRepository.findById(exintigId);
		Assertions.assertFalse(product.isPresent());
	}
}
