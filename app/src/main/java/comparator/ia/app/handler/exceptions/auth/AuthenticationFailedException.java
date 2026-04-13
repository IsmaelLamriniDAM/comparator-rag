package comparator.ia.app.handler.exceptions.auth;

public class AuthenticationFailedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 694961194135992416L;
	
	private static final String MESSAGE_ERROR = "Autenticación fallida";
	
	public AuthenticationFailedException(String message) {
		super(message);
	}
	
	public AuthenticationFailedException() {
		super(MESSAGE_ERROR);
	}

}
