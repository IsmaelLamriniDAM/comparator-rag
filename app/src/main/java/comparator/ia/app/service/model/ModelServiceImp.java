package comparator.ia.app.service.model;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import comparator.ia.app.dtos.brand.BrandModel;
import comparator.ia.app.entities.BrandEntity;
import comparator.ia.app.entities.ModelEntity;
import comparator.ia.app.repository.ModelRepository;
import comparator.ia.app.service.alias.model.ModelAliasService;
import comparator.ia.app.service.brand.BrandService;

@Service
public class ModelServiceImp implements ModelService {
	
	private static final Logger logger = LoggerFactory.getLogger(ModelServiceImp.class);
	
	private final ModelRepository modelRepo;
	
	private final BrandService brandService;
	
	private final ModelAliasService modelAliasService;
	
	ModelServiceImp(ModelRepository modelRepo, BrandService brandService, ModelAliasService modelAliasService){
		this.modelRepo = modelRepo;
		this.brandService = brandService;
		this.modelAliasService = modelAliasService;
	}

	@Override
	public void saveModel(Set<BrandModel> brandModels) {
		logger.info("saving models in db.");

		for (BrandModel brandModel : brandModels) {
			for (String modelName : brandModel.model()) {
				BrandEntity brandEnt = brandService.getBrandForThisName(brandModel.brand().toUpperCase());
				if(brandEnt != null && !modelRepo.existsByModelNameAndBrand(modelName.toUpperCase(), brandEnt)) {
					ModelEntity modelEntity = new ModelEntity();
					modelEntity.setModelName(modelName.toUpperCase());

					modelEntity.setBrand(brandEnt);

					ModelEntity modelSaved = modelRepo.save(modelEntity);
					modelAliasService.saveAlias(modelName.toUpperCase(), modelSaved);
				}
			}
		}
		logger.info("Terminate persisted in db.");
	}

	@Override
	public ModelEntity getModelByName(String nameModel) {
		Optional<ModelEntity> modelOpt = modelRepo.findByModelName(nameModel.toUpperCase());
		if(modelOpt.isPresent()) {
			return modelOpt.get();
		}
		
		return null;
	}

	@Override
	public ModelEntity getModelByNameAndBrand(String nameModel, BrandEntity brand) {
		ModelEntity entity = modelRepo.findByModelNameAndBrand(nameModel.toUpperCase(), brand);
		if(entity != null)  {
			return entity;
		}
		return null;
	}

	@Override
	public boolean existsModelsInDB() {
		return modelRepo.existsBy();
	}

}
