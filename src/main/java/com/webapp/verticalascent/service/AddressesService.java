package com.webapp.verticalascent.service;

import com.webapp.verticalascent.dto.AddressDto;
import com.webapp.verticalascent.entity.Address;
import com.webapp.verticalascent.entity.User;
import com.webapp.verticalascent.repository.AddressesRepository;
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
	public Address getUserAddresses(User user) {
		return addressesRepository.findByUser(user);
	}
	
	/**
	 * Convert an AddresseDto to Addresse Object.
	 *
	 * @param addressesDto AddressesDto
	 * @return addresses Addresses
	 */
	public Address convertAddresseDtoToAddresse(AddressDto addressesDto) {
		Address addresses = new Address();
		addresses.setNumber(Integer.parseInt(addressesDto.getNumberStreet()));
		addresses.setPostalCode(addressesDto.getPostalCode());
		addresses.setStreet(addressesDto.getStreet());
		addresses.setCity(addressesDto.getCity());
		return addresses;
	}
	
	/**
	 * Linked an Addresses to User.
	 *
	 * @param addresses Addresses
	 * @param user User
	 * @return addresses Addresses
	 */
	public Address linkAddresseToUser(Address addresses, User user) {
		addresses.setUser(user);
		return addresses;
	}
	
	public void saveAddress(Address address) {
		addressesRepository.save(address);
	}
	
}
