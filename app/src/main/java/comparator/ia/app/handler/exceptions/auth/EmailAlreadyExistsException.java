package comparator.ia.app.handler.exceptions.auth;

public class EmailAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8067842215911768073L;

	private static final String MESSAGE_ERROR = "Este email ya existe en el sistema.";

	public EmailAlreadyExistsException() {
		super(MESSAGE_ERROR);
	}

}
