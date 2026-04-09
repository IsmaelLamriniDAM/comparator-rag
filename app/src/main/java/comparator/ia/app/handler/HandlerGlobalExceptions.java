package comparator.ia.app.handler;

import org.hibernate.NonUniqueResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import comparator.ia.app.handler.exceptions.EmptyVehiclesInDBException;
import comparator.ia.app.handler.exceptions.auth.AuthenticationFailedException;
import comparator.ia.app.handler.exceptions.auth.EmailAlreadyExistsException;
import comparator.ia.app.handler.exceptions.auth.InvalidPasswordException;
import comparator.ia.app.handler.exceptions.auth.TokenExpiredException;
import comparator.ia.app.handler.exceptions.auth.TokenTemporalyIsEmptyException;
import comparator.ia.app.handler.exceptions.auth.UserNotCouldCreatedException;

@ControllerAdvice
public class HandlerGlobalExceptions {

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<String> emailAlreadyExistsException(EmailAlreadyExistsException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(UserNotCouldCreatedException.class)
	public ResponseEntity<String> userNotCouldCreatedException(UserNotCouldCreatedException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AuthenticationFailedException.class)
	public ResponseEntity<String> authenticationFailedException(AuthenticationFailedException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(NonUniqueResultException.class)
	public ResponseEntity<String> handleNonUniqueException(NonUniqueResultException ex) {
	    return new ResponseEntity<>("Se han encontrado múltiples resultados para esta búsqueda. Por favor, sea más específico.", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(EmptyVehiclesInDBException.class)
	public ResponseEntity<String> emptyVehiclesInDBException(EmptyVehiclesInDBException ex) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.PRECONDITION_FAILED);
	}
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<String> invalidPasswordException(InvalidPasswordException ex) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(TokenTemporalyIsEmptyException.class)
	public ResponseEntity<String> tokenTemporalyIsEmptyException(TokenTemporalyIsEmptyException ex) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<String> tokenExpiredException(TokenExpiredException ex) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.GONE);
	}
}
