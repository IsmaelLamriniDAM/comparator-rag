package comparator.ia.app.service.password;

import org.springframework.stereotype.Service;

import comparator.ia.app.entities.UserEntity;
import comparator.ia.app.entities.UserPasswordEntity;
import comparator.ia.app.repository.PasswordRepository;
import comparator.ia.app.util.singleton.hashPwd.CreatorHashPwd;

@Service
public class PasswordServiceImp implements PasswordService {
	
	
	private final PasswordRepository repo;
	
	private final CreatorHashPwd hashPwd;

	PasswordServiceImp(PasswordRepository repo, CreatorHashPwd hashPwd){
		this.repo = repo;
		this.hashPwd = hashPwd;}
	
	@Override
	public void createPassword(String password, UserEntity userEntity) {
		UserPasswordEntity entity = new UserPasswordEntity();
		entity.setUser(userEntity);
		entity.setPassword(hashPwd.createHash(password));
		repo.save(entity);
	}

	@Override
	public boolean existsByPwdAndUser(String pwd, UserEntity entity) {
		String pwdHashed = hashPwd.createHash(pwd);
		return repo.existsByPasswordAndUser(pwdHashed, entity);
	}

}
