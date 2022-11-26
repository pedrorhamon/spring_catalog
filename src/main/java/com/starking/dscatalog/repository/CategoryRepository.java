package com.starking.dscatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starking.dscatalog.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
