package comparator.ia.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import comparator.ia.app.entities.BrandAliasEntity;
import comparator.ia.app.entities.BrandEntity;

public interface BrandAliasRepository extends JpaRepository<BrandAliasEntity, Long>{
	boolean existsByAliasAndBrand(String alias, BrandEntity entity);
	
	BrandAliasEntity findByAlias(String alias);
}
