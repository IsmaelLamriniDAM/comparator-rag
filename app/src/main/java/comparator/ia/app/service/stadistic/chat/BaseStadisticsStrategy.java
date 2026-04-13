package comparator.ia.app.service.stadistic.chat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comparator.ia.app.dtos.chain.price.PriceRange;
import comparator.ia.app.dtos.logic.VehicleDto;
import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.repository.VehicleRepository;
import comparator.ia.app.repository.VehicleStadisticsRepository;
import comparator.ia.app.service.vehicle.VehicleService;
import comparator.ia.app.util.strategy.stadistics.BuyStadisticsStrategy;
import comparator.ia.app.util.strategy.stadistics.api.StadistisStrategy;

public abstract class BaseStadisticsStrategy implements StadistisStrategy{
	private static final int NUMBER_HALF = 2;
	protected static final Double PORCENTAGE_RECOMMENDATION = 10.0;

	protected BaseStadisticsStrategy() {
		super();
	}
	
	protected Double halfRangePrice(PriceRange range) {
		if(range == null || range.max() == null || range.min() == null) return null;
		return (range.min() + range.max()) / NUMBER_HALF;
	}
	
	protected List<VehicleDto> mapToVehicleDto(List<VehicleEntity> entities){
		System.err.println("LISTA DE COCHES CON LOS QUE SE HA COMPARADO: " + entities);
		return entities.stream().map(e -> {
			VehicleDto dto = new VehicleDto();
			dto.setBrand(e.getBrand());
			dto.setModel(e.getModel());
			dto.setFuelType(e.getFuelType());
			dto.setHorsePower(e.getHorsePower());
			dto.setImage(e.getWebVehicleImg());
			dto.setKilometers(e.getKilometers());
			dto.setPrice(e.getPrice());
			dto.setPublishedDate(e.getPublishedDate().toString());
			dto.setRemoveData(e.getRemovalDate() != null ? e.getRemovalDate().toString() : null);
			dto.setWebName(e.getWebName());
			dto.setYear(e.getYear());
			dto.setWebVehicleUrl(e.getWebVehicleUrl());
			dto.setSell(e.getRemovalDate() != null);
			
			if(e.getRemovalDate() != null) {
				dto.setMoreThanNinetyDaysActive(ChronoUnit.DAYS.between(e.getPublishedDate(),e.getRemovalDate()) >= 90);
			} else {
				dto.setMoreThanNinetyDaysActive(ChronoUnit.DAYS.between(e.getPublishedDate(),LocalDateTime.now()) >= 90);
			}
			
			return dto;
		}).toList();
		
		
	}
	
	
	
}
