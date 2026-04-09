package comparator.ia.app.dtos.logic;

import java.time.LocalDateTime;

import comparator.ia.app.enums.FuelType;
import comparator.ia.app.enums.WebName;

public class VehicleDto {
	private String model;
	private String brand;
	private Integer year;
	private Integer kilometers;
	private Double horsePower;
	private FuelType fuelType;
	private WebName webName;
	private String webVehicleUrl;
	private String publishedDate;
	private Double price;
	private String image;
	private String removeData;
	private boolean isSell;
	private boolean isMoreThanNinetyDaysActive;

	public boolean isMoreThanNinetyDaysActive() {
		return isMoreThanNinetyDaysActive;
	}

	public void setMoreThanNinetyDaysActive(boolean isMoreThanNinetyDaysActive) {
		this.isMoreThanNinetyDaysActive = isMoreThanNinetyDaysActive;
	}

	public boolean isSell() {
		return isSell;
	}

	public void setSell(boolean isSell) {
		this.isSell = isSell;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public Integer getKilometers() {
		return kilometers;
	}

	public void setKilometers(Integer kilometers) {
		this.kilometers = kilometers;
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

	public String getWebVehicleUrl() {
		return webVehicleUrl;
	}

	public void setWebVehicleUrl(String webVehicleUrl) {
		this.webVehicleUrl = webVehicleUrl;
	}


	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public WebName getWebName() {
		return webName;
	}

	public void setWebName(WebName webName) {
		this.webName = webName;
	}
	
	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getRemoveData() {
		return removeData;
	}

	public void setRemoveData(String removeData) {
		this.removeData = removeData;
	}

	@Override
	public String toString() {
		return "VehicleDto [model=" + model + ", brand=" + brand + ", year=" + year + ", kilometers=" + kilometers
				+ ", horsePower=" + horsePower + ", fuelType=" + fuelType + ", webVehicleUrl="
				+ webVehicleUrl + ", price=" + price +  "]";
	}
	
	
	
}
