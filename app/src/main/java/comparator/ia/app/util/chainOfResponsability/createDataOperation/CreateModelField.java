package comparator.ia.app.util.chainOfResponsability.createDataOperation;

import java.util.List;
import java.util.Optional;

import org.hibernate.NonUniqueResultException;

import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.entities.BrandEntity;
import comparator.ia.app.entities.ModelAliasEntity;
import comparator.ia.app.repository.BrandRepository;
import comparator.ia.app.repository.ModelRepository;
import comparator.ia.app.service.alias.model.ModelAliasService;
import comparator.ia.app.util.singleton.Normalizare;

public class CreateModelField extends CreateDataOperation {

	private final ModelAliasService aliasService;
	
	private final ModelRepository modelRepo;
	
	private final BrandRepository brandRepo;
	
	public CreateModelField(ModelAliasService aliasService, ModelRepository modelRepo, BrandRepository brandRepo) {
		this.aliasService = aliasService;
		this.modelRepo = modelRepo;
		this.brandRepo = brandRepo;
	}
	
	@Override
	public void buildFields(String message, SimilarCarOperationDTO car)  throws NonUniqueResultException{
		logger.info("creating model field");
		
		int numOpportunitiesTotal = modelRepo.findMaxModelNameLength();
		
		String[] tokens = Normalizare.getInstance().normalizater(message).split("\\s+");
		boolean existsModel = false;
		int index = 0;
		int interations = 0;
		
		while(!existsModel && interations < tokens.length) {
			int opportunitiesSpent = 0;
			index = interations;
			StringBuilder sb = new StringBuilder();
			
			while(index < tokens.length && !existsModel) {
				if(opportunitiesSpent < numOpportunitiesTotal) {
					sb.append(tokens[index]);
					ModelAliasEntity entity = searchModelAlias(sb.toString(), car);
					if(entity != null) {
						car.setModel(entity.getModel().getModelName());
						existsModel = true;
					} else {
						car.setModel(null);
					}
					opportunitiesSpent++;
				}
				index++;
			}
			
			interations++;
		}
		
		logger.info("created model field -> {}", car.getModel());
		nextField.buildFields(message, car);
	}
	
	private ModelAliasEntity searchModelAlias(String possibleAlias, SimilarCarOperationDTO car) {
		if (car.getBrand() != null) {
			return searchForTheBrandModel(possibleAlias, car);	
		}
		return searchModelWithoutBrand(possibleAlias);
	}

	private ModelAliasEntity searchModelWithoutBrand(String possibleAlias) throws NonUniqueResultException{
		return aliasService.getModelAlias(possibleAlias);
	}

	private ModelAliasEntity searchForTheBrandModel(String possibleAlias, SimilarCarOperationDTO car) {
		BrandEntity brand = brandRepo.findBycommunName(car.getBrand()).get();

		List<ModelAliasEntity> entities = aliasService.getModelsAlias(possibleAlias);
		if (entities == null || entities.isEmpty()) {
			return null;
		}
		
		Optional<ModelAliasEntity> aliasOpt = entities.stream().filter(e -> e.getModel().getBrand().getCommunName().equals(brand.getCommunName()))
				.findFirst();
		
		if(aliasOpt.isPresent()){
			return aliasOpt.get();
		}
		
		return null;
	}

}
