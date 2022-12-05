package com.starking.dscatalog.services;

import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.starking.dscatalog.domain.Product;
import com.starking.dscatalog.domain.dtos.ProductDTO;
import com.starking.dscatalog.exception.CategoryException;
import com.starking.dscatalog.exception.DatabaseException;
import com.starking.dscatalog.repository.ProductRepository;
import com.starking.dscatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long dependentId;
	private Product product;
	private PageImpl<Product> page;
	
	@BeforeEach
	public void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		product = Factory.createProduct();
		page = new PageImpl<>(List.of(product));
		
		Mockito.when(this.repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		Mockito.when(this.repository.save(ArgumentMatchers.any())).thenReturn(product);
		
		Mockito.when(this.repository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(this.repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.doNothing().when(this.repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(this.repository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(this.repository).deleteById(dependentId);
	}

	@Test
	public void findAllPagedShouldReturnPage() {
		Pageable pageable = PageRequest.of(0, 12);
		Page<ProductDTO> result = this.service.findAllPaged(pageable);
		
		Assertions.assertNotNull(result);
		Mockito.verify(this.repository, times(1)).findAll(pageable);
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
		
		Assertions.assertThrows(DatabaseException.class, () -> {
			this.service.delete(dependentId);
		});
		
		Mockito.verify(this.repository, times(1)).deleteById(dependentId);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(CategoryException.class, () -> {
			this.service.delete(nonExistingId);
		});

		Mockito.verify(this.repository, times(1)).deleteById(nonExistingId);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(() -> {
			this.service.delete(existingId);
		});
		
		Mockito.verify(this.repository, times(1)).deleteById(existingId);
	}
}
