package comparator.ia.app.service.stadistic.chat.both;

import comparator.ia.app.dtos.chain.price.PriceRange;
import comparator.ia.app.dtos.stadistics.both.SaleProfitDTO;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;

public interface StadisticsBothApi {
	Double calculateMinPriceBuy(SimilarCarOperationDTO vehicle);
	Integer calculateCountCarsSell(SimilarCarOperationDTO vehicle);
	Double calculateProfabilitySell(SimilarCarOperationDTO vehicle);
	SaleProfitDTO calculateSaleProfit(Double halfRangeBuy , Double priceSell);
	Double calculateTimeAverageSell(SimilarCarOperationDTO vehicle);
}
