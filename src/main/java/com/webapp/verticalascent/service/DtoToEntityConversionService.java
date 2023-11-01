package com.webapp.verticalascent.service;

import com.webapp.verticalascent.dto.UserRegistrationDto;
import com.webapp.verticalascent.entity.ErrorsLog;
import com.webapp.verticalascent.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation with logic for user registration.
 *
 * @author Nathan L.
 * @version 1.0
 */
@Service
public class DtoToEntityConversionService {
	
	private final ErrorsLogService errorsLogService;
	
	@Autowired
	public DtoToEntityConversionService(ErrorsLogService errorsLogService) {
		this.errorsLogService = errorsLogService;
	}
	
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
			errorsLogService.storeLogs(e);
		}
		return user;
	}
}
