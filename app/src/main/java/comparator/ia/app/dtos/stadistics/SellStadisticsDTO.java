package comparator.ia.app.dtos.stadistics;

import java.util.List;

import comparator.ia.app.dtos.logic.VehicleDto;
import comparator.ia.app.dtos.stadistics.buy.PriceIncreaseNeededToReachMarketDTO;
import comparator.ia.app.dtos.stadistics.sell.profability.PercentageProbabilityOfSaleDTO;
import comparator.ia.app.dtos.stadistics.sell.time.StadisticTimeSellDTO;
import comparator.ia.app.dtos.stadistics.sendView.SendCarView;
import comparator.ia.app.enums.Recommendation;

public class SellStadisticsDTO {
	
	private Double marketMaxSellPrice;
	private Double marketMinSellPrice;
	private Double marketMostFrequentSellPrice;
	private Double sellProbability;
	private Double averageDaysToSell;
	private SendCarView vehicleFormSent;
	private List<VehicleDto> listVehiclesCompared;
	private Integer countVehiclesHasBeenCompared;
	private Recommendation recommendation;
	
	
	public Recommendation getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(Recommendation recommendation) {
		this.recommendation = recommendation;
	}
	
	
	public Integer getCountVehiclesHasBeenCompared() {
		return countVehiclesHasBeenCompared;
	}
	public void setCountVehiclesHasBeenCompared(Integer countVehiclesHasBeenCompared) {
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
	public Double getMarketMaxSellPrice() {
		return marketMaxSellPrice;
	}
	public void setMarketMaxSellPrice(Double marketMaxSellPrice) {
		this.marketMaxSellPrice = marketMaxSellPrice;
	}
	public Double getMarketMinSellPrice() {
		return marketMinSellPrice;
	}
	public void setMarketMinSellPrice(Double marketMinSellPrice) {
		this.marketMinSellPrice = marketMinSellPrice;
	}
	public Double getMarketMostFrequentSellPrice() {
		return marketMostFrequentSellPrice;
	}
	public void setMarketMostFrequentSellPrice(Double marketMostFrequentSellPrice) {
		this.marketMostFrequentSellPrice = marketMostFrequentSellPrice;
	}
	public Double getSellProbability() {
		return sellProbability;
	}
	public void setSellProbability(Double sellProbability) {
		this.sellProbability = sellProbability;
	}
	public Double getAverageDaysToSell() {
		return averageDaysToSell;
	}
	public void setAverageDaysToSell(Double averageTimeToSell) {
		this.averageDaysToSell = averageTimeToSell;
	}
	@Override
	public String toString() {
		return "SellStadisticsDTO [marketMaxSellPrice=" + marketMaxSellPrice + ", marketMinSellPrice="
				+ marketMinSellPrice + ", marketMostFrequentSellPrice=" + marketMostFrequentSellPrice
				+ ", sellProbability=" + sellProbability + ", averageTimeToSell=" + averageDaysToSell
				+ ", comparedVehicleCount="  + "]";
	}
	
	
}
