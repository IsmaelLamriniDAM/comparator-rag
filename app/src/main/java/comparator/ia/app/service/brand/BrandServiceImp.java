package comparator.ia.app.service.brand;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import comparator.ia.app.dtos.brand.BrandModel;
import comparator.ia.app.dtos.brandModel.BrandModelSend;
import comparator.ia.app.entities.BrandEntity;
import comparator.ia.app.repository.BrandRepository;
import comparator.ia.app.service.alias.brand.BrandAliasService;

@Service
public class BrandServiceImp implements BrandService {
	
	private static final Logger logger = LoggerFactory.getLogger(BrandServiceImp.class);
	
	private final BrandRepository brandRepo;
	
	private final BrandAliasService aliasService;
	
	public BrandServiceImp(BrandRepository brandRepo, BrandAliasService aliasService) {
		this.brandRepo = brandRepo;
		this.aliasService = aliasService;
	}
	
	@Override
	public BrandEntity getBrandForThisName(String name) {
		Optional<BrandEntity> dtoOpt = brandRepo.findBycommunName(name.toUpperCase());
		if(dtoOpt.isPresent()) {
			return dtoOpt.get();
		}
		
		return null;
	}

	@Override
	public void saveBrands(Set<String> nameBrands) {
		logger.info("saving makes in db.");
		for(String brandName : nameBrands) {
			if(!brandRepo.existsByCommunName(brandName)) {
				BrandEntity entity = new BrandEntity();
				entity.setCommunName(brandName);
				
				BrandEntity entitySaved =  brandRepo.save(entity);
				aliasService.saveAlias(brandName, entitySaved);
			}
		}
		logger.info("Terminate persisted in db.");
	}

	@Override
	public boolean existsBrandsInDB() {
		return brandRepo.existsBy();
	}

	@Override
	public List<BrandModelSend> getAllBrandsWithHisModels() {
		List<BrandModelSend> list = new ArrayList<>();
		for (BrandEntity entity : brandRepo.findAll()) {
			list.add(new BrandModelSend(entity.getCommunName(), entity
					.getModels()
					.stream()
					.map(m -> m.getModelName())
					.sorted(Comparator.naturalOrder())
					.toList()));
		}
		
		return list.stream().sorted(Comparator.comparing(BrandModelSend::brand)).toList();
	}

	

}
