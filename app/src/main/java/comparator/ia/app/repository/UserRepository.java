package comparator.ia.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import comparator.ia.app.entities.UserEntity;
import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
	
	boolean existsByEmail(String email);
	
	Optional<UserEntity> findByEmail(String email);
	
	boolean existsById(UUID id);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE users SET comparisons = comparisons + 1 WHERE id = :id", nativeQuery = true)
	int addComparisonsForThisUser(@Param("id") UUID id);
	
	@Query("SELECT COUNT(u) > 0 FROM UserEntity u " +
	           "JOIN UserPasswordEntity p ON p.user = u " +
	           "WHERE u.id = :id AND p.password = :passwordHash")
	    boolean existsByEmailAndPasswordHash(@Param("id") UUID id, 
	                                         @Param("passwordHash") String passwordHash);
}
