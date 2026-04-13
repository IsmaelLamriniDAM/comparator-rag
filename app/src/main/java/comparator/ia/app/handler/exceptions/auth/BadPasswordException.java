package comparator.ia.app.handler.exceptions.auth;

public class BadPasswordException extends AuthenticationFailedException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1704812653636383935L;
	
	public BadPasswordException(String message) {
		super(message);
	}

}
