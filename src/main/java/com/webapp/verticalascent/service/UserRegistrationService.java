package com.webapp.verticalascent.service;

import com.webapp.verticalascent.dto.UserRegistrationDTO;
import com.webapp.verticalascent.entity.Addresses;
import com.webapp.verticalascent.entity.User;
import com.webapp.verticalascent.repository.AddressesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation with logic for user registration.
 *
 * @author Nathan L.
 * @version 1.0
 */
@Service
public class UserRegistrationService {
	
	public User convertUserRegistrationDTOToEntity(UserRegistrationDTO uRegisterDTO) {
		User user = new User();
		user.setFirstName(uRegisterDTO.getFirstName());
		user.setLastName(uRegisterDTO.getLastName());
		user.setEmail(uRegisterDTO.getEmail());
		user.setPassword(uRegisterDTO.getPassword());
		user.setMobilePhone(uRegisterDTO.getMobilePhone());
		user.setBirthDate(uRegisterDTO.getBirthDate());
		return user;
	}
	
}
