package comparator.ia.app.util.strategy.stadistics;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.util.strategy.stadistics.api.StadistisStrategy;

@Component
public class CalculateStadistic {
	
	private static final Logger logger = LoggerFactory.getLogger(CalculateStadistic.class);
	private final Map<Operation, StadistisStrategy> strategies;
	
	public CalculateStadistic(List<StadistisStrategy> listStrategies) {
		super();
		this.strategies = listStrategies.stream().collect(Collectors.toMap(StadistisStrategy::getOperation,s -> s));
	}

	public StadisticsVehicleChatDto calculate(StadisticsInput input) {
		StadistisStrategy strategy = strategies.get(input.getSimilarVehicle().getOperation());
		
		if(strategy == null) {
			throw new IllegalStateException("DEVELOPER ERROR: The strategy for operation '" 
			        + input.getSimilarVehicle().getOperation() 
			        + "' is not registered in the Strategy Map.");
		}
		
		logger.info("CALCULATING THIS STRATEGY -> {}", strategy.getClass().getName());
		return strategy.calculate(input);
	}
}
