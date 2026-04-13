package comparator.ia.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import comparator.ia.app.entities.TokenTemporalyEntity;

public interface TokenTemporalyRepository extends JpaRepository<TokenTemporalyEntity, Long>{
	
	
	Optional<TokenTemporalyEntity> findByToken(String token);
}
