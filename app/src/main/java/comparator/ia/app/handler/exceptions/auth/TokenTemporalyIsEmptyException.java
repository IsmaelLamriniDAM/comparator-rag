package comparator.ia.app.handler.exceptions.auth;

public class TokenTemporalyIsEmptyException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1968152798445910132L;
	
	private static final String MESSAGE_ERROR = "El token recibido no sea encontrado.";
	
	public TokenTemporalyIsEmptyException() {
		super(MESSAGE_ERROR);
	}

}
