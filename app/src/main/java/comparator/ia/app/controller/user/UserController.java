package comparator.ia.app.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import comparator.ia.app.dtos.auth.login.DataLogin;
import comparator.ia.app.dtos.user.DataChangeForgottPwd;
import comparator.ia.app.dtos.user.DataChangePwdUserDto;
import comparator.ia.app.dtos.user.DataUpdateUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RequestMapping("/api/v1/user")
public interface UserController {
	@Operation(summary = "Change password of logged-in user.", description = "Endpoint to change user password.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Password changed successfully."),
			@ApiResponse(responseCode = "404", description = "User not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unidentified user.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@PutMapping("/changePassword")
	ResponseEntity<Void> userChangePassword(
			@Parameter(description = "Password change information.", required = true) @Valid @RequestBody DataChangePwdUserDto pwdUserDto,
			HttpSession sesion);

		@Operation(summary = "Solicitar nueva contraseña",
				description = "Endpoint que recibe el correo electrónico del usuario para cambiar la contraseña")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = ""),
				@ApiResponse(responseCode = "404", description = "El correo electronico recibido no existe") 
		})
		@GetMapping("/forgottenPassword")
		ResponseEntity<Void> forgottenPassword(@RequestParam(name = "email", required = true) String email);

	@Operation(summary = "Update user profile data.", description = "Endpoint to change user profile data.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Profile successfully updated"),
			@ApiResponse(responseCode = "400", description = "Invalid datas."),
			@ApiResponse(responseCode = "404", description = "User not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unidentified user.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@PutMapping("/updateProfile")
	ResponseEntity<Void> userUpdateProfile(
			@Parameter(description = "Data for updating user data.", required = true) @Valid @RequestBody DataUpdateUserDto userUpdateDto,
			HttpSession sesion);

	@Operation(summary = "Delete user account.", description = "Endpoint for deleting a user's account.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Profile successfully delete."),
			@ApiResponse(responseCode = "404", description = "User not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unidentified user.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@DeleteMapping("/removeUser")
	ResponseEntity<Void> userRemove(
			@Parameter(description = "Data for user deletion.", required = true) @RequestBody @Valid DataLogin userDto,
			HttpSession sesion);// String contraseña
	
	@PostMapping("/reset-pwd")
	ResponseEntity<Void> resetPwd(@RequestBody DataChangeForgottPwd dto);
	
}
