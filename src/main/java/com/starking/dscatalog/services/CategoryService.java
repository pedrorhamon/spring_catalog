package com.starking.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.starking.dscatalog.domain.Category;
import com.starking.dscatalog.domain.dtos.CategoryDTO;
import com.starking.dscatalog.exception.CategoryException;
import com.starking.dscatalog.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		 List<Category> list = this.categoryRepository.findAll();
		 
		 return list.stream()
				 .map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = this.categoryRepository.findById(id);
		Category entity = obj.orElseThrow(() -> new CategoryException("ID não encontrado"));
		return new CategoryDTO(entity);
	}
}
