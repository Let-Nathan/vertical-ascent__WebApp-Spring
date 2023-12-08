package com.webapp.verticalascent.repository;

import com.webapp.verticalascent.entity.ShoppingSession;
import com.webapp.verticalascent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Access and manage "Addresses" data.
 *
 * @author Nathan L
 * @version 1.0
 */
public interface ShoppingSessionRepository extends JpaRepository<ShoppingSession, Long> {
	ShoppingSession findBySessionIdAndIsActive(String sessionId, boolean isActive);
	
	ShoppingSession findBySessionIdAndIsShoppingProcessEnd(String sessionId, boolean isProcessEnd);
	
	ShoppingSession findBySessionId(String sessionId);
}
