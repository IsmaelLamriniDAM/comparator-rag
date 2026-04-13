package comparator.ia.app.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import comparator.ia.app.enums.FuelType;
import comparator.ia.app.enums.WebName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Vehicles")
public class VehicleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="model", length=150, nullable=false)
	private String model;
	@Column(name="brand", length=150, nullable=false)
	private String brand;
	@Column(name="price", nullable=false)
	private Double price;
	@Column(name="vehicle_year", nullable=false)
	private Integer year;
	@Column(name="kilometers", nullable=false)
	private Integer kilometers;
	@Column(name="horsepower", nullable=true)
	private Double horsePower;
	@Enumerated(EnumType.STRING)
	@Column(name="fuel_type", length=150, nullable=true)
	private FuelType fuelType;
	@Enumerated(EnumType.STRING)
	@Column(name="web_name", length=100, nullable=true)
	private WebName webName;
	@Column(name="web_vehicle_url", length=1000, nullable=true)
	private String webVehicleUrl;
	@Column(name="web_vehicle_img", length=1000, nullable=true)
	private String webVehicleImg;
	@Column(name="web_vehicle_id", length=500, nullable=true)
	private String webVehicleId;
	@Column(name="published_date", nullable=true)
	private LocalDateTime publishedDate;
	@Column(name="removal_date", nullable=true)
	private LocalDateTime removalDate;
	@CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	@UpdateTimestamp
    @Column(name = "updated_at", nullable = false, updatable = false)
	private LocalDateTime updatedAt;
    
    
	public Long getId() {
		return id;
	}
	public String getWebVehicleImg() {
		return webVehicleImg;
	}
	public void setWebVehicleImg(String webVehicleImg) {
		this.webVehicleImg = webVehicleImg;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
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
	public WebName getWebName() {
		return webName;
	}
	public void setWebName(WebName webName) {
		this.webName = webName;
	}
	public String getWebVehicleUrl() {
		return webVehicleUrl;
	}
	public void setWebVehicleUrl(String webVehicleUrl) {
		this.webVehicleUrl = webVehicleUrl;
	}
	public String getWebVehicleId() {
		return webVehicleId;
	}
	public void setWebVehicleId(String webVehicleId) {
		this.webVehicleId = webVehicleId;
	}
	public LocalDateTime getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(LocalDateTime publishedDate) {
		this.publishedDate = publishedDate;
	}
	public LocalDateTime getRemovalDate() {
		return removalDate;
	}
	public void setRemovalDate(LocalDateTime removalDate) {
		this.removalDate = removalDate;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(obj.getClass() != this.getClass()) return false;
		VehicleEntity other = (VehicleEntity) obj;
		return this.getWebVehicleId().equals(other.getWebVehicleId());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getWebVehicleId());
	}
	@Override
	public String toString() {
		return "VehicleEntity [model=" + model + ", brand=" + brand + ", price=" + price + ", year=" + year
				+ ", kilometers=" + kilometers + ", horsePower=" + horsePower + ", fuelType=" + fuelType
				+ ", webVehicleUrl=" + webVehicleUrl + ", removaldate = "+  removalDate + "]";
	}


}
