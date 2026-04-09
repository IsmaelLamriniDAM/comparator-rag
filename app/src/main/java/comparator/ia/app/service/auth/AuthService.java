package comparator.ia.app.service.auth;

import java.util.Optional;
import java.util.UUID;

import comparator.ia.app.dtos.auth.UserDataDto;
import comparator.ia.app.dtos.auth.login.DataLogin;
import comparator.ia.app.dtos.auth.login.DataUserSearched;
import comparator.ia.app.dtos.auth.register.DataRegisted;
import comparator.ia.app.dtos.auth.register.DataRegister;
import comparator.ia.app.entities.UserEntity;

public interface AuthService {
	Optional<DataRegisted> createCredentialUser(DataRegister data);
	
	Optional<DataUserSearched> searchUserLogin(DataLogin data);
	
	UserDataDto getUserDataDto(UUID id);
	
	UserEntity getUserEntity(UUID id);
	
	boolean existsUser(UUID id);
	
	boolean isValidateToken(String token);
}
