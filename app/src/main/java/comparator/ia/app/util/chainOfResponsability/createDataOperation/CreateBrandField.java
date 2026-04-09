package comparator.ia.app.util.chainOfResponsability.createDataOperation;

import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.entities.BrandAliasEntity;
import comparator.ia.app.service.alias.brand.BrandAliasService;
import comparator.ia.app.util.singleton.Normalizare;

public class CreateBrandField extends CreateDataOperation {
	
	private final BrandAliasService aliasService;
	
	public CreateBrandField(BrandAliasService aliasService) {
		this.aliasService = aliasService;
	}

	@Override
	public void buildFields(String message, SimilarCarOperationDTO car) {
		logger.info("creating brand field");
		
		String[] tokens = message.split("\\s+"); 
		boolean existsBrand = false;
		int index = 0;
		
		while(index < tokens.length && !existsBrand) {
			String normalitedToken = Normalizare.getInstance().normalizater(tokens[index]);
			BrandAliasEntity entity = aliasService.getBrandAlias(normalitedToken);
			if(entity != null) {
				car.setBrand(entity.getBrand().getCommunName());
				existsBrand = true;
			}
			index++;
		}
		
		logger.info("created brand field -> {}", car.getBrand());
		nextField.buildFields(message, car);
	}

}
