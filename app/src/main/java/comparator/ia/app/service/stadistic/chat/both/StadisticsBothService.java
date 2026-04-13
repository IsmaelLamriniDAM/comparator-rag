package comparator.ia.app.service.stadistic.chat.both;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import comparator.ia.app.dtos.chain.HorsePower;
import comparator.ia.app.dtos.chain.Kilometers;
import comparator.ia.app.dtos.chain.price.PriceRange;
import comparator.ia.app.dtos.stadistics.both.SaleProfitDTO;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.repository.VehicleRepository;
import comparator.ia.app.repository.VehicleStadisticsRepository;
import comparator.ia.app.service.manager.objUseInManager.Price;
import comparator.ia.app.util.strategy.stadistics.BothOperationStadisticsStrategy;

@Service
public class StadisticsBothService implements StadisticsBothApi {
	public static final int CARS_MAX_PUBLISH_DATE = 90;
	public static final double MAX_PERCENTAGE = 100.0;
	public static final double MIN_PERCENTAGE = 0;
	private static final double PERCENTAGE = 0.2;

	private final VehicleStadisticsRepository stadisticsRepo;
	private final VehicleRepository vehicleRepo;
	
	public StadisticsBothService(VehicleStadisticsRepository repo, VehicleRepository vehicleRepo) {
		this.stadisticsRepo = repo;
		this.vehicleRepo = vehicleRepo;
	}

	@Override
	public Double calculateMinPriceBuy(SimilarCarOperationDTO vehicle) {
		modifiersValues(vehicle);
		return stadisticsRepo.getMinPriceBuy(vehicle.getModel(), vehicle.getBrand(), vehicle.getHp().min(), vehicle.getHp().max(),  vehicle.getKm().min(), vehicle.getKm().max(),
				vehicle.getYear(), vehicle.getFuelType().name());
	}

	@Override
	public Integer calculateCountCarsSell(SimilarCarOperationDTO vehicle) {
		modifiersValues(vehicle);
		return stadisticsRepo.getCountVehiclesSell(vehicle.getModel(), vehicle.getBrand(), vehicle.getHp().min(), vehicle.getHp().max(), vehicle.getKm().min(), vehicle.getKm().max(),
				vehicle.getYear(), vehicle.getFuelType(), vehicle.getPrice().getRangeSell().min(), vehicle.getPrice().getRangeSell().max());
	}

	@Override
	public Double calculateProfabilitySell(SimilarCarOperationDTO vehicle) {
		List<VehicleEntity> entities = vehicleRepo.getVechilesForThisParameters(vehicle.getModel(), vehicle.getBrand(), vehicle.getHp().min(), vehicle.getHp().max(), vehicle.getKm().min(), vehicle.getKm().max(),
				vehicle.getYear(), vehicle.getFuelType(), LocalDateTime.now().minusDays(CARS_MAX_PUBLISH_DATE));
		  long soldCars = entities.stream()
		            .filter(v -> v.getRemovalDate() != null)
		            .count();

		    if (soldCars == 0L) {
		        return MIN_PERCENTAGE;
		    }

		    long evaluableCars = entities.stream()
		            .filter(v ->
		                    ChronoUnit.DAYS.between(v.getPublishedDate(), LocalDateTime.now()) >= 20
		                    || v.getRemovalDate() != null
		            )
		            .count();

		    if (evaluableCars == 0L) {
		        return MIN_PERCENTAGE;
		    }

		    double percentage = BigDecimal.valueOf((soldCars * 100.0) / evaluableCars)
		            .setScale(2, RoundingMode.HALF_UP)
		            .doubleValue();

		    if (percentage > MAX_PERCENTAGE) {
		        return MAX_PERCENTAGE;
		    }

		    return Math.max(percentage, MIN_PERCENTAGE);
	}

	@Override
	public SaleProfitDTO calculateSaleProfit(Double priceBuy, Double priceSell) {
		SaleProfitDTO dto = new SaleProfitDTO();
		
			Double diference = priceSell - priceBuy;
			dto.setPrice(diference); 
			Double percentage = (diference / priceBuy) * 100;
			if (percentage > MAX_PERCENTAGE) {
				dto.setPercentage(MAX_PERCENTAGE);
			} else {
				dto.setPercentage(BigDecimal.valueOf(Math.max(percentage, MIN_PERCENTAGE)).setScale(2, RoundingMode.HALF_UP).doubleValue());
			}
		
		return dto;
	}

	@Override
	public Double calculateTimeAverageSell(SimilarCarOperationDTO vehicle) {
		modifiersValues(vehicle);
		Double num = stadisticsRepo.getAverageTimeSell(vehicle.getModel(), vehicle.getBrand(), vehicle.getHp().min(), vehicle.getHp().max(), vehicle.getKm().min(), vehicle.getKm().max(),
				vehicle.getYear(), vehicle.getFuelType().name(), vehicle.getPrice().getRangeSell().min(),   vehicle.getPrice().getRangeSell().max(), LocalDateTime.now().minusDays(CARS_MAX_PUBLISH_DATE));
		if(num == null ) {return null;}
		return BigDecimal.valueOf(num).setScale(2,RoundingMode.HALF_UP).doubleValue();
	}
	
	private void modifiersValues(SimilarCarOperationDTO vehicle) {
		Range rangeHoursePower = null;
		Range rangeKm = null;
		
		if(vehicle.getHp().max() != null && vehicle.getHp().min() != null) {
			rangeHoursePower = getNewValueField((double)vehicle.getHp().max(), (double)vehicle.getHp().min());
		} 
		if(vehicle.getKm().max() != null && vehicle.getKm().min() != null) {
			rangeKm = getNewValueField((double)vehicle.getKm().max(), (double)vehicle.getKm().min());
		}
		
		HorsePower power = (vehicle.getHp().max() == null && vehicle.getHp().min() == null) ? vehicle.getHp() : new HorsePower((int)rangeHoursePower.max, (int)rangeHoursePower.min);
		Kilometers km = (vehicle.getKm().max() == null && vehicle.getKm().min() == null) ? vehicle.getKm() : new Kilometers((int)rangeKm.max, (int)rangeKm.min);
		vehicle.setKm(km);
		vehicle.setHp(power);
	}
	
	private Range getNewValueField(Double max, Double min) {
		if(Double.compare(max, min) == 0)  {
			return new Range(
					(max + (max * PERCENTAGE)),
					(min - (min * PERCENTAGE))
			);
		}
		
		return new Range(Math.max(max, min), Math.min(max, min));
	}
	
	private class Range {
		private double max;
		private double min;
		Range(double max, double min){
			this.max = max;
			this.min = min;
		}
	}
	
}
