package com.webapp.verticalascent.repository;

import com.webapp.verticalascent.entity.Address;
import com.webapp.verticalascent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Access and manage "Addresses" data.
 *
 * @author Nathan L
 * @version 1.0
 */
public interface AddressesRepository extends JpaRepository<Address, Long> {
	Address findByUser(User user);
}
