package com.starking.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.starking.dscatalog.domain.Category;
import com.starking.dscatalog.domain.Product;
import com.starking.dscatalog.domain.dtos.CategoryDTO;
import com.starking.dscatalog.domain.dtos.ProductDTO;
import com.starking.dscatalog.exception.CategoryException;
import com.starking.dscatalog.exception.DatabaseException;
import com.starking.dscatalog.repository.CategoryRepository;
import com.starking.dscatalog.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;
	
	private final CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		Page<Product> list = this.productRepository.findAll(pageRequest);
		return list.map(x -> new ProductDTO(x));
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = this.productRepository.findById(id);
		Product entity = obj.orElseThrow(() -> new CategoryException("ID não encontrado"));
		return new ProductDTO(entity, entity.getCategories());
	}
	
	@Transactional(readOnly = true)
	public ProductDTO save(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = this.productRepository.save(entity);
		return new ProductDTO(entity);
	}


	@Transactional(readOnly = true)
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = this.productRepository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = this.productRepository.save(entity);
			return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new CategoryException("Id not found" + id);
		}
	}

	public void delete(Long id) {
		try {
			this.productRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new CategoryException("Id not found" + id);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setImgUrl(dto.getImgUrl());
		entity.setDate(dto.getDate());
		entity.setPrice(dto.getPrice());
		entity.getCategories().clear();
		
		for(CategoryDTO catDTO : dto.getCategories()) {
			Category category = this.categoryRepository.getOne(catDTO.getId());
			entity.getCategories().add(category);
		}
	}
}
