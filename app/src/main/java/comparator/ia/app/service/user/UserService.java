package comparator.ia.app.service.user;

import java.util.Optional;
import java.util.UUID;

import comparator.ia.app.dtos.auth.login.DataLogin;
import comparator.ia.app.dtos.user.DataChangeForgottPwd;
import comparator.ia.app.dtos.user.DataChangePwdUserDto;
import comparator.ia.app.dtos.user.DataUpdateUserDto;
import comparator.ia.app.entities.UserEntity;

public interface UserService {
	void addComparisons(UUID id);
	
	void changePwd(UUID id, DataChangePwdUserDto pwdUserDto);
	
	boolean verificateUser(UUID id, String password);
	
	void userUpdateProfile(UUID id, DataUpdateUserDto userUpdateDto);
	
	boolean existUser(UUID id);
	
	void removeUser(UUID id, DataLogin userDto);
	
	boolean isExistsLogin(UUID id, DataLogin userDto);
	
	Optional<UserEntity>  getUserByEmail(String email);
	
	void resetPwd(DataChangeForgottPwd dto);
}
