package comparator.ia.app.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import comparator.ia.app.dtos.auth.UserDataDto;
import comparator.ia.app.dtos.auth.login.DataLogin;
import comparator.ia.app.dtos.auth.register.DataRegister;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Tag(name = "Auth", description = "Controller the Authentication")
@RequestMapping("/api/v1/auth") 
public interface ControllerAuthApi {
	
	@Operation(summary = "Endpoint for register one user.", description = "Search that user is exists already in this program."
			+ " If not exists will create user else it will throw exception")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "This user has been registered correct."),
			@ApiResponse(responseCode = "400", description = "This user already exists in this program so it can`t register.")
	})
	@PostMapping("/register")
	ResponseEntity<Void> registerUser(@Parameter(description = "Credentials for register user") @RequestBody DataRegister data
			, @Parameter(description = "user session", hidden = true) HttpServletRequest request);
	
	@Operation(summary = "Endpoint for login one user.", description = "it will check that credentials are corrects")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "This user has been logined correct."),
			@ApiResponse(responseCode = "401", description = "the credentials sent it are incorrects.")
	})
	@PostMapping("/login")
	ResponseEntity<Void> loginUser(@Parameter(description = "Credentials for login user") @RequestBody DataLogin data, 
			@Parameter(description = "user session", hidden = true) HttpServletRequest request);
	
	@Operation(summary = "Endpoint for logout one user.", description = "Will close the user session.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "User has been logouted correct."),
	})
	@PostMapping("/logout")
	ResponseEntity<Void> logoutUser(@Parameter(description = "user session", hidden = true) HttpServletRequest request);
	
	@Operation(summary = "Endpoint for validate user sesion.", description = "Will validate session.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User has been validated correct.", content = { 
		            @Content(
		                    mediaType = "application/json", 
		                    schema = @Schema(implementation = UserDataDto.class)
		                ) 
		            }),
			@ApiResponse(responseCode = "401", description = "the credentials sent it are incorrects. This throw exception (AuthenticationFailedException)",
					content = @Content())
	})
	@PostMapping("/validate")
    ResponseEntity<UserDataDto> validate(@Parameter(description = "user session", hidden = true)  HttpSession session);	
	
	@Operation(summary = "Endpoint for validate Token Temporaly when attempting to recover the password.", description = "Will validate Token Temporaly.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Validation check completed. Returns true if the token is valid, false if invalid or expired.",
					content = @Content(
				            mediaType = "application/json",
				            schema = @Schema(type = "boolean", example = "true")
				        ))
	})
	@GetMapping("/validate-token")
	ResponseEntity<Boolean> validateTokenTemporaly(
			@Parameter(
	        name = "token", 
	        description = "Temporary alphanumeric code sent to the user's email for password recovery.",
	        example = "131141-4134131-1313131-41241312",
	        required = true
	    )@RequestParam(name = "token", required = true) String token);
}
