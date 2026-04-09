package comparator.ia.app.handler.exceptions;

public class EmptyVehiclesInDBException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3591784966892792145L;
	
	private static final String MESSAGE_ERROR = "Not exists vehicles in DB therefore it not cant execute operation.";
	
	public EmptyVehiclesInDBException()  {
		super(MESSAGE_ERROR);
	}
	

}
