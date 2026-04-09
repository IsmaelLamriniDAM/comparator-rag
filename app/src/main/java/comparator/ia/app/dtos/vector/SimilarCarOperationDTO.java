package comparator.ia.app.dtos.vector;

import java.util.List;

import comparator.ia.app.dtos.chain.HorsePower;
import comparator.ia.app.dtos.chain.Kilometers;
import comparator.ia.app.enums.FuelType;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.service.manager.objUseInManager.Price;

public class SimilarCarOperationDTO {
    private String model;
    private String brand;
    private HorsePower hp;
	private Kilometers km;
    private Integer year;
    private FuelType fuelType;
    private Price price;
    private Operation operation;

    
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


	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
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

	public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

	@Override
	public String toString() {
		return "SimilarCarOperationDTO [model=" + model + ", brand=" + brand + ", hp=" + hp + ", km=" + km
				+ ", year=" + year + ", fuelType=" + fuelType + ", price=" + price + ", operation=" + operation + "]";
	}
    
}
