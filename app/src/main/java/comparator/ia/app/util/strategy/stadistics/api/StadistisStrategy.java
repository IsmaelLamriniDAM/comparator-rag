package comparator.ia.app.util.strategy.stadistics.api;

import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.util.strategy.stadistics.StadisticsInput;

public interface StadistisStrategy {
	StadisticsVehicleChatDto calculate(StadisticsInput input);
	Operation getOperation();
}
