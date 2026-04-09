package comparator.ia.app.util.strategy.stadistics;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import comparator.ia.app.dtos.chain.price.PriceRange;
import comparator.ia.app.dtos.logic.VehicleDto;
import comparator.ia.app.dtos.stadistics.BothOperationStadisticsDTO;
import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;
import comparator.ia.app.dtos.stadistics.both.SaleProfitDTO;
import comparator.ia.app.dtos.stadistics.sendView.SendCarView;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.enums.Recommendation;
import comparator.ia.app.repository.VehicleRepository;
import comparator.ia.app.repository.VehicleStadisticsRepository;
import comparator.ia.app.service.stadistic.chat.BaseStadisticsStrategy;
import comparator.ia.app.service.stadistic.chat.both.StadisticsBothApi;
import comparator.ia.app.service.vehicle.VehicleService;

@Service
public class BothOperationStadisticsStrategy extends BaseStadisticsStrategy {
	
	private static final Logger logger = LoggerFactory.getLogger(BothOperationStadisticsStrategy.class);
	
	private final StadisticsBothApi service;
	
	private final VehicleService vehicleService;

	public BothOperationStadisticsStrategy(StadisticsBothApi service, VehicleService vehicleService) {
		this.service = service;
		this.vehicleService = vehicleService;
	}

	@Override
	public StadisticsVehicleChatDto calculate(StadisticsInput input) {
		logger.info("calculating...");
		StadisticsVehicleChatDto stadisticsDto = new StadisticsVehicleChatDto();
		BothOperationStadisticsDTO bothStatistics = new BothOperationStadisticsDTO();
		SimilarCarOperationDTO similarCarDTO = input.getSimilarVehicle();
		
		bothStatistics.setMarketMinBuyPrice(service.calculateMinPriceBuy(similarCarDTO));
		Double porcentage = service.calculateProfabilitySell(similarCarDTO);
		bothStatistics.setSellSuccessProbability(porcentage);
		Double daysAverage = service.calculateTimeAverageSell(similarCarDTO);
		bothStatistics.setAverageDaysToSell(daysAverage);
		
		if(similarCarDTO.getPrice() != null && similarCarDTO.getPrice().getRangeBuy() != null && similarCarDTO.getPrice().getRangeSell() != null) {
			Double priceBuyUser = halfRangePrice(input.getSimilarVehicle().getPrice().getRangeBuy());
			Double priceSellUser = halfRangePrice(input.getSimilarVehicle().getPrice().getRangeSell());
			
			bothStatistics.setMarketSellComparableVehicleCount(service.calculateCountCarsSell(similarCarDTO));
			SaleProfitDTO saleProfit =  service.calculateSaleProfit(priceBuyUser, priceSellUser);
			bothStatistics.setEstimatedSaleProfit(saleProfit);
			
			if(saleProfit.getPercentage() < 0.0) {
				bothStatistics.setRecommendation(Recommendation.NOT_RECOMMENDED);
			} else if(saleProfit.getPercentage() > 0.0){
				bothStatistics.setRecommendation(Recommendation.RECOMMENDED);
			} else {
				bothStatistics.setRecommendation(Recommendation.CAUTION);
			}
		}
		
		List<VehicleDto> listDto = mapToVehicleDto(vehicleService.findVehiclesComparedSell(similarCarDTO));
		bothStatistics.setListVehiclesCompared(listDto);
		bothStatistics.setCountVehiclesHasBeenCompared(listDto.size());
		bothStatistics.setVehicleFormSent(new SendCarView(similarCarDTO.getBrand(), similarCarDTO.getModel(), similarCarDTO.getYear(), similarCarDTO.getHp(), similarCarDTO.getKm(), similarCarDTO.getPrice(), similarCarDTO.getFuelType()));
		stadisticsDto.setBothStadisticsDTO(bothStatistics);
		return stadisticsDto;
	}

	@Override
	public Operation getOperation() {
		return Operation.BOTH;
	}

}
