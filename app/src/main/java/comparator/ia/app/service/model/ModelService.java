package comparator.ia.app.service.model;

import java.util.Set;

import comparator.ia.app.dtos.brand.BrandModel;
import comparator.ia.app.entities.BrandEntity;
import comparator.ia.app.entities.ModelEntity;

public interface ModelService {
	
	void saveModel(Set<BrandModel> brandModels);
	
	ModelEntity getModelByName(String nameModel);
	
	ModelEntity getModelByNameAndBrand(String nameModel, BrandEntity nameBrand);
	
	boolean existsModelsInDB();
}
