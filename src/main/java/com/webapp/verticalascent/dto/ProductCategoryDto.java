package com.webapp.verticalascent.dto;

import lombok.Data;

/**
 * Data transfer object of Product Category
 *
 * @author Nathan L.
 * @version 1.0
 *
 */
@Data
public class ProductCategoryDto {
	private Long id;
	private String name;
	private String description;
}
