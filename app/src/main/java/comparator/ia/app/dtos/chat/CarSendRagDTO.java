package comparator.ia.app.dtos.chat;

import comparator.ia.app.enums.FuelType;

public record CarSendRagDTO(String brand, String model, Double price, Integer kilometers, Double horsePower,
		FuelType fuelType, String webVehicleUrl, String publishedDate, String removalDate) {

}
