package comparator.ia.app.dtos.auth.register;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Information required to register a new user.")
public record DataRegister(
		
		@NotNull
		@NotBlank
		@Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
		@Schema(description = "username", example = "Arthur")
		String name, 
		
		@Email(regexp = "^[a-zA-Z0-9]{4,}@[a-zA-Z0-9]{4,}[.][a-z]{1,3}$", message = "Rule Email")
		@Schema(description = "userEmail", example = "Arthur@gmail.com")
		String email, 
		
		@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-.]).{5,}$")
		@Schema(description = "userPassword", example = "12345At@")
		String password, 
		
		@Pattern(regexp = "^\\d{9}$", message = "Number phone wich length should 9 characters.")
		@Schema(description = "userPhone", example = "123234532@")
		String phone
		) {
}
