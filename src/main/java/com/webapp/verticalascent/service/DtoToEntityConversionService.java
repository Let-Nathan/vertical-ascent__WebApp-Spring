package com.webapp.verticalascent.service;

import com.webapp.verticalascent.dto.UserRegistrationDto;
import com.webapp.verticalascent.entity.User;
import org.springframework.stereotype.Service;

/**
 * Service implementation with logic for user registration.
 *
 * @author Nathan L.
 * @version 1.0
 */
@Service
public class DtoToEntityConversionService {
	/**
	 * Methode to convert User Dto to User Entity.
	 *
	 * @param userRegistrationDto (Takes dto as input param to insert into User entity)
	 * @return user (User object with inserted value)
	 */
	public User convertUserRegistrationDtoToEntity(UserRegistrationDto userRegistrationDto) {
		User user = new User();
		try {
			user.setFirstName(userRegistrationDto.getFirstName());
			user.setLastName(userRegistrationDto.getLastName());
			user.setEmail(userRegistrationDto.getEmail());
			user.setPassword(userRegistrationDto.getPassword());
			user.setMobilePhone(userRegistrationDto.getMobilePhone());
			user.setBirthDate(userRegistrationDto.getBirthDate());
		} catch (Exception e) {
			e.getStackTrace();
		}
		return user;
	}
}
