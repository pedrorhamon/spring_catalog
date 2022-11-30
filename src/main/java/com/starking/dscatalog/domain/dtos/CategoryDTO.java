package com.starking.dscatalog.domain.dtos;

import java.io.Serializable;

import com.starking.dscatalog.domain.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	
	public CategoryDTO(Category entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
	}
}