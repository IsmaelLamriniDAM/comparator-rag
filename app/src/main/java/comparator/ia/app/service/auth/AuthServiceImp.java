package comparator.ia.app.service.auth;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import comparator.ia.app.dtos.auth.UserDataDto;
import comparator.ia.app.dtos.auth.login.DataLogin;
import comparator.ia.app.dtos.auth.login.DataUserSearched;
import comparator.ia.app.dtos.auth.register.DataRegisted;
import comparator.ia.app.dtos.auth.register.DataRegister;
import comparator.ia.app.entities.TokenTemporalyEntity;
import comparator.ia.app.entities.UserEntity;
import comparator.ia.app.handler.exceptions.auth.EmailAlreadyExistsException;
import comparator.ia.app.repository.TokenTemporalyRepository;
import comparator.ia.app.repository.UserRepository;
import comparator.ia.app.service.password.PasswordService;
import jakarta.validation.Valid;

@Service
public class AuthServiceImp implements AuthService{
	
	private static final int LIMIT_MAX = 15;
	private final UserRepository userRepo;
	private final PasswordService pwdService;
	private final TokenTemporalyRepository tokenRepo;
	
	AuthServiceImp(UserRepository userRepo, PasswordService pwdService, TokenTemporalyRepository tokenRepo){
		this.userRepo = userRepo;
		this.pwdService = pwdService;
		this.tokenRepo = tokenRepo;
		}
	
	@Override
	public Optional<DataRegisted> createCredentialUser(DataRegister data) {
		if(userRepo.existsByEmail(data.email())) {
			throw new EmailAlreadyExistsException();
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setName(data.name());
		userEntity.setEmail(data.email());
		userEntity.setPhone(data.phone());
		userEntity.setNumComparisons(0);
		
		UserEntity entity = userRepo.save(userEntity);
		pwdService.createPassword(data.password(), entity);
		
		
		return Optional.of(new DataRegisted(entity.getId()));
	}

	@Override
	public Optional<DataUserSearched> searchUserLogin(@Valid DataLogin data) {
		UserEntity entity = userRepo.findByEmail(data.email()).get();
		if(entity == null) {
			return Optional.empty();
		}
		
		if(pwdService.existsByPwdAndUser(data.password(), entity)) {
			return Optional.of(new DataUserSearched(entity.getId()));
		}
		
		return Optional.empty();
	}

	@Override
	public UserDataDto getUserDataDto(UUID id) {
		Optional<UserEntity> posibleUser = userRepo.findById(id);
		 
		if(posibleUser.isPresent()) {
			return new UserDataDto(posibleUser.get().getName(), 
					/*posibleUser.get().getEmail(),*/ posibleUser.get().getPhone(), 
					posibleUser.get().getUpdatedAt(), posibleUser.get().getNumComparisons());
		}
		return null;
	}

	@Override
	public boolean existsUser(UUID id) {
		return userRepo.existsById(id);
	}

	@Override
	public UserEntity getUserEntity(UUID id) {
		Optional<UserEntity> posibleUser = userRepo.findById(id);
		if(posibleUser.isPresent()) {
			return posibleUser.get();
		}
		return null;
	}

	@Override
	public boolean isValidateToken(String token) {
		Optional<TokenTemporalyEntity> entity = tokenRepo.findByToken(token);
		if(entity.isEmpty()) {return false;}
		
		return ChronoUnit.MINUTES.between(entity.get().getExpiryDate(), LocalDateTime.now()) < LIMIT_MAX;
	}


}
