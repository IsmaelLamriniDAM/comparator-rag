package comparator.ia.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import comparator.ia.app.entities.BrandEntity;
import comparator.ia.app.entities.ModelEntity;

public interface ModelRepository extends JpaRepository<ModelEntity, Long> {
	Optional<ModelEntity> findByModelName(String name);
	
	ModelEntity findByModelNameAndBrand(String modelName, BrandEntity brand);
	
	boolean existsBy();
	
	boolean existsByModelNameAndBrand(String name, BrandEntity brand);

	@Query(value = "SELECT MAX(LENGTH(model_name)) " + "FROM models", nativeQuery = true)
	Integer findMaxModelNameLength();
}
