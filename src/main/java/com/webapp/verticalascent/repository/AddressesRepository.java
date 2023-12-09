package com.webapp.verticalascent.repository;

import com.webapp.verticalascent.entity.Addresses;
import com.webapp.verticalascent.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Access and manage "Addresses" data.
 *
 * @author Nathan L
 * @version 1.0
 */
public interface AddressesRepository extends JpaRepository<Addresses, Long> {
	Addresses findByUser(User user);
}
