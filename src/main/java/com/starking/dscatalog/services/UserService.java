package com.starking.dscatalog.services;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.starking.dscatalog.domain.Product;
import com.starking.dscatalog.domain.User;
import com.starking.dscatalog.domain.dtos.UserDTO;
import com.starking.dscatalog.exception.CategoryException;
import com.starking.dscatalog.exception.DatabaseException;
import com.starking.dscatalog.repository.CategoryRepository;
import com.starking.dscatalog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	private final CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable) {
		Page<User> list = this.userRepository.findAll(pageable);
		return list.map(x -> new UserDTO(x));
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<Product> obj = this.userRepository.findById(id);
		Product entity = obj.orElseThrow(() -> new CategoryException("ID não encontrado"));
		return new UserDTO(entity, entity.getCategories());
	}
	
	@Transactional(readOnly = true)
	public UserDTO save(UserDTO dto) {
		User entity = new User();
		copyDtoToEntity(dto, entity);
		entity = this.userRepository.save(entity);
		return new UserDTO(entity);
	}


//	@Transactional(readOnly = true)
//	public UserDTO update(Long id, UserDTO dto) {
//		try {
//			Product entity = this.userRepository.getOne(id);
//			copyDtoToEntity(dto, entity);
//			entity = this.userRepository.save(entity);
//			return new UserDTO(entity);
//		} catch (EntityNotFoundException e) {
//			throw new CategoryException("Id not found" + id);
//		}
//	}

	public void delete(Long id) {
		try {
			this.userRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new CategoryException("Id not found" + id);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
//	private void copyDtoToEntity(UserDTO dto, User entity) {
//		entity.setFirstName(dto.getFirstName());
//		entity.setLastName(dto.getLastName());
//		entity.setEmail(dto.getEmail());
//		entity.setPassword(dto.getPassword());
//		entity.getRoles().clear();
//		
//		for(RolesDTO catDTO : dto.get()) {
//			Category category = this.categoryRepository.getOne(catDTO.getId());
//			entity.getRoles().a(category);
//		}
//	}
}
