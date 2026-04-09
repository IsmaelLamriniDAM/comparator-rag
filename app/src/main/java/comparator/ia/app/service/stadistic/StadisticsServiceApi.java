package comparator.ia.app.service.stadistic;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import comparator.ia.app.dtos.logic.CarCaractersiticsDto;
import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;


public interface StadisticsServiceApi {
	
	StadisticsVehicleChatDto getStatistics (SimilarCarOperationDTO similarCar);

}
