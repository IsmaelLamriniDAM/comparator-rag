package comparator.ia.app.service.user;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import comparator.ia.app.dtos.auth.login.DataLogin;
import comparator.ia.app.dtos.user.DataChangeForgottPwd;
import comparator.ia.app.dtos.user.DataChangePwdUserDto;
import comparator.ia.app.dtos.user.DataUpdateUserDto;
import comparator.ia.app.entities.TokenTemporalyEntity;
import comparator.ia.app.entities.UserEntity;
import comparator.ia.app.entities.UserPasswordEntity;
import comparator.ia.app.handler.exceptions.auth.AuthenticationFailedException;
import comparator.ia.app.handler.exceptions.auth.BadPasswordException;
import comparator.ia.app.handler.exceptions.auth.InvalidPasswordException;
import comparator.ia.app.handler.exceptions.auth.TokenExpiredException;
import comparator.ia.app.handler.exceptions.auth.TokenTemporalyIsEmptyException;
import comparator.ia.app.repository.PasswordRepository;
import comparator.ia.app.repository.TokenTemporalyRepository;
import comparator.ia.app.repository.UserRepository;
import comparator.ia.app.service.auth.AuthService;
import comparator.ia.app.util.singleton.hashPwd.CreatorHashPwd;

@Service()
public class UserServiceImp implements UserService{
	
	private static Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
	private final UserRepository userRepo;
	private final CreatorHashPwd hashPwd;
	private final PasswordRepository pwdRepo;
	private final TokenTemporalyRepository tokenRepo;
	private final AuthService authService;
	
	UserServiceImp(UserRepository userRepo, CreatorHashPwd hashPwd, PasswordRepository pwdRepo, TokenTemporalyRepository tokenRepo, AuthService authService){
		this.userRepo = userRepo;
		this.hashPwd = hashPwd;
		this.pwdRepo = pwdRepo;
		this.tokenRepo = tokenRepo;
		this.authService = authService;}

	@Override
	public void addComparisons(UUID id) {
		int row = userRepo.addComparisonsForThisUser(id);
		if(row == 1) {
			logger.info("Has been added one comparisons more.");
		} else {
			logger.warn("Not has been added one comparisons more.");
		}
		
	}

	@Override
	public void changePwd(UUID id, DataChangePwdUserDto pwdUserDto) {
		if(!verificateUser(id, pwdUserDto.pwdCurrent())) {
			logger.warn("CREDENTIALS INCORRECTS");
			throw new BadPasswordException("La contraseña es incorrecta. Por favor intentelo de nuevo.");
		}

		UserPasswordEntity userPassword = pwdRepo.findByUser_id(id);
		
		if(pwdUserDto.pwdCurrent().equals(pwdUserDto.pwdNew())) {
			logger.warn("CREDENTIALS INCORRECTS");
			throw new InvalidPasswordException("La nueva contraseña no puede ser igual a la antigua.");
		}
		
		if(!(pwdUserDto.pwdNew().equals(pwdUserDto.pwdNewConfirm()))) {
			logger.warn("CREDENTIALS INCORRECTS");
			throw new InvalidPasswordException("La nueva contraseña no coincide con la contraseña de confirmación");
		}
		
		logger.info("CHANGE PWD SUCCESSFULY");
		userPassword.setPassword(hashPwd.createHash(pwdUserDto.pwdNewConfirm()));
		pwdRepo.save(userPassword);
		
	}

	@Override
	public boolean verificateUser(UUID id, String password) {
		return userRepo.existsByEmailAndPasswordHash(id, hashPwd.createHash(password));
	}

	@Override
	public void userUpdateProfile(UUID id, DataUpdateUserDto userUpdateDto) {
		if(!existUser(id)) {
			logger.warn("USER NOT FOUND");
			throw new AuthenticationFailedException();
		}
		
		UserEntity userData = userRepo.findById(id).get();
		userData.setName(userUpdateDto.name());
		userData.setPhone(userUpdateDto.phoneNumber());
		userData.setUpdatedAt(LocalDateTime.now());
		userRepo.save(userData);
		
	}
	
	@Override
	public boolean existUser(UUID id) {
		return userRepo.existsById(id);
	}

	@Override
	public void removeUser(UUID id, DataLogin userDto) {
		if(!verificateUser(id, userDto.password())) {
			logger.warn("CREDENTIALS INCORRECTS");
			throw new AuthenticationFailedException();
		}
		userRepo.deleteById(id);
		
	}

	@Override
	public boolean isExistsLogin(UUID id, DataLogin userDto) {
		return verificateUser(id, userDto.password());
	}

	@Override
	public Optional<UserEntity> getUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	@Transactional
	public void resetPwd(DataChangeForgottPwd dto) {
		Optional<TokenTemporalyEntity> entityToken = tokenRepo.findByToken(dto.token());
		
		if(entityToken.isEmpty()) {
			throw new TokenTemporalyIsEmptyException();
		}
		
		if(!authService.isValidateToken(entityToken.get().getToken())) {
			throw new TokenExpiredException();
		}
		
		if(!(dto.newPwd().equals(dto.checkPwd()))) {
			logger.warn("CREDENTIALS INCORRECTS");
			throw new InvalidPasswordException("La nueva contraseña no coincide con la contraseña de confirmación");
		}
		
		UserEntity user = entityToken.get().getUser();
		
		if(user.getPassword().getPassword().equals(hashPwd.createHash(dto.checkPwd()))) {
			logger.warn("CREDENTIALS INCORRECTS");
			throw new InvalidPasswordException("La nueva contraseña no puede coincidir con la antigua.");
		}
		
		user.getPassword().setPassword(hashPwd.createHash(dto.checkPwd()));
		userRepo.save(user);
		tokenRepo.delete(entityToken.get());
	}

}
