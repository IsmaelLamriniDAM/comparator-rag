package comparator.ia.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import comparator.ia.app.entities.BrandEntity;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

	
	List<BrandEntity> findAllByCommunNameIn(List<String> communName);
	
	Optional<BrandEntity> findBycommunName(String communName);
	
	boolean existsByCommunName(String name);
	
	boolean existsBy();
	
}
