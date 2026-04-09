package comparator.ia.app.config.chainOfResponsability.createDataOperation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import comparator.ia.app.repository.BrandRepository;
import comparator.ia.app.repository.ModelRepository;
import comparator.ia.app.service.alias.brand.BrandAliasService;
import comparator.ia.app.service.alias.model.ModelAliasService;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateBrandField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateDataOperation;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateFuelField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateHpField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateKmField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateModelField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateOperationField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreatePriceField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateYearField;
import comparator.ia.app.util.chainOfResponsability.priceMessage.FindOutPriceOperation;

@Configuration
public class ConfigurationCreationDataOperation {
	
	
	private final BrandAliasService brandAliasService;
	
	private final ModelAliasService modelAliasService;
	
	private final FindOutPriceOperation findPriceOperation;
	
	private final BrandRepository brandRepo;
	
	private final ModelRepository modelRepo;
	
	ConfigurationCreationDataOperation( FindOutPriceOperation findPriceOperation, 
			BrandAliasService brandAliasService, ModelAliasService modelAliasService, ModelRepository modelRepo, BrandRepository brandRepo){
		this.brandAliasService = brandAliasService;
		this.modelAliasService = modelAliasService;
		this.findPriceOperation = findPriceOperation;
		this.brandRepo = brandRepo;
		this.modelRepo = modelRepo;}
	
	@Bean
	public CreateDataOperation createOrderFields() {
		CreateDataOperation operation = new CreateOperationField();
		CreateDataOperation brand = new CreateBrandField(brandAliasService);
		CreateDataOperation model = new CreateModelField(modelAliasService, modelRepo, brandRepo);
		CreateDataOperation price = new CreatePriceField(findPriceOperation);
		CreateDataOperation fuel = new CreateFuelField();
		CreateDataOperation year = new CreateYearField();
		CreateDataOperation vh = new CreateHpField();
		CreateDataOperation km = new CreateKmField();
		
		operation.setNextField(brand);
		brand.setNextField(model);
		model.setNextField(price);
		price.setNextField(fuel);
		fuel.setNextField(year);
		year.setNextField(vh);
		vh.setNextField(km);
		
		return operation;
	}
	
	
}
