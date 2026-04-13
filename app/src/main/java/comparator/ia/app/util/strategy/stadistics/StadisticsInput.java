package comparator.ia.app.util.strategy.stadistics;

import comparator.ia.app.dtos.logic.VehicleDto;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.enums.Operation;

public class StadisticsInput {
	
	private SimilarCarOperationDTO similarVehicle;
	public StadisticsInput(SimilarCarOperationDTO similarVehicle) {
		super();
		this.similarVehicle = similarVehicle;
	}
	public SimilarCarOperationDTO getSimilarVehicle() {
		return similarVehicle;
	}
	public void setSimilarVehicle(SimilarCarOperationDTO similarVehicle) {
		this.similarVehicle = similarVehicle;
	}
	
}
