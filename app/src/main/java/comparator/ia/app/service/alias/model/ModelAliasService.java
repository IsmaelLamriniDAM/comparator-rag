package comparator.ia.app.service.alias.model;

import java.util.List;

import comparator.ia.app.entities.ModelAliasEntity;
import comparator.ia.app.entities.ModelEntity;

public interface ModelAliasService {
	
	void saveAlias(String nameModel, String aliasModel, String brand);
	
	void saveAlias(String aliasModel, ModelEntity model);
	
	ModelAliasEntity getModelAlias(String input);
	
	List<ModelAliasEntity> getModelsAlias(String input);
	
}
