package com.webapp.verticalascent.repository;

import com.webapp.verticalascent.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Access and manage "Product Category" data.
 *
 * @author Nathan L
 * @version 1.0
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
	ProductCategory findByName(String categoryName);
}
