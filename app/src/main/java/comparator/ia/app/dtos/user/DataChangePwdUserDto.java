package comparator.ia.app.dtos.user;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DataChangePwdUserDto(
		@Schema(description = "User current password.", format = "password", pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-.]).{5,}$")
		@NotEmpty
		@NotBlank
		@Pattern(regexp ="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-.]).{5,}$", message="La contraseña no cumple las condiciones")
		String pwdCurrent,
		
		@Schema(description = "User new password.", format = "password", pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-.]).{5,}$")
		@Pattern(regexp ="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-.]).{5,}$", message="La contraseña no cumple las condiciones")
		@NotEmpty
		@NotBlank
		String pwdNew,
		
		@Schema(description = "User confirm password.", format = "password", pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-.]).{5,}$")
		@Pattern(regexp ="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-.]).{5,}$", message="La contraseña no cumple las condiciones")
		@NotEmpty
		@NotBlank
		String pwdNewConfirm
		
		) {

}
