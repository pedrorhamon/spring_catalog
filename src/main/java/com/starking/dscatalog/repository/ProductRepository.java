package com.starking.dscatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starking.dscatalog.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
