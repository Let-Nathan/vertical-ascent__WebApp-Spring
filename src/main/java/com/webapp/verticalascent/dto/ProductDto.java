package com.webapp.verticalascent.dto;
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
	private Long id;
	private String name;
	private Long price;
	private int quantity;
}
