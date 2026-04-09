package epp.tgf.app.mapper.brandEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Import;

import comparator.ia.app.dtos.brand.DataBrandDto;
import comparator.ia.app.entities.BrandEntity;
import comparator.ia.app.mapper.brandEntity.BrandMapper;


@Import(BrandMapper.class)
class BrandEntityMapperTest {
	
	private static final List<String> COMMUNNAME = List.of("BMW", "TOYOTA");
	
	private final BrandMapper mapper = Mappers.getMapper(BrandMapper.class);

	@Test
	@DisplayName("Check that DataBrandDto has been set correct.")
	void testBrandEntitiesToDataBrandDto() {
		List<BrandEntity> brands = new ArrayList<BrandEntity>();
		for(String name : COMMUNNAME) {
			BrandEntity brand = new BrandEntity();
			brand.setCommunName(name);
			brands.add(brand);
		}
		
		List<DataBrandDto> actualsDTO =  mapper.brandEntityToDataBrandDto(brands);
		
		for(DataBrandDto dto : actualsDTO) {
			assertTrue(COMMUNNAME.contains(dto.communName()));
		}
	}
	
	@Test
	@DisplayName("Verify that DataBrandDto was been set correct.")
	void testBrandEntityToDataBrandDto() {
		BrandEntity entity = new BrandEntity();
		fail();
	}

}
