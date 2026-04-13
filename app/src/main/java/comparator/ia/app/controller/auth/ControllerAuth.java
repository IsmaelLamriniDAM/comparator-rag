package comparator.ia.app.controller.auth;

import java.util.Optional;

import javax.sound.midi.SysexMessage;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import comparator.ia.app.dtos.auth.UserDataDto;
import comparator.ia.app.dtos.auth.login.DataLogin;
import comparator.ia.app.dtos.auth.login.DataUserSearched;
import comparator.ia.app.dtos.auth.register.DataRegisted;
import comparator.ia.app.dtos.auth.register.DataRegister;
import comparator.ia.app.handler.exceptions.auth.AuthenticationFailedException;
import comparator.ia.app.handler.exceptions.auth.EmailAlreadyExistsException;
import comparator.ia.app.handler.exceptions.auth.EmailOrPasswordIncorrectException;
import comparator.ia.app.handler.exceptions.auth.UserNotCouldCreatedException;
import comparator.ia.app.service.auth.AuthService;
import comparator.ia.app.util.auth.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
public class ControllerAuth implements ControllerAuthApi{
	
	private final AuthService authService;
	private final SessionManager sessionManager;
	
	ControllerAuth(AuthService authService, SessionManager sessionManager){
		this.authService = authService;
		this.sessionManager = sessionManager;}

	@Override
	public ResponseEntity<Void> registerUser(@RequestBody @Valid DataRegister data, HttpServletRequest request) {
		HttpSession currentSession = request.getSession(false);
		if (currentSession != null) {
			sessionManager.deleteSession(currentSession);
		}
		Optional<DataRegisted> dataRegisted = authService.createCredentialUser(data);
		if(dataRegisted.isEmpty()) {
			throw new UserNotCouldCreatedException();
		}
		
		HttpSession newSession = request.getSession(true);
		sessionManager.createSession(newSession, dataRegisted.get().userId());
		
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> loginUser(DataLogin data, HttpServletRequest request) {
		Optional<DataUserSearched> dataOpt =  authService.searchUserLogin(data);
		
		if(dataOpt.isEmpty()) {
			throw new EmailOrPasswordIncorrectException();
		}
		
		HttpSession currentSession = request.getSession(false);
		if(currentSession != null) {
			sessionManager.deleteSession(currentSession);
			
		}
		
		HttpSession newSession = request.getSession(true);
		sessionManager.createSession(newSession, dataOpt.get().userId());
		
		
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> logoutUser(HttpServletRequest request) {
		HttpSession sesion = request.getSession(false);
		if(sesion != null) {
			sessionManager.deleteSession(sesion);
		}
		
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<UserDataDto> validate(HttpSession session) {
		if (sessionManager.checkSession(session)) {
			UserDataDto dto = authService.getUserDataDto(sessionManager.getKeySession(session).get());
			if(dto != null) {
				return ResponseEntity.ok(dto);
			}
		}
		throw new AuthenticationFailedException();
	}

	@Override
	public ResponseEntity<Boolean> validateTokenTemporaly(String token) {
		if(authService.isValidateToken(token)) {
			return ResponseEntity.ok(true);
		}
		return ResponseEntity.ok(false);
	}

}
