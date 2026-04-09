package comparator.ia.app.mapper.brandAndModel;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;

import comparator.ia.app.dtos.brand.BrandModel;
import comparator.ia.app.entities.VehicleEntity;

@Mapper(componentModel = "spring")
public interface BrandAndModelMapper {
	
	Set<BrandModel> vehicleEntityToBrandModel(List<VehicleEntity> entity);
	
	
}
