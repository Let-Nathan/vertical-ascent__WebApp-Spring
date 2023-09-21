package com.webapp.verticalascent.repository;

import com.webapp.verticalascent.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nathan L
 * @version 1.0
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

}
