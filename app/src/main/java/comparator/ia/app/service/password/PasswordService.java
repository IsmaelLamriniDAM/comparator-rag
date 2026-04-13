package comparator.ia.app.service.password;

import comparator.ia.app.entities.UserEntity;
import comparator.ia.app.entities.UserPasswordEntity;

public interface PasswordService {
	
	void createPassword(String password, UserEntity entity);
	
	boolean existsByPwdAndUser(String pwd, UserEntity entity);
}
