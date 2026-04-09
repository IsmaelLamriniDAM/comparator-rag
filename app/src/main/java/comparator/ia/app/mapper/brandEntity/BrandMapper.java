package comparator.ia.app.mapper.brandEntity;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import comparator.ia.app.dtos.brand.DataBrandDto;
import comparator.ia.app.dtos.brand.ResponseBrandApiDto;
import comparator.ia.app.entities.BrandEntity;


@Mapper(componentModel = "spring")
public interface BrandMapper {
	
	List<DataBrandDto> brandEntityToDataBrandDto(List<BrandEntity> entities);
	
	DataBrandDto brandEntityToDataBrandDto(BrandEntity entity);
	
	BrandEntity dataBrandDtoToBrandEntity(DataBrandDto dataBrandDto);
	
	
}
