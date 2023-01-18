package com.starking.dscatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starking.dscatalog.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
