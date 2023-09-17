package com.webapp.verticalascent.repository;

import com.webapp.verticalascent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nathan L
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
