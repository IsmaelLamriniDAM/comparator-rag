package comparator.ia.app.dtos.stadistics;

import java.util.List;

import comparator.ia.app.dtos.logic.VehicleDto;
import comparator.ia.app.dtos.stadistics.buy.PriceDifferenceAboveMarketDTO;
import comparator.ia.app.dtos.stadistics.buy.PriceIncreaseNeededToReachMarketDTO;
import comparator.ia.app.dtos.stadistics.sendView.SendCarView;
import comparator.ia.app.enums.Recommendation;

public class BuyStadisticsDTO {
	private Double marketMaxPrice;
	private Double marketMinPrice;
	private Double marketMostFrequentPrice;
	private Integer marketVehicleCountAtUserBuyPrice;
	private PriceIncreaseNeededToReachMarketDTO priceIncreaseNeededToReachMarket;
	private PriceDifferenceAboveMarketDTO priceDifferenceAboveMarket;
	private SendCarView vehicleFormSent;
	private List<VehicleDto> listVehiclesCompared;
	private Long countVehiclesHasBeenCompared;
	private Recommendation recommendation;
	
	
	public Recommendation getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(Recommendation recommendation) {
		this.recommendation = recommendation;
	}
	public Long getCountVehiclesHasBeenCompared() {
		return countVehiclesHasBeenCompared;
	}
	public void setCountVehiclesHasBeenCompared(Long countVehiclesHasBeenCompared) {
		this.countVehiclesHasBeenCompared = countVehiclesHasBeenCompared;
	}
	public List<VehicleDto> getListVehiclesCompared() {
		return listVehiclesCompared;
	}
	public void setListVehiclesCompared(List<VehicleDto> listVehiclesCompared) {
		this.listVehiclesCompared = listVehiclesCompared;
	}
	public SendCarView getVehicleFormSent() {
		return vehicleFormSent;
	}
	public void setVehicleFormSent(SendCarView vehicleFormSent) {
		this.vehicleFormSent = vehicleFormSent;
	}
	public Double getMarketMaxPrice() {
		return marketMaxPrice;
	}
	public void setMarketMaxPrice(Double maxPrice) {
		this.marketMaxPrice = maxPrice;
	}
	public Double getMarketMinPrice() {
		return marketMinPrice;
	}
	public void setMarketMinPrice(Double minPrice) {
		this.marketMinPrice = minPrice;
	}
	public Double getMarketMostFrequentPrice() {
		return marketMostFrequentPrice;
	}
	public void setMarketMostFrequentPrice(Double modePrice) {
		this.marketMostFrequentPrice = modePrice;
	}
	public Integer getMarketVehicleCountAtUserBuyPrice() {
		return marketVehicleCountAtUserBuyPrice;
	}
	public void setMarketVehicleCountAtUserBuyPrice(Integer countVehicleBuyByUserPrice) {
		this.marketVehicleCountAtUserBuyPrice = countVehicleBuyByUserPrice;
	}
	public PriceIncreaseNeededToReachMarketDTO getPriceIncreaseNeededToReachMarket() {
		return priceIncreaseNeededToReachMarket;
	}
	public void setPriceIncreaseNeededToReachMarket(PriceIncreaseNeededToReachMarketDTO percentagePay) {
		this.priceIncreaseNeededToReachMarket = percentagePay;
	}
	public PriceDifferenceAboveMarketDTO getPriceDifferenceAboveMarket() {
		return priceDifferenceAboveMarket;
	}
	public void setPriceDifferenceAboveMarket(PriceDifferenceAboveMarketDTO percentageSave) {
		this.priceDifferenceAboveMarket = percentageSave;
	}
	
}	
