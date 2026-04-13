package comparator.ia.app.util.strategy.stadistics;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import comparator.ia.app.dtos.logic.VehicleDto;
import comparator.ia.app.dtos.stadistics.BuyStadisticsDTO;
import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;
import comparator.ia.app.dtos.stadistics.buy.PriceDifferenceAboveMarketDTO;
import comparator.ia.app.dtos.stadistics.sendView.SendCarView;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.enums.Recommendation;
import comparator.ia.app.service.stadistic.chat.BaseStadisticsStrategy;
import comparator.ia.app.service.stadistic.chat.buy.StadisticsBuyApi;
import comparator.ia.app.service.vehicle.VehicleService;

@Service
public class BuyStadisticsStrategy extends BaseStadisticsStrategy{
	
	private final Logger logger = org.slf4j.LoggerFactory.getLogger(BuyStadisticsStrategy.class);
	
	private final StadisticsBuyApi stadisticsBuyService;
	
	private final VehicleService vehicleService;
	
	public BuyStadisticsStrategy(StadisticsBuyApi stadisticsBuyService, VehicleService vehicleService) {
		this.stadisticsBuyService = stadisticsBuyService;
		this.vehicleService = vehicleService;
	}

	@Override
	public StadisticsVehicleChatDto calculate(StadisticsInput input) {
		logger.info("calculating...");
		StadisticsVehicleChatDto stadisticsDto = new StadisticsVehicleChatDto();
		BuyStadisticsDTO buyDto = new BuyStadisticsDTO();
		SimilarCarOperationDTO jsonDto = input.getSimilarVehicle();
		
		Double mode = stadisticsBuyService.getModePrice(jsonDto);
		
		
		buyDto.setMarketMostFrequentPrice(mode);
		buyDto.setMarketMaxPrice(stadisticsBuyService.getMaxPrice(jsonDto));
		buyDto.setMarketMinPrice(stadisticsBuyService.getMinPrice(jsonDto));
		
		if(jsonDto.getPrice() != null && jsonDto.getPrice().getRangeBuy() != null 
				&& (jsonDto.getPrice().getRangeBuy().max() != null && jsonDto.getPrice().getRangeBuy().min() != null) && mode != null) {
			Double priceBuyUser = halfRangePrice(jsonDto.getPrice().getRangeBuy());
			
			buyDto.setMarketVehicleCountAtUserBuyPrice(stadisticsBuyService.getCarsSellForThisPrice(jsonDto));
			if(priceBuyUser < mode) {
				buyDto.setPriceIncreaseNeededToReachMarket(stadisticsBuyService.getPercentageToPain(priceBuyUser, mode));
				buyDto.setRecommendation(Recommendation.NOT_RECOMMENDED);
			} else if(priceBuyUser > mode){
				PriceDifferenceAboveMarketDTO dto = stadisticsBuyService.getPercentageToSave(priceBuyUser, mode);
				buyDto.setPriceDifferenceAboveMarket(dto);
				
				setRecommendation(buyDto, dto);
				
			}
		}
		List<VehicleDto> listDto = mapToVehicleDto(vehicleService.findVehiclesCompared(jsonDto));
		
		
		buyDto.setListVehiclesCompared(listDto);
		buyDto.setCountVehiclesHasBeenCompared(listDto.stream().filter(dto -> !dto.isSell()).count());
		buyDto.setVehicleFormSent(new SendCarView(jsonDto.getBrand(), jsonDto.getModel(), jsonDto.getYear(), jsonDto.getHp(), jsonDto.getKm(), jsonDto.getPrice(), jsonDto.getFuelType()));
		
		stadisticsDto.setBuyStadisticsDTO(buyDto);
		return stadisticsDto;
	}

	private void setRecommendation(BuyStadisticsDTO buyDto, PriceDifferenceAboveMarketDTO dto) {
		if(dto.getPercentage() >= PORCENTAGE_RECOMMENDATION) {
			buyDto.setRecommendation(Recommendation.RECOMMENDED);
		} else {
			buyDto.setRecommendation(Recommendation.CAUTION);
		}
		
	}

	@Override
	public Operation getOperation() {
		return Operation.BUY;
	}
	
	
	
	

}
