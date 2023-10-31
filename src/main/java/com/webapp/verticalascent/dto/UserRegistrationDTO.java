package com.webapp.verticalascent.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * Data Transfer Object for user
 *
 * @author Nathan L.
 * @version 1.0
 */
@Data
public class UserRegistrationDTO {
	
	@NotBlank(message = "Merci de renseigner un prénom.")
	@NotEmpty
	private String firstName;
	
	@NotBlank(message = "Merci de renseigner votre nom de famille.")
	@NotEmpty
	private String lastName;
	
	@NotBlank
	@NotEmpty
	@Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "L'adresse e-mail n'est pas valide.")
	private String email;
	
	@NotBlank
	@NotEmpty
	@Size(min = 8, max = 32, message = "Le mot de passe doit contenir entre 8 et 32 caracthères.")
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Le mot de passe doit contenir, un chiffre, une lettre minuscule et une majuscule et un caracthère spécial.")
	private String password;
	
	@Pattern(regexp = "^(?:\\+\\d+|\\d+)$", message = "Le téléphone doit être au format chiffre ou avec +33.")
	@Size(min = 10, message = "Vous devez renseigner un nuémro valide, avec 10 chiffres.")
	private String mobilePhone;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past(message = "La date ne peut pas être dans le futur.")
	private Date birthDate;
	
}
