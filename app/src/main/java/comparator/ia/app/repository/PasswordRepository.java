package comparator.ia.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import comparator.ia.app.entities.UserEntity;
import comparator.ia.app.entities.UserPasswordEntity;

public interface PasswordRepository extends JpaRepository<UserPasswordEntity, UUID> {
	
	boolean existsByPasswordAndUser(String pwd, UserEntity user);
	
	UserPasswordEntity findByUser_id(UUID id);
}
