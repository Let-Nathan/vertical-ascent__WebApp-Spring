package com.webapp.verticalascent.service;

import com.webapp.verticalascent.entity.Addresses;
import com.webapp.verticalascent.entity.User;
import com.webapp.verticalascent.repository.AddressesRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation with logic for Addresses.
 *
 * @author Nathan L.
 * @version 1.0
 */
@Service
public class AddressesService {
	
	private final AddressesRepository addressesRepository;
	
	@Autowired
	public AddressesService(AddressesRepository addressesRepository) {
		this.addressesRepository = addressesRepository;
	}
	
	/**
	 * Get all the addresses link to a user.
	 *
	 * @return List Addresses Addresses
	 */
	public List<Addresses> getAllUserAddresses(User user) {
		return addressesRepository.findByUser(user);
	}
	
}
