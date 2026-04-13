package comparator.ia.app.service.vehicle;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import comparator.ia.app.dtos.chat.CarSendRagDTO;
import comparator.ia.app.dtos.logic.CarCaractersiticsDto;
import comparator.ia.app.dtos.logic.VehicleDto;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.enums.WebName;

public interface VehicleService {
	
	List<VehicleDto> findAllVehiclesMatchesWith(CarCaractersiticsDto vehicle);
	List<VehicleEntity> save(Collection<VehicleEntity> filteredVehicles);
	void removeDate(LocalDateTime scrapDate, WebName webName);
	List<VehicleDto> getAllCarsForUrls(List<String> urls);
	
	CarSendRagDTO findVehicleMinPrice(SimilarCarOperationDTO vehicle);
	CarSendRagDTO findVehicleMaxPrice(SimilarCarOperationDTO vehicle);
	Long getCountVehiclesCompared(SimilarCarOperationDTO vehicle);
	
	List<VehicleEntity> findVehiclesCompared(SimilarCarOperationDTO vehicle);
	
	List<VehicleEntity> findVehiclesComparedSell(SimilarCarOperationDTO vehicle);
	
	List<VehicleEntity> findVehiclesComparedBoth(SimilarCarOperationDTO vehicle);
	
	boolean existsVehiclesInDB();
}
