package comparator.ia.app.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import comparator.ia.app.dtos.auth.login.DataLogin;
import comparator.ia.app.dtos.user.DataChangeForgottPwd;
import comparator.ia.app.dtos.user.DataChangePwdUserDto;
import comparator.ia.app.dtos.user.DataUpdateUserDto;
import comparator.ia.app.handler.exceptions.auth.AuthenticationFailedException;
import comparator.ia.app.service.email.EmailService;
import comparator.ia.app.service.user.UserService;
import comparator.ia.app.util.auth.SessionManager;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Controller-Change-Profile-User")
public class UserControllerImp implements UserController{
	
	private final UserService userService;
    private final SessionManager sessionManager;
    private final EmailService emailService;

    UserControllerImp(SessionManager sessionManager, UserService userService, EmailService emailService) {
        this.sessionManager = sessionManager;
        this.userService = userService;
		this.emailService = emailService;
    }

	@Override
	public ResponseEntity<Void> userChangePassword(@Valid DataChangePwdUserDto pwdUserDto, HttpSession sesion) {
		if(!sessionManager.checkSession(sesion)) {
			throw new AuthenticationFailedException();
		}
		
		userService.changePwd(sessionManager.getKeySession(sesion).get(), pwdUserDto);
			
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> userUpdateProfile(@Valid DataUpdateUserDto userUpdateDto, HttpSession sesion) {
		if(!sessionManager.checkSession(sesion)) {
			throw new AuthenticationFailedException();
		}
		
		userService.userUpdateProfile(sessionManager.getKeySession(sesion).get(), userUpdateDto);
		
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> userRemove(@Valid DataLogin userDto, HttpSession sesion) {
		if(!sessionManager.checkSession(sesion)) {
			throw new AuthenticationFailedException();
		}
		
		if(!userService.isExistsLogin(sessionManager.getKeySession(sesion).get(), userDto)) {
			return ResponseEntity.notFound().build();
		}
		
		userService.removeUser(sessionManager.getKeySession(sesion).get(), userDto);
		
		sessionManager.deleteSession(sesion);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> forgottenPassword(String email) {
		if(emailService.hasBeenSendEmail(email)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}

	@Override
	public ResponseEntity<Void> resetPwd(DataChangeForgottPwd dto) {
		userService.resetPwd(dto);
		return ResponseEntity.noContent().build();
	}

}
