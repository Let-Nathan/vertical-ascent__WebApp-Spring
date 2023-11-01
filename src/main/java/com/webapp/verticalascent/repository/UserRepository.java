package com.webapp.verticalascent.repository;

import com.webapp.verticalascent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Access and manage "User" data.
 *
 * @author Nathan L
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {
//	@Query("SELECT id FROM User where email = :email")
	User findByEmail(@Param("email") String email);
}
