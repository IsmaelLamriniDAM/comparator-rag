package comparator.ia.app.dtos.stadistics;

import java.util.List;

import comparator.ia.app.dtos.logic.VehicleDto;
import comparator.ia.app.dtos.stadistics.both.SaleProfitDTO;
import comparator.ia.app.dtos.stadistics.sendView.SendCarView;
import comparator.ia.app.enums.Recommendation;

public class BothOperationStadisticsDTO {
	
	private Integer marketSellComparableVehicleCount;
	private Double sellSuccessProbability;
	private SaleProfitDTO estimatedSaleProfit;
	private Double averageDaysToSell;
	private Double marketMinBuyPrice;
	private SendCarView vehicleFormSent;
	private Integer countVehiclesHasBeenCompared;
	private List<VehicleDto> listVehiclesCompared;
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
	public Integer getMarketSellComparableVehicleCount() {
		return marketSellComparableVehicleCount;
	}
	public void setMarketSellComparableVehicleCount(Integer marketSellComparableVehicleCount) {
		this.marketSellComparableVehicleCount = marketSellComparableVehicleCount;
	}
	public Double getSellSuccessProbability() {
		return sellSuccessProbability;
	}
	public void setSellSuccessProbability(Double sellSuccessProbability) {
		this.sellSuccessProbability = sellSuccessProbability;
	}
	public SaleProfitDTO getEstimatedSaleProfit() {
		return estimatedSaleProfit;
	}
	public void setEstimatedSaleProfit(SaleProfitDTO estimatedSaleProfit) {
		this.estimatedSaleProfit = estimatedSaleProfit;
	}
	public Double getAverageDaysToSell() {
		return averageDaysToSell;
	}
	public void setAverageDaysToSell(Double averageDaysToSell) {
		this.averageDaysToSell = averageDaysToSell;
	}
	public Double getMarketMinBuyPrice() {
		return marketMinBuyPrice;
	}
	public void setMarketMinBuyPrice(Double marketMinBuyPrice) {
		this.marketMinBuyPrice = marketMinBuyPrice;
	}
	@Override
	public String toString() {
		return "BothOperationStadisticsDTO [marketSellComparableVehicleCount=" + marketSellComparableVehicleCount
				+ ", sellSuccessProbability=" + sellSuccessProbability + ", estimatedSaleProfit=" + estimatedSaleProfit
				+ ", averageDaysToSell=" + averageDaysToSell + ", marketMinBuyPrice=" + marketMinBuyPrice
				+ ", marketComparedVehicleCount="  + "]";
	}
	
}
