package com.webapp.verticalascent.repository;

import com.webapp.verticalascent.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Access and manage user role.
 *
 * @author Nathan L
 * @version 1.0
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
