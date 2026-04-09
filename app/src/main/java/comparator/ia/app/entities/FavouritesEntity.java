package comparator.ia.app.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import comparator.ia.app.enums.FuelType;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.enums.Recommendation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name="favourites_entity")
public class FavouritesEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="model", length=150, nullable=true)
	private String model;
	@Column(name="brand", length=150, nullable=true)
	private String brand;
	
	@Column(name="price_min_sell", nullable=true)
	private Double priceMinSell;
	
	@Column(name="price_max_sell", nullable=true)
	private Double priceMaxSell;
	
	@Column(name="price_min_buy", nullable=true)
	private Double priceMinBuy;
	
	@Column(name="price_max_buy", nullable=true)
	private Double priceMaxBuy;
	@Column(name="year", nullable=true)
	private Integer year;

	@Enumerated(EnumType.STRING)
	@Column(name="fuel_type", length=150, nullable=false)
	private FuelType fuelType;
	
	@Column(name="hp_min", nullable=true)
	private Integer minHp;
	
	@Column(name="hp_max", nullable=true)
	private Integer maxHp;
	
	@Column(name="max_kilometers", nullable=true)
	private Integer maxKilometers;
	
	@Column(name="min_kilometers", nullable=true)
	private Integer minKilometers;
	
	@Enumerated(EnumType.STRING)
	@Column(name="operation", length=150, nullable=false)
	private Operation operation;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserEntity user;
	
	@CreationTimestamp
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Enumerated(EnumType.STRING)
	@Column(name="recommendation", length=150, nullable=false)
	private Recommendation recommendation;

	public Recommendation getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(Recommendation recommendation) {
		this.recommendation = recommendation;
	}

	public Double getPriceMinSell() {
		return priceMinSell;
	}

	public void setPriceMinSell(Double priceMinSell) {
		this.priceMinSell = priceMinSell;
	}

	public Double getPriceMaxSell() {
		return priceMaxSell;
	}

	public void setPriceMaxSell(Double priceMaxSell) {
		this.priceMaxSell = priceMaxSell;
	}

	public Double getPriceMinBuy() {
		return priceMinBuy;
	}

	public void setPriceMinBuy(Double priceMinBuy) {
		this.priceMinBuy = priceMinBuy;
	}

	public Double getPriceMaxBuy() {
		return priceMaxBuy;
	}

	public void setPriceMaxBuy(Double priceMaxBuy) {
		this.priceMaxBuy = priceMaxBuy;
	}

	public Integer getMinHp() {
		return minHp;
	}

	public void setMinHp(Integer minHp) {
		this.minHp = minHp;
	}

	public Integer getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(Integer maxHp) {
		this.maxHp = maxHp;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public Long getId() {
		return id;
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


	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
