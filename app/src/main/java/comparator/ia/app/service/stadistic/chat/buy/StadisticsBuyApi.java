package comparator.ia.app.service.stadistic.chat.buy;

import comparator.ia.app.dtos.chain.price.PriceRange;
import comparator.ia.app.dtos.stadistics.buy.PriceDifferenceAboveMarketDTO;
import comparator.ia.app.dtos.stadistics.buy.PriceIncreaseNeededToReachMarketDTO;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;

public interface StadisticsBuyApi {
	
	Integer getCarsSellForThisPrice(SimilarCarOperationDTO vehicle);
	PriceIncreaseNeededToReachMarketDTO getPercentageToPain(Double price, Double mode);
	PriceDifferenceAboveMarketDTO getPercentageToSave(Double price, Double mode);
	Double getMaxPrice(SimilarCarOperationDTO vehicle);
	Double getMinPrice(SimilarCarOperationDTO vehicle);
	Double getModePrice(SimilarCarOperationDTO vehicle);
}
