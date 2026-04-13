package comparator.ia.app.handler.exceptions.auth;

public class InvalidPasswordException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5508315441529854973L;
	
	private static final String MESSAGE_ERROR = "Contraseña invalida";
	
	public InvalidPasswordException() {
		super(MESSAGE_ERROR);
	}
	
	public InvalidPasswordException(String message) {
		super(message);
	}


}
