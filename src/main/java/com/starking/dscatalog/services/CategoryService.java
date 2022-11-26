package com.starking.dscatalog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.starking.dscatalog.domain.Category;
import com.starking.dscatalog.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	public List<Category> findAll() {
		return this.categoryRepository.findAll();
	}

}
