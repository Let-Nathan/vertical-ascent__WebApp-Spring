package com.webapp.verticalascent.repository;

import com.webapp.verticalascent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Access and manage "User" data
 *
 * @author Nathan L
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
