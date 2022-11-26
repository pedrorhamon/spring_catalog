package com.starking.dscatalog.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starking.dscatalog.domain.Category;
import com.starking.dscatalog.services.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
	
	private final CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		List<Category> list = this.categoryService.findAll();
		return ResponseEntity.ok(list);
	}
}
