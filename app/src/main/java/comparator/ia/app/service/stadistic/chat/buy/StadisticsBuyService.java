package comparator.ia.app.service.stadistic.chat.buy;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import comparator.ia.app.dtos.chain.HorsePower;
import comparator.ia.app.dtos.chain.Kilometers;
import comparator.ia.app.dtos.chain.price.PriceRange;
import comparator.ia.app.dtos.stadistics.buy.PriceDifferenceAboveMarketDTO;
import comparator.ia.app.dtos.stadistics.buy.PriceIncreaseNeededToReachMarketDTO;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.enums.FuelType;
import comparator.ia.app.repository.VehicleStadisticsRepository;
import comparator.ia.app.service.manager.objUseInManager.Price;

@Service
public class StadisticsBuyService implements StadisticsBuyApi{
	
	private static final double PERCENTAGE = 0.2;
	private static final Logger logger = LoggerFactory.getLogger(StadisticsBuyService.class);
	private static final int NUM_CHANGE_TO_PERCENTAGE = 100;
	private VehicleStadisticsRepository repo;
	public StadisticsBuyService(VehicleStadisticsRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public Integer getCarsSellForThisPrice(SimilarCarOperationDTO vehicle) {
		modifiersValues(vehicle);
		logger.info("calculatin CarsSellForThisPrice()");
		return repo.getCountVehiclesSell(vehicle.getModel(), vehicle.getBrand(), vehicle.getHp().min(),  vehicle.getHp().max(), vehicle.getKm().min(),  vehicle.getKm().max(),
				vehicle.getYear(), vehicle.getFuelType(), vehicle.getPrice().getRangeBuy().min(),   vehicle.getPrice().getRangeBuy().max());
	}
	@Override
	public PriceIncreaseNeededToReachMarketDTO getPercentageToPain(Double price, Double mode) {
		logger.info("calculating PercentageToPain() price: " + price + " < " + "mode: " + mode);
		PriceIncreaseNeededToReachMarketDTO dto = new PriceIncreaseNeededToReachMarketDTO();
		Double toPay = mode - price;
		Double percentageToPay = (toPay / mode) * NUM_CHANGE_TO_PERCENTAGE;
		Double roundingToTwoDecimals = BigDecimal.valueOf(percentageToPay).setScale(2, RoundingMode.HALF_UP).doubleValue();
		dto.setMoney(toPay);
		dto.setPercentage(roundingToTwoDecimals);
		logger.info("porcentaje a pagar si el precio es menor que la moda --> " + dto.toString());
		return dto;
	}
	@Override
	public PriceDifferenceAboveMarketDTO getPercentageToSave(Double price, Double mode) {
		logger.info("calculating PercentageToSave() price: " + price + " > " + "mode: " + mode);
		PriceDifferenceAboveMarketDTO dto = new PriceDifferenceAboveMarketDTO();
		Double moneySave = price - mode;
		Double percentageSave = (moneySave / price) * NUM_CHANGE_TO_PERCENTAGE;
		Double roundingToTwoDecimals = BigDecimal.valueOf(percentageSave).setScale(2, RoundingMode.HALF_UP).doubleValue();
		dto.setMoney(moneySave);
		dto.setPercentage(roundingToTwoDecimals);
		
		logger.info("porcentaje a ahorrar si el precio es mayor que la moda --> {}", dto.toString());
		return dto;
	}
	@Override
	public Double getMaxPrice(SimilarCarOperationDTO vehicle) {
		logger.info("calculating MaxPrice()");
		
		modifiersValues(vehicle);
		
		Double max = repo.getMaxPriceBuy(vehicle.getModel(), vehicle.getBrand(), vehicle.getHp().min(),  vehicle.getHp().max(), vehicle.getKm().min(),  vehicle.getKm().max(),
				vehicle.getYear(), vehicle.getFuelType().name());
		
		logger.info("max: {}", max);
		return max;
	}

	@Override
	public Double getMinPrice(SimilarCarOperationDTO vehicle) {
		logger.info("calculating MinPrice()");
		
		modifiersValues(vehicle);
		
		Double min = repo.getMinPriceBuy(vehicle.getModel(), vehicle.getBrand(), vehicle.getHp().min(), vehicle.getHp().max(), vehicle.getKm().min(),  vehicle.getKm().max(),
				vehicle.getYear(), vehicle.getFuelType().name());
		logger.info("min: {}" , min);
		return min;
	}

	@Override
	public Double getModePrice(SimilarCarOperationDTO vehicle) {
		modifiersValues(vehicle);
		logger.info("calculating ModePrice()");
		Double mode = repo.getModePriceBuy(vehicle.getModel(), vehicle.getBrand(), vehicle.getHp().min(), vehicle.getHp().max(), vehicle.getKm().min(),  vehicle.getKm().max(),
				vehicle.getYear(), vehicle.getFuelType().name());
		logger.info("mode: {}" , mode);
		return mode;
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
