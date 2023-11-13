package com.webapp.verticalascent.repository;

import com.webapp.verticalascent.entity.Product;
import com.webapp.verticalascent.entity.ProductCategory;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Access and manage "Product Category" data.
 *
 * @author Nathan L
 * @version 1.0
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
	ProductCategory findByName(String categoryName);
}
