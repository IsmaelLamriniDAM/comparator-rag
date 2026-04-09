package comparator.ia.app.util.strategy.vehicle.api;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.ai.chat.model.ChatModel;

import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.enums.FuelType;
import comparator.ia.app.service.alias.brand.BrandAliasService;
import comparator.ia.app.service.vector.VectorService;
import comparator.ia.app.service.vehicle.VehicleKey;

public interface VehicleScrapperStrategy {
	
	static FuelType getFuelType(String fuelType) {
		if (fuelType == null) return FuelType.OTHER;

		return switch (fuelType.toLowerCase()) {
        case "gasolina", "gasoline" -> FuelType.GASOLINE;
        case "diésel", "diesel" -> FuelType.DIESEL;
        case "eléctrico", "electric" -> FuelType.ELECTRIC;
        case "glp", "gas licuado (gpl)", "gas licuado (glp)" -> FuelType.GLP;
        case "gnc", "gas natural (cng)" -> FuelType.GNC;
        case "híbrido", "híbrido enchufable", "hybrid" -> FuelType.HYBRID;
        default -> FuelType.OTHER;
    };
	}

	private static Optional<Double> getMode(List<Double> priceList) {
		if (priceList == null || priceList.isEmpty()) {
	        return Optional.empty();
	    }
		
		Map<Double, Long> frequencyMap = priceList.stream()
		        .collect(Collectors.groupingBy(
		            Function.identity(), 
		            Collectors.counting() 
		        ));
		
		return frequencyMap.entrySet().stream()
		        .max(Map.Entry.comparingByValue()) 
		        .map(Map.Entry::getKey);
	}
	
	static List<VehicleEntity> filter(List<VehicleEntity> vehicles) {
		Map<VehicleKey, List<VehicleEntity>> filteredVehicles = vehicles.stream().collect(Collectors.groupingBy(v -> new VehicleKey(v.getBrand(), v.getModel(), v.getYear())));
		
		Map<VehicleKey, Double> modePrices = filteredVehicles
				.entrySet()
				.stream()
				.collect(Collectors.toMap(Entry::getKey, entry -> {
					List<Double> prices =  entry.getValue().stream().map(VehicleEntity::getPrice).collect(Collectors.toList()); 
					return getMode(prices).get();
				} ));


		return vehicles.stream()
				.filter(v -> {
			        VehicleKey key = new VehicleKey(v.getBrand(), v.getModel(), v.getYear());
			        Double modePrice = modePrices.get(key);
			        if (modePrice == null) return false;
			        Double minAllowed = modePrice * 0.05;
			        Double maxAllowed = modePrice * 1.95;
			        return v.getPrice() >= minAllowed && v.getPrice() <= maxAllowed;
			    })
			    .toList();
	}
	
	default void setChatModel(ChatModel chatmodel) {
		this.setChatModel(chatmodel);
	}
	
	List<VehicleEntity> scrap();
}
