package com.webapp.verticalascent.repository;

import com.webapp.verticalascent.entity.Addresses;
import com.webapp.verticalascent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Access and manage "Addresses" data.
 *
 * @author Nathan L
 * @version 1.0
 */
public interface AddressesRepository extends JpaRepository<Addresses, Long> {
	List<Addresses> findByUser(User user);
}
