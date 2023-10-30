package com.webapp.verticalascent.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Data Transfer Object for user
 *
 * @author Nathan L.
 * @version 1.0
 */
@Data
public class UserRegistrationDTO {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String mobilePhone;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;
	
}
