package comparator.ia.app.dtos.logic;


import comparator.ia.app.enums.FuelType;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.tags.BeforeActualYear;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CarCaractersiticsDto {
	
	//FIXME: The user can put a wrong model
	@Schema(description = "Vehicle model.")
	@NotEmpty(message = "El modelo del vehículo no puede estar vacio.")
	private String model;
	
	//FIXME: The user can put a wrong brand
	@Schema(description = "Vehicle brand.")
	@NotEmpty(message = "La marca del vehículo no puede estar vacía.")
	private String brand;
	
	@Schema(description = "Vehicle price.")
	@NotNull(message = "El precio del coche no puede ser null.")
	@Positive(message = "El precio del coche no puede ser negativo.")
	private double price;
	
	@Schema(description = "Vehicle year.")
	@NotNull(message = "El año del coche no puede ser null.")
	@BeforeActualYear()
	private Integer year;
	
	@Schema(description = "Vehicle horsepower.")
	@NotNull(message = "Los caballos de potencia no puede ser null.")
	@DecimalMin(value = "0.01", message = "La pontencia no puede ser menor de 0,01.")
	@DecimalMax(value = "100000.00", message = "La pontencia no puede ser mayor de 100000,00.")
	private Double horsePower;
	

	//FIXME: puede dar error al intentar recibir un fuelType invalido
	private FuelType fuelType;
	
	@Schema(description = "External costs of the user.")
	@NotNull(message = "El coste estra no puede ser null.")

	@DecimalMax(value = "1000000000.00", message="EL coste máximo no puede ser superior a 1000000000,00.")
	@DecimalMin(value = "0.01", message = "EL coste máximo no puede ser inferior a 0,00.")
	private Double extraCost;

	@Schema(description = "Maximum mileage of the car.")
	@NotNull(message = "Los kilometros máximos no pueden ser null.")
	@Max(value = 1_500_000, message = "Los kilometros máximos no pueden ser superior a 1500000.")
	@Min(value = 1, message = "Los kilometros máximos no pueden ser inferior a 1.")
	private Integer maxKilometers;
	
	@Schema(description = "Minimum mileage of the car.")
	@NotNull(message = "Los kilometros máximos no pueden ser null.")
	@Max(value = 1_499_999, message = "Los kilometros máximos no pueden ser superior a 1499999.")
	@Min(value = 0, message = "Los kilometros mínimos no pueden ser inferior a 0.")
	private Integer minKilometers;

	@Schema(description = "Operacion que se quiere analizar")
	@NotNull(message = "")
	private Operation operation;
	
	//-------GETTERS AND SETTERS-------------
	
	public String getModel() {
		return model;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Double getHorsePower() {
		return horsePower;
	}

	public void setHorsePower(Double horsePower) {
		this.horsePower = horsePower;
	}

	public FuelType getFuelType() {
		return fuelType;
	}

	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}

	public Double getExtraCost() {
		return extraCost;
	}

	public void setExtraCost(Double extraCost) {
		this.extraCost = extraCost;
	}

	public Integer getMaxKilometers() {
		return maxKilometers;
	}

	public void setMaxKilometers(Integer maxKilometers) {
		this.maxKilometers = maxKilometers;
	}

	public Integer getMinKilometers() {
		return minKilometers;
	}

	public void setMinKilometers(Integer minKilometers) {
		this.minKilometers = minKilometers;
	}
	
	
}
