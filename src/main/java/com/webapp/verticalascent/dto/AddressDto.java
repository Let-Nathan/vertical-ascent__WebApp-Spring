package com.webapp.verticalascent.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Data transfer object for Product.
 *
 * @author Nathan L.
 * @version 1.0
 *
 */
@Data
public class AddressDto {
	
	private Long id;
	
	@Pattern(regexp = "^\\d+$", message = "Le champ 'numéro' doit contenir uniquement des chiffres")
	@NotBlank(message = "Le champ 'numéro' est obligatoire")
	private String numberStreet;
	
	@NotBlank(message = "Le champ 'Voie' est obligatoire")
	@Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]*$", message = "Le champ 'Voie' doit contenir uniquement des lettres")
	private String street;
	
	@NotBlank(message = "Le champ 'Code postal' est obligatoire")
	@Pattern(regexp = "\\d{5}", message = "Le code postal doit contenir 5 chiffres")
	private String postalCode;
	
	@NotBlank(message = "Le champ 'Ville' est obligatoire")
	@Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]*$", message = "Le champ 'ville' doit contenir uniquement des lettres")
	private String city;

}
