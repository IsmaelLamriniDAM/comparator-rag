package comparator.ia.app.dtos.user;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DataUpdateUserDto(
		@Schema(
				description = "Nombre completo del usuario.",
				minLength = 3,
				maxLength = 20
			)
			@NotNull
			@NotBlank
			@Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
			String name,
			
			@Schema(
			        description = "Numero de telefono del usuario.",
			        minLength = 9,
			        maxLength = 9,
			        pattern = "^\\d{9}$" 
			 )
			@Pattern(regexp = "^\\d{9}$", message = "El número de teléfono no cumple el formato.")
			String phoneNumber
		) {

}
