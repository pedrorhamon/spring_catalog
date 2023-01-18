package com.starking.dscatalog.domain.dtos;

import java.io.Serializable;

import com.starking.dscatalog.domain.Department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public Long id;
	public String name;

	public DepartmentDTO(Department entity) {
		id = entity.getId();
		name = entity.getName();
	}
}
