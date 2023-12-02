package com.webapp.verticalascent.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * Data transfer object for Product.
 *
 * @author Nathan L.
 * @version 1.0
 *
 */
@Data
public class ProductDto {
	
	@NotNull(message = "Merci de ne pas modifier le local storage ;)")
	private Long id;
	
	@NotNull(message = "Merci de ne pas modifier le local storage ;)")
	@Pattern(regexp = "[a-zA-Z0-9]+")
	private String name;
	
	@NotNull(message = "Merci de ne pas modifier le local storage ;)")
	private Long price;
	
	@NotNull(message = "Merci de ne pas modifier le local storage ;)")
	@Min(value = 1)
	@Max(value = 1)
	private int quantity;
}
