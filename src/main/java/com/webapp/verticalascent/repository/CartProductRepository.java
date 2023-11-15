package com.webapp.verticalascent.repository;

import com.webapp.verticalascent.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Access and manage "Addresses" data.
 *
 * @author Nathan L
 * @version 1.0
 */
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
}
