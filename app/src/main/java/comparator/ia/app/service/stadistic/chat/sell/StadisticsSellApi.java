package comparator.ia.app.service.stadistic.chat.sell;

import java.util.List;

import comparator.ia.app.dtos.logic.VehicleDto;
import comparator.ia.app.dtos.stadistics.sell.profability.PercentageProbabilityOfSaleDTO;
import comparator.ia.app.dtos.stadistics.sell.time.StadisticTimeSellDTO;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;

public interface StadisticsSellApi {
	
	Double calculatePercentageSell(SimilarCarOperationDTO vehicle);
	Double calculateTimeAverageSell(SimilarCarOperationDTO vehicle);
	Double getMaxPrice(SimilarCarOperationDTO vehicle);
	Double getMinPrice(SimilarCarOperationDTO vehicle);
	Double getModePrice(SimilarCarOperationDTO vehicle);
	
	
	
}
