package comparator.ia.app.handler.exceptions.auth;

import org.springframework.http.ResponseEntity;

public class EmailOrPasswordIncorrectException extends AuthenticationFailedException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2332024584552703383L;
	
	public EmailOrPasswordIncorrectException() {
		super("Email o contraseña incorrecta.");
	}
}
