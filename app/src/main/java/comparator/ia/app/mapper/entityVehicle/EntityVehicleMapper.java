package comparator.ia.app.mapper.entityVehicle;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import comparator.ia.app.dtos.chat.CarSendRagDTO;
import comparator.ia.app.entities.VehicleEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {MapperFieldVehicleEntity.class})
public interface EntityVehicleMapper {
	
	@Mapping(source = "publishedDate", target = "publishedDate" , qualifiedByName = "publishDateToString")
	@Mapping(source = "removalDate", target = "removalDate", qualifiedByName = "removalDateToString")
	CarSendRagDTO vehicleEntityToCarSendRagDTO(VehicleEntity vehicleEntity);
	
}
