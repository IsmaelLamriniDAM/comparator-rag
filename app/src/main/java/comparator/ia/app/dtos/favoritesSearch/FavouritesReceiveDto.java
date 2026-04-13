package comparator.ia.app.dtos.favoritesSearch;


import comparator.ia.app.dtos.chain.HorsePower;
import comparator.ia.app.dtos.chain.Kilometers;
import comparator.ia.app.enums.FuelType;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.enums.Recommendation;
import comparator.ia.app.service.manager.objUseInManager.Price;
import comparator.ia.app.tags.BeforeActualYear;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class FavouritesReceiveDto {
	
	@Schema(description = "Vehicle model.", example = "scudo")
	private String model;

	@Schema(description = "Vehicle brand.", example = "fiat")
	private String brand;

	@Schema(description = "Vehicle price.", example = "12000")
	@NotNull(message = "El precio del coche no puede ser null.")
	private Price price;

	@Schema(description = "Vehicle year.", example = "2022")
	@NotNull(message = "El año del coche no puede ser null.")
	@BeforeActualYear()
	private Integer year;

	@Schema(description = "Fuel type of the vehicle", example = "GASOLINE")
	private FuelType fuelType;
	
	@Schema(description = "Range km", nullable = true)
    private Kilometers km;
    
	@Schema(description = "Range hp", nullable = true)
	private HorsePower hp;
	
	@Schema(description = "Operation of the vehicle", example = "BUY", nullable = false)
	private Operation operation;
	
	@Schema(description = "Operation of the search", example = "RECOMMENDED", nullable = false)
	private Recommendation recommendation;
	
	
	public Recommendation getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(Recommendation recommendation) {
		this.recommendation = recommendation;
	}

	public Price getPrice() {
		return price;
	}

	public Kilometers getKm() {
		return km;
	}

	public void setKm(Kilometers km) {
		this.km = km;
	}

	public HorsePower getHp() {
		return hp;
	}

	public void setHp(HorsePower hp) {
		this.hp = hp;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}



	public String getBrand() {
		return brand;
	}



	public void setBrand(String brand) {
		this.brand = brand;
	}



	public Integer getYear() {
		return year;
	}



	public void setYear(Integer year) {
		this.year = year;
	}



	public FuelType getFuelType() {
		return fuelType;
	}



	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}

}
