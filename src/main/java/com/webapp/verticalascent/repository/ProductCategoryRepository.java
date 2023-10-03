package com.webapp.verticalascent.repository;

import com.webapp.verticalascent.entity.Product;
import com.webapp.verticalascent.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nathan L
 * @version 1.0
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

}
