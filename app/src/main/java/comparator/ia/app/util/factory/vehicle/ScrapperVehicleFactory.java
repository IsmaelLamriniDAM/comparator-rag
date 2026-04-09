package comparator.ia.app.util.factory.vehicle;

import java.util.Map;

import org.springframework.stereotype.Component;

import comparator.ia.app.handler.exceptions.DaySelectionScrapperNotExists;
import comparator.ia.app.service.scrap.vehicle.CochesNetScrapperService;
import comparator.ia.app.service.scrap.vehicle.MilAnuncionScrapperService;
import comparator.ia.app.service.scrap.vehicle.OcasionPlusScrapperService;
import comparator.ia.app.util.strategy.vehicle.api.VehicleScrapperStrategy;

@Component
public final class ScrapperVehicleFactory{
	
	 private static final int TUE = 2;
	  private static final int WED = 3;
	  private static final int THU = 4;

	  private final Map<Integer, VehicleScrapperStrategy> strategies;

	  public ScrapperVehicleFactory(MilAnuncionScrapperService mil,
	                                CochesNetScrapperService coches,
	                                OcasionPlusScrapperService ocasion) {
	    this.strategies = Map.of(
	        TUE, mil,
	        WED, coches,
	        THU, ocasion
	    );
	  }

	public VehicleScrapperStrategy get(Integer day) {
		VehicleScrapperStrategy strategy = strategies.get(day);
		if(strategy == null) {
			throw new DaySelectionScrapperNotExists("Strategy Day does not exist");
		}
		return strategy;
	}

}
