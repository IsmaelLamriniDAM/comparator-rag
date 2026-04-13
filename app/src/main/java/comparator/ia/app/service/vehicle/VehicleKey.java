package comparator.ia.app.service.vehicle;

import java.util.Objects;

public class VehicleKey {
	private String brand;
	private String model;
	private Integer year;
	
	
	public VehicleKey(String brand, String model, Integer year) {
		super();
		this.brand = brand;
		this.model = model;
		this.year = year;
	}
	
	public String getBrand() {
		return brand;
	}
	public String getModel() {
		return model;
	}
	public Integer getYear() {
		return year;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof VehicleKey)) return false;
		
		VehicleKey key = (VehicleKey) obj;
		
		return brand.equals(key.brand) &&
			   model.equals(key.model) &&
			   year.equals(key.year);
		
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(brand, model, year);
	}
	
}
