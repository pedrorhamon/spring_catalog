package com.starking.dscatalog.domain.dtos;

import javax.validation.constraints.NotEmpty;

import com.starking.dscatalog.services.validation.UserInsertValid;

@UserInsertValid
public class UserInsertDTO extends UserDTO {
	
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Senha não Pode ser vazia")
	private String password;

	UserInsertDTO() {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
