package comparator.ia.app.util.strategy.stadistics;

import java.util.List;

import org.springframework.stereotype.Service;

import comparator.ia.app.dtos.logic.VehicleDto;
import comparator.ia.app.dtos.stadistics.SellStadisticsDTO;
import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;
import comparator.ia.app.dtos.stadistics.sendView.SendCarView;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.enums.Recommendation;
import comparator.ia.app.repository.VehicleRepository;
import comparator.ia.app.repository.VehicleStadisticsRepository;
import comparator.ia.app.service.stadistic.chat.BaseStadisticsStrategy;
import comparator.ia.app.service.stadistic.chat.sell.StadisticsSellApi;
import comparator.ia.app.service.vehicle.VehicleService;


@Service
public class SellStadisticsStrategy extends BaseStadisticsStrategy {
	
	private final StadisticsSellApi sellService;
	
	private final VehicleService vehicleService;

	public SellStadisticsStrategy(StadisticsSellApi sellService, VehicleService vehicleService) {
		this.sellService = sellService;
		this.vehicleService = vehicleService;
	}

	@Override
	public StadisticsVehicleChatDto calculate(StadisticsInput input) {
		StadisticsVehicleChatDto stadisticsDto = new StadisticsVehicleChatDto();
		SellStadisticsDTO sellDto = new SellStadisticsDTO();
		SimilarCarOperationDTO vehicle = input.getSimilarVehicle();
		
		Double mode = sellService.getModePrice(vehicle);
		sellDto.setMarketMaxSellPrice(sellService.getMaxPrice(vehicle));
		sellDto.setMarketMinSellPrice(sellService.getMinPrice(vehicle));
		sellDto.setMarketMostFrequentSellPrice(mode);
		Double porcentage = sellService.calculatePercentageSell(vehicle);
		sellDto.setSellProbability(porcentage);
		Double daysAverage = sellService.calculateTimeAverageSell(vehicle);
		sellDto.setAverageDaysToSell(daysAverage);
		
		List<VehicleDto> listDto = mapToVehicleDto(vehicleService.findVehiclesComparedSell(vehicle));
		sellDto.setListVehiclesCompared(listDto);
		sellDto.setCountVehiclesHasBeenCompared(listDto.size());
		
		if(porcentage <= 49 && (daysAverage == null || daysAverage >= 90)) {
			sellDto.setRecommendation(Recommendation.NOT_RECOMMENDED);
		} else if ( (porcentage >= 50 &&  porcentage < 70) && (daysAverage >= 30 && daysAverage < 90) ) {
			sellDto.setRecommendation(Recommendation.CAUTION);
		} else if(porcentage >= 70 &&  daysAverage < 30) {
			sellDto.setRecommendation(Recommendation.RECOMMENDED);
		}
		
		sellDto.setVehicleFormSent(new SendCarView(vehicle.getBrand(), vehicle.getModel(), vehicle.getYear(), vehicle.getHp(), vehicle.getKm(), vehicle.getPrice(), vehicle.getFuelType()));
		stadisticsDto.setSellStadisticsDTO(sellDto);
		return stadisticsDto;
	}

	@Override
	public Operation getOperation() {
		return Operation.SELL;
	}

}
