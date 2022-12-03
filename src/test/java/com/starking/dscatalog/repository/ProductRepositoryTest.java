package com.starking.dscatalog.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.starking.dscatalog.domain.Product;
import com.starking.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	private long exintigId;
	private long nonExintigId;
	private long countTotalProduct;
	
	@BeforeEach
	void setUp() throws Exception {
		exintigId = 1L;;
		nonExintigId = 1000L;
		countTotalProduct = 25L;
	}
	
	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
		Product product = Factory.createProduct();
		product.setId(null);
		
		product = this.productRepository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProduct +1 , product.getId());
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		
		this.productRepository.deleteById(exintigId);
		
		Optional<Product> product = this.productRepository.findById(exintigId);
		Assertions.assertFalse(product.isPresent());
	}
	
	@TestFactory
	public void deleteShouldDeleteThrowDataIntegrityViolationException() {
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
			this.productRepository.deleteById(nonExintigId);
		});
	}
}
