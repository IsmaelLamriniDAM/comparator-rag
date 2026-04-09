package comparator.ia.app.util.schedule.brandModel;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import comparator.ia.app.dtos.brand.BrandModel;
import comparator.ia.app.service.brand.BrandService;
import comparator.ia.app.service.model.ModelService;
import comparator.ia.app.service.vector.VectorService;
import comparator.ia.app.util.strategy.brandModel.StrategyScrapBrandModel;

@Component
public class ScrapperBrandModel {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(ScrapperBrandModel.class);
	
	private final ModelService modelService;
	private final BrandService brandService;
	private final VectorService vectorService;
	
	ScrapperBrandModel(VectorService vectorService, ModelService modelService, BrandService brandService){
		this.modelService = modelService;
		this.brandService = brandService;
		this.vectorService = vectorService;}
	
	@Transactional
	public void scrap(StrategyScrapBrandModel strategy) {
		logger.info("Scrapping...");
		
		Set<BrandModel> brandAndModels = strategy.scrapBrandModel();
		
		if(brandAndModels.isEmpty()) {
			logger.warn("List brandAndModels is empty.");
		}
		
		brandService.saveBrands(brandAndModels.stream().map(bm -> bm.brand()).collect(Collectors.toSet()));
		modelService.saveModel(brandAndModels);
		vectorService.createEmbbeding(brandAndModels);
		
		logger.info("Terminate persisted and embbedable in DB.");
	}
	
	
}
