package comparator.ia.app.handler.exceptions;

public class EmptyVehicleListException extends RuntimeException {

	private static final long serialVersionUID = -8383407452012927909L;

	public EmptyVehicleListException() {
		super();
	}

	public EmptyVehicleListException(String message) {
		super(message);
	}

}
