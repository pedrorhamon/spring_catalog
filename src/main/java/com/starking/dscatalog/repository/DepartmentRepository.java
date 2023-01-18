package com.starking.dscatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starking.dscatalog.domain.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
