package comparator.ia.app.dtos.auth.login;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Schema(name = "Information to required to login user.")
public record DataLogin(
		@Email(regexp = "^[a-zA-Z0-9]{4,}@[a-zA-Z0-9]{4,}[.][a-z]{1,3}$", message = "Rule Email")
		@Schema(name = "userEmail", example = "Arthur@gmail.com")
		String email, 
		
		@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{5,}$")
		@Schema(name = "userPassword", example = "12345At@")
		String password
		) {

}
