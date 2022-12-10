package com.starking.dscatalog.domain.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.starking.dscatalog.domain.Category;
import com.starking.dscatalog.domain.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@Size(min = 5, max=60, message = "Deve ter entre 5 e 60 caracteres")
	@NotBlank(message = "Campo Nome obrigatório")
	private String name;
	
	@Positive(message = "Preço deve ser um valor positivo")
	private BigDecimal price;
	
	@NotBlank(message = "Campo Description é obrigatório")
	private String description;
	
	@PastOrPresent(message = "A data do Produto não pode ser futura")
	private Instant date;
	private String imgUrl;
	
	private List<CategoryDTO> categories = new ArrayList<>();
 	
	public ProductDTO(Product entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
		this.imgUrl = entity.getImgUrl();
		this.date = entity.getDate();
	}
	
	public ProductDTO(Product entity, Set<Category> categories) {
		this(entity);
		categories.forEach(cat -> this.categories.add(new CategoryDTO(cat)));
	}
}