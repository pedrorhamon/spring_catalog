package com.starking.dscatalog.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.starking.dscatalog.domain.Product;

@DataJpaTest
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	private long exintigId;
	private long nonExintigId;
	
	@BeforeEach
	void setUp() throws Exception {
		exintigId = 1L;;
		nonExintigId = 1000L;
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		
		this.productRepository.deleteById(exintigId);
		
		Optional<Product> product = this.productRepository.findById(exintigId);
		Assertions.assertFalse(product.isPresent());
	}
	
	@Test
	public void deleteShouldDeleteThrowDataIntegrityViolationException() {
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
			this.productRepository.deleteById(nonExintigId);
		});
	}
}
