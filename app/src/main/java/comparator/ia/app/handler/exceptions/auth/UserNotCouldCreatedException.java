package comparator.ia.app.handler.exceptions.auth;

public class UserNotCouldCreatedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7713389162208764321L;

	private static final String MESSAGE_ERROR = "No se ha podido registrar al usuario actual.";

	public UserNotCouldCreatedException() {
		super(MESSAGE_ERROR);
	}

}
