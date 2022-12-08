package com.starking.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.starking.dscatalog.domain.Role;
import com.starking.dscatalog.domain.User;
import com.starking.dscatalog.domain.dtos.RolesDTO;
import com.starking.dscatalog.domain.dtos.UserDTO;
import com.starking.dscatalog.domain.dtos.UserInsertDTO;
import com.starking.dscatalog.domain.dtos.UserUpdateDTO;
import com.starking.dscatalog.exception.CategoryException;
import com.starking.dscatalog.exception.DatabaseException;
import com.starking.dscatalog.repository.RoleRepository;
import com.starking.dscatalog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private final UserRepository userRepository;
	
	private final RoleRepository roleRepository;
	
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable) {
		Page<User> list = this.userRepository.findAll(pageable);
		return list.map(x -> new UserDTO(x));
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<User> obj = this.userRepository.findById(id);
		User entity = obj.orElseThrow(() -> new CategoryException("ID não encontrado"));
		return new UserDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public UserDTO save(UserInsertDTO dto) {
		User entity = new User();
		copyDtoToEntity(dto, entity);
		entity.setPassword(this.passwordEncoder.encode(dto.getPassword()));
		entity = this.userRepository.save(entity);
		return new UserDTO(entity);
	}


	@Transactional(readOnly = true)
	public UserDTO update(Long id, UserUpdateDTO dto) {
		try {
			User entity = this.userRepository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = this.userRepository.save(entity);
			return new UserDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new CategoryException("Id not found" + id);
		}
	}

	public void delete(Long id) {
		try {
			this.userRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new CategoryException("Id not found" + id);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	private void copyDtoToEntity(UserDTO dto, User entity) {

		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		
		entity.getRoles().clear();
		for (RolesDTO roleDto : dto.getRoles()) {
			Role role = roleRepository.getOne(roleDto.getId());
			entity.getRoles().add(role);
		}
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = this.userRepository.findByEmail(username);
		if (user == null) {
			logger.error("User not found: " + username);
			throw new UsernameNotFoundException("Email not found");
		}
		logger.info("User found: " + username);
		return user;
	}
}
