package comparator.ia.app.service.alias.brand;

import comparator.ia.app.entities.BrandAliasEntity;
import comparator.ia.app.entities.BrandEntity;

public interface BrandAliasService {
	
	void saveAlias(String brandName, String brandAlias);
	
	void saveAlias(String alias, BrandEntity entity);
	
	BrandAliasEntity getBrandAlias(String input);
}
