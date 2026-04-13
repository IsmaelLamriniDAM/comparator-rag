package comparator.ia.app.service.alias.brand;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import comparator.ia.app.entities.BrandAliasEntity;
import comparator.ia.app.entities.BrandEntity;
import comparator.ia.app.repository.BrandAliasRepository;
import comparator.ia.app.repository.BrandRepository;
import comparator.ia.app.util.singleton.Normalizare;
import comparator.ia.app.util.singleton.alias.brand.AliasCreatorBrandInput;

@Service
public class BrandAliasServiceImp implements BrandAliasService {
	
	private final BrandAliasRepository repo;
	private final BrandRepository brandRepo;
	
	BrandAliasServiceImp(BrandAliasRepository repo, BrandRepository brandRepo){
		this.repo = repo;
		this.brandRepo = brandRepo;
		}

	@Override
	public void saveAlias(String brandName, String brandAlias) {
		Optional<BrandEntity> entity = brandRepo.findBycommunName(brandName.toUpperCase());
		BrandAliasEntity entityAlias = new BrandAliasEntity();
		String normalizateAliasd = Normalizare.getInstance().normalizater(brandAlias);
		
		if (entity.isPresent() && !repo.existsByAliasAndBrand(normalizateAliasd, entity.get())) {
			entityAlias.setAlias(normalizateAliasd);

			entityAlias.setBrand(entity.get());
			repo.save(entityAlias);
		}
	}

	@Override
	public BrandAliasEntity getBrandAlias(String input) {
		return repo.findByAlias(input);
	}

	@Override
	public void saveAlias(String alias, BrandEntity entity) {
		Set<String> diferentAlias = AliasCreatorBrandInput.getInstance().createAlias(alias);
		
		for(String oneAlias : diferentAlias) {
			BrandAliasEntity entityAlias = new BrandAliasEntity();
			entityAlias.setAlias(oneAlias);
			entityAlias.setBrand(entity);
			repo.save(entityAlias);
		}
	}

}
