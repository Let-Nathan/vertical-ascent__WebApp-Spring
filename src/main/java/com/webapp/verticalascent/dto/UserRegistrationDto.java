package com.webapp.verticalascent.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * Data Transfer Object for user.
 *
 * @author Nathan L.
 * @version 1.0
 */
@Data
public class UserRegistrationDto {
	
	@NotBlank(message = "Merci de renseigner un prénom.")
	@NotEmpty
	private String firstName;
	
	@NotBlank(
		message = "Merci de renseigner votre nom de famille."
	)
	@NotEmpty
	private String lastName;
	
	@NotBlank
	@NotEmpty
	@Email
	@Pattern(
		regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
		message = "L'adresse e-mail n'est pas valide."
	)
	private String email;
	
	@NotBlank
	@NotEmpty
	@Size(
		min = 8, max = 32,
		message = "Le mot de passe doit contenir entre 8 et 32 caractères."
	)
	@Pattern(
		regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
		message = "Le mot de passe doit contenir, un chiffre, une lettre minuscule "
			+ "et une majuscule et un caractère spécial."
	)
	private String password;
	
	@Pattern(
		regexp = "^(?:\\+\\d+|\\d+)$",
		message = "Le téléphone doit contenir uniquement des chiffres "
			+ "ou dans le format international."
	)
	@Size(
		min = 10,
		message = "La longueur du numéro n'est pas valide."
	)
	private String mobilePhone;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past(message = "La date ne peut pas être dans le futur.")
	private Date birthDate;
	
}
