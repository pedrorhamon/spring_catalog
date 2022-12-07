package com.starking.dscatalog.domain.dtos;

import java.io.Serializable;

import com.starking.dscatalog.domain.Role;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class RolesDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String authority;
	
	public RolesDTO() {}
	
	public RolesDTO(Long id, String authority) {
		this.id = id;
		this.authority = authority;
	}
	
	public RolesDTO(Role role) {
		id = role.getId();
		authority = role.getAuthority();
	}
}