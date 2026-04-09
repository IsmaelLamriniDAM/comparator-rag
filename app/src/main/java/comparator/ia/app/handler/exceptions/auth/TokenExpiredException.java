package comparator.ia.app.handler.exceptions.auth;

public class TokenExpiredException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1603125505157343632L;
	
	private static final String MESSAGE_ERROR = "El enlace de recuperación ha caducado. Por favor, solicita uno nuevo.";
	
	public TokenExpiredException() {
		super(MESSAGE_ERROR);
	}

}
