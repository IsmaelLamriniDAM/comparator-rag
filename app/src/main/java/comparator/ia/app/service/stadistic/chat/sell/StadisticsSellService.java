package comparator.ia.app.service.stadistic.chat.sell;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import comparator.ia.app.dtos.chain.HorsePower;
import comparator.ia.app.dtos.chain.Kilometers;
import comparator.ia.app.dtos.chain.price.PriceRange;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.repository.VehicleRepository;
import comparator.ia.app.repository.VehicleStadisticsRepository;
import comparator.ia.app.service.manager.objUseInManager.Price;

@Service
public class StadisticsSellService implements StadisticsSellApi{

    public static final int CARS_MAX_PUBLISH_DATE = 90;
    public static final double MAX_PERCENTAGE = 100.0;
    public static final double MIN_PERCENTAGE = 0;
    private static final double PERCENTAGE = 0.2;
    private final VehicleStadisticsRepository stadisticsRepo;
    private final VehicleRepository vehicleRepo;
	
	public StadisticsSellService(VehicleStadisticsRepository stadisticsRepo, VehicleRepository vehicleRepo) {
		this.stadisticsRepo = stadisticsRepo;
        this.vehicleRepo = vehicleRepo;
    }
	

	@Override
	public Double calculatePercentageSell(SimilarCarOperationDTO vehicle) {
		modifiersValues(vehicle);
        List<VehicleEntity> entities = vehicleRepo.getVechilesForThisParameters(vehicle.getModel(), vehicle.getBrand(), vehicle.getHp().min(), vehicle.getHp().max(), vehicle.getKm().min(), vehicle.getKm().max(),
				vehicle.getYear(), vehicle.getFuelType(), LocalDateTime.now().minusDays(CARS_MAX_PUBLISH_DATE));
        
        Long totalSells = entities.stream().filter(v -> v.getRemovalDate() != null).count();
        if(totalSells == 0L) {return MIN_PERCENTAGE;}
        Integer totalCarsOlderTwentyDays = 0;
        for (VehicleEntity entity : entities) {
            if (ChronoUnit.DAYS.between( entity.getPublishedDate(),LocalDateTime.now()) >= 20){
                totalCarsOlderTwentyDays++;
            } else {
                if(entity.getRemovalDate() != null){
                    totalCarsOlderTwentyDays++;
                }
            }
        }
        if (totalCarsOlderTwentyDays == 0) {return MIN_PERCENTAGE;}
        Double operation = BigDecimal.valueOf((totalSells.doubleValue() / totalCarsOlderTwentyDays)*100).setScale(2, RoundingMode.HALF_UP).doubleValue();
        if(operation > MAX_PERCENTAGE){
            return MAX_PERCENTAGE;
        } else if (operation >= MIN_PERCENTAGE){
            return operation;
        }
        return MIN_PERCENTAGE;
	}


	@Override
	public Double calculateTimeAverageSell(SimilarCarOperationDTO vehicle) {
		modifiersValues(vehicle);
		Double num = stadisticsRepo.getAverageTimeSell(vehicle.getModel(), vehicle.getBrand(), vehicle.getHp().min(), vehicle.getHp().max(), vehicle.getKm().min(), vehicle.getKm().max(),
				vehicle.getYear(), vehicle.getFuelType().name(), vehicle.getPrice().getRangeSell().min(),   vehicle.getPrice().getRangeSell().max(), LocalDateTime.now().minusDays(CARS_MAX_PUBLISH_DATE));
		if(num == null ) {return null;}
		return BigDecimal.valueOf(num).setScale(2,RoundingMode.HALF_UP).doubleValue();
	}

    @Override
    public Double getMaxPrice(SimilarCarOperationDTO vehicle) {
    	modifiersValues(vehicle);
        return stadisticsRepo.getMaxPriceSell(vehicle.getModel(), vehicle.getBrand(), vehicle.getHp().min(), vehicle.getHp().max(), vehicle.getKm().min(), vehicle.getKm().max(),
				vehicle.getYear(), vehicle.getFuelType().name(), LocalDateTime.now().minusDays(CARS_MAX_PUBLISH_DATE));
    }

    @Override
    public Double getMinPrice(SimilarCarOperationDTO vehicle) {
    	modifiersValues(vehicle);
        return stadisticsRepo.getMinPriceSell(vehicle.getModel(), vehicle.getBrand(), vehicle.getHp().min(), vehicle.getHp().max(), vehicle.getKm().min(), vehicle.getKm().max(),
				vehicle.getYear(), vehicle.getFuelType().name(), LocalDateTime.now().minusDays(CARS_MAX_PUBLISH_DATE));
    }

    @Override
    public Double getModePrice(SimilarCarOperationDTO vehicle) {
    	modifiersValues(vehicle);
		return stadisticsRepo.getModePriceSell(vehicle.getModel(), vehicle.getBrand(), vehicle.getHp().min(), vehicle.getHp().max(), vehicle.getKm().min(), vehicle.getKm().max(),
				vehicle.getYear(), vehicle.getFuelType().name(), LocalDateTime.now().minusDays(CARS_MAX_PUBLISH_DATE));
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
