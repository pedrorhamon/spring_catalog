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
import com.starking.dscatalog.domain.dtos.CategoryDTO;
import com.starking.dscatalog.exception.CategoryException;
import com.starking.dscatalog.exception.DatabaseException;
import com.starking.dscatalog.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
		Page<Category> list = this.categoryRepository.findAll(pageRequest);
		return list.map(x -> new CategoryDTO(x));
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = this.categoryRepository.findById(id);
		Category entity = obj.orElseThrow(() -> new CategoryException("ID não encontrado"));
		return new CategoryDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO save(CategoryDTO dto) {
		Category entity = new Category();
		entity.setNome(dto.getNome());
		entity = this.categoryRepository.save(entity);
		return new CategoryDTO(entity);
	}

	@Transactional(readOnly = true)
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = this.categoryRepository.getOne(id);
			entity.setNome(dto.getNome());
			entity = this.categoryRepository.save(entity);
			return new CategoryDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new CategoryException("Id not found" + id);
		}
	}

	public void delete(Long id) {
		try {
			this.categoryRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new CategoryException("Id not found" + id);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
}
