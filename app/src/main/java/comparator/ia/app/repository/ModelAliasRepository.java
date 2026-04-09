package comparator.ia.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import comparator.ia.app.entities.ModelAliasEntity;
import comparator.ia.app.entities.ModelEntity;

public interface ModelAliasRepository extends JpaRepository<ModelAliasEntity, Long>{
	
	boolean existsByAliasAndModel(String alias, ModelEntity entity);
	
	ModelAliasEntity findByAlias(String alias);
	
	List<ModelAliasEntity> findAllByAlias(String alias);
}
