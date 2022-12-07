package com.starking.dscatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starking.dscatalog.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);

}
