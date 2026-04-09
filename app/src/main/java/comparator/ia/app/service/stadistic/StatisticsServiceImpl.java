package comparator.ia.app.service.stadistic;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import comparator.ia.app.dtos.logic.CarCaractersiticsDto;
import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.service.vehicle.VehicleService;
import comparator.ia.app.util.strategy.stadistics.CalculateStadistic;
import comparator.ia.app.util.strategy.stadistics.StadisticsInput;


@Service
public class StatisticsServiceImpl implements StadisticsServiceApi {

	private final CalculateStadistic calculateOperation;
	
	public StatisticsServiceImpl(CalculateStadistic calculateOperation) {
		this.calculateOperation = calculateOperation;
	}

	@Override
	public StadisticsVehicleChatDto getStatistics(SimilarCarOperationDTO similarCar) {
		StadisticsInput input = new StadisticsInput(similarCar);
		return calculateOperation.calculate(input);
	}
	
}
