package com.starking.dscatalog.domain.dtos;

import java.io.Serializable;

import com.starking.dscatalog.domain.Employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String email;
	private Long departmentId;
	
	public EmployeeDTO(Employee entity) {
		id = entity.getId();
		name = entity.getName();
		email = entity.getEmail();
		departmentId = entity.getDepartment().getId();
	}
}