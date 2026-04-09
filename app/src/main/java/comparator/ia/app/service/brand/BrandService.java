package comparator.ia.app.service.brand;

import java.util.List;
import java.util.Set;

import comparator.ia.app.dtos.brandModel.BrandModelSend;
import comparator.ia.app.entities.BrandEntity;

public interface BrandService {
	
	BrandEntity getBrandForThisName(String name);
	
	void saveBrands(Set<String> nameBrands);
	
	boolean existsBrandsInDB();
	
	List<BrandModelSend> getAllBrandsWithHisModels();
}
