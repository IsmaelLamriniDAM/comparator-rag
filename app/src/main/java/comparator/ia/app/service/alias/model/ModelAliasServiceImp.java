package comparator.ia.app.service.alias.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.NonUniqueResultException;
import org.slf4j.Logger;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import comparator.ia.app.entities.BrandEntity;
import comparator.ia.app.entities.ModelAliasEntity;
import comparator.ia.app.entities.ModelEntity;
import comparator.ia.app.repository.ModelAliasRepository;
import comparator.ia.app.repository.ModelRepository;
import comparator.ia.app.service.brand.BrandService;
import comparator.ia.app.util.singleton.Normalizare;
import comparator.ia.app.util.singleton.alias.model.AliasCreatorModelInput;

@Service
public class ModelAliasServiceImp implements ModelAliasService {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(ModelAliasServiceImp.class);
	
	private final ModelAliasRepository modelAliasRepository;
	
	private final ModelRepository modelRepo;
	
	private final BrandService brandService;
	
	ModelAliasServiceImp(ModelAliasRepository modelAliasRepository, BrandService brandService, ModelRepository modelRepo){
		this.modelAliasRepository = modelAliasRepository;
		this.modelRepo = modelRepo;
		this.brandService = brandService;}

	@Override
	public void saveAlias(String nameModel, String aliasModel, String brand) {
		BrandEntity brandEntity = brandService.getBrandForThisName(brand.toUpperCase());
		ModelEntity	model = modelRepo.findByModelNameAndBrand(nameModel.toUpperCase(), brandEntity);
		if(model == null ) {
			logger.warn("Not found model with this name model: {}", nameModel);
		} else {
			ModelAliasEntity modelAliasEntity = new ModelAliasEntity();
			String normalizateAlias = Normalizare.getInstance().normalizater(aliasModel);
			
			if(!modelAliasRepository.existsByAliasAndModel(normalizateAlias, model)) {
				modelAliasEntity.setAlias(normalizateAlias);
				
				modelAliasEntity.setModel(model);
				modelAliasRepository.save(modelAliasEntity);
			}
		}
	}

	@Override
	public ModelAliasEntity getModelAlias(String input) throws NonUniqueResultException {
		return modelAliasRepository.findByAlias(input);
	}

	@Override
	public void saveAlias(String aliasModel, ModelEntity model) {
		Set<String> diferentAlias = AliasCreatorModelInput.getInstance().createAlias(aliasModel);
		
		for(String oneAlias : diferentAlias) {
			ModelAliasEntity modelAliasEntity = new ModelAliasEntity();
			modelAliasEntity.setAlias(oneAlias);
			modelAliasEntity.setModel(model);
			
			modelAliasRepository.save(modelAliasEntity);
		}
	}

	@Override
	public List<ModelAliasEntity> getModelsAlias(String input) {
		return modelAliasRepository.findAllByAlias(input);
	}

}
