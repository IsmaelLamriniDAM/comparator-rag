package comparator.ia.app.util.schedule.vehicle;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import comparator.ia.app.dtos.brand.BrandModel;
import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.mapper.brandAndModel.BrandAndModelMapper;
import comparator.ia.app.mapper.brandEntity.BrandMapper;
import comparator.ia.app.service.brand.BrandService;
import comparator.ia.app.service.model.ModelService;
import comparator.ia.app.service.vector.VectorService;
import comparator.ia.app.service.vehicle.VehicleService;
import comparator.ia.app.util.strategy.vehicle.api.VehicleScrapperStrategy;
	
@Component
public class Scrapper {
	
	private static final Logger logger = LoggerFactory.getLogger(Scrapper.class);
	
	private final VehicleService service;
	
	private final BrandService brandService;
	
	private final ModelService modelService;
	
	public Scrapper(VehicleService service, BrandService brandService, ModelService modelService) {
		this.service = service;
		this.brandService = brandService;
		this.modelService = modelService;
	}
	
	@Transactional
	public void scrap(VehicleScrapperStrategy strategy) {
		if(brandService.existsBrandsInDB() && modelService.existsModelsInDB()) {
			service.save(strategy.scrap());
			logger.info("The cars have been persistenced in DDBB.");
		} else {
			logger.warn("You not should persisted cars if brands not exists in db.");
		}
	}
}
