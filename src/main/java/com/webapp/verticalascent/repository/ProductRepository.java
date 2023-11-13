package com.webapp.verticalascent.repository;

import com.webapp.verticalascent.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Access and manage "Product" data.
 *
 * @author Nathan L
 * @version 1.0
 *
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query("SELECT p FROM Product p WHERE p.category IN "
		+ "(SELECT c FROM ProductCategory c WHERE c.name = :categoryName)")
	List<Product> findByCategoryName(@Param("categoryName") String categoryName);
}
