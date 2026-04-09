package comparator.ia.app.dtos.favoritesSearch;


import comparator.ia.app.dtos.chain.HorsePower;
import comparator.ia.app.dtos.chain.Kilometers;
import comparator.ia.app.enums.FuelType;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.enums.Recommendation;
import comparator.ia.app.service.manager.objUseInManager.Price;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detailed information of a specific history")
public class FavouritesReturnDto {

	@Schema(description = "Unique identifier of the vehicle", example = "1025", nullable = true)
    private long id;
    
	@Schema(description = "Vehicle model.", example = "serie 3", nullable = true)
    private String model;

	@Schema(description = "Vehicle brand.", example = "bmw", nullable = true)
    private String brand;

	@Schema(description = "Vehicle price.", nullable = true)
    private Price price;

	@Schema(description = "Vehicle year.", example = "2022", nullable = true)
    private Integer year;

	@Schema(description = "Fuel type of the vehicle", example = "GASOLINE", nullable = true)
    private FuelType fuelType;
	
	@Schema(description = "Operation of the vehicle", example = "BUY", nullable = false)
	private Operation operation;
	
	@Schema(description = "Operation of the search", example = "RECOMMENDED", nullable = false)
	private Recommendation recommendation;
    
	@Schema(description = "Range km", nullable = true)
    private Kilometers km;
    
	@Schema(description = "Range hp", nullable = true)
	private HorsePower hp;
	
	
    
	public Recommendation getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(Recommendation recommendation) {
		this.recommendation = recommendation;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
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


	public Price getPrice() {
		return price;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}

