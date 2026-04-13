package comparator.ia.app.dtos.stadistics.sendView;

import comparator.ia.app.dtos.chain.HorsePower;
import comparator.ia.app.dtos.chain.Kilometers;
import comparator.ia.app.enums.FuelType;
import comparator.ia.app.service.manager.objUseInManager.Price;

public record SendCarView(
		String brand,
		String model,
		Integer year,
		HorsePower hp,
		Kilometers km,
		Price price,
		FuelType fuelType) {

}
