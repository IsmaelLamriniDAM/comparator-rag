package comparator.ia.app.dtos.logic;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;


public class DataDto {	
	private List<VehicleDto> carList;
	
	private Integer numberVehicles;

	@Schema(description = "Precio maximo del coche")
	private double maxPrice;
	@Schema(description = "Precio minimo del coche")
	private double minPrice;
	
	@Schema(description = "Precio mas repetido del coche")
	private double modePrice;
	
	@Schema(description = "Precio medio del coche")
	private double averagePrice;
	
	@Schema(description = "Details about the cars sold.")
	private SalesData salesData;
	
	@Schema(description = "Rentabilidad maxima del coche sobre el precio máximo")
	private ProfitabilityDetail profitabilityMaxPrice;
	
	@Schema(description = "Rentabilidad minima del coche sobre la media del precio")
	private ProfitabilityDetail profitabilityModePrice;
	
	@Schema(description = "Rentabilidad minima del coche sobre el precio medio")
	private ProfitabilityDetail profitabilityAveragePrice;
	
	public DataDto() {}
	
	@Schema(description = "Details about the cars sold.")
	public static class SalesData {
		
		@Schema(description = "Estimated number of days it takes to sell.", example = "5")
		private Integer estimatedTime;
		@Schema(description = "Most frequent price of sold cars.", example = "5500.0")
		private Double mostFrequentClosingPrice;
		
		public SalesData() {}

		public Integer getEstimatedTime() {
			return estimatedTime;
		}

		public void setEstimatedTime(Integer estimatedTime) {
			this.estimatedTime = estimatedTime;
		}

		public Double getMostFrequentClosingPrice() {
			return mostFrequentClosingPrice;
		}

		public void setMostFrequentClosingPrice(Double mostFrequentClosingPrice) {
			this.mostFrequentClosingPrice = mostFrequentClosingPrice;
		}
		
	}

	public static class ProfitabilityDetail {
        
        private double percentage; 
        private double money; 

        public ProfitabilityDetail () {}

        public void setPercentage(double percentage) {
            this.percentage = percentage;
        }

        public void setMoney(double money) {
            this.money = money;
        }
        
        public double getPercentage() {
            return percentage;
        }

        public double getMoney() {
            return money;
        }
        
    }
	
	public Integer getNumberVehicles() {
		return numberVehicles;
	}

	public void setNumberVehicles(Integer numberVehicles) {
		this.numberVehicles = numberVehicles;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public double getModePrice() {
		return modePrice;
	}

	public void setModePrice(double modePrice) {
		this.modePrice = modePrice;
	}

	public ProfitabilityDetail getProfitabilityMaxPrice() {
		return profitabilityMaxPrice;
	}

	public void setProfitabilityMaxPrice(ProfitabilityDetail profitabilityMaxPrice) {
		this.profitabilityMaxPrice = profitabilityMaxPrice;
	}

	public ProfitabilityDetail getProfitabilityModePrice() {
		return profitabilityModePrice;
	}

	public void setProfitabilityModePrice(ProfitabilityDetail profitabilityModePrice) {
		this.profitabilityModePrice = profitabilityModePrice;
	}

	public List<VehicleDto> getCarList() {
		return carList;
	}

	public void setCarList(List<VehicleDto> carList) {
		this.carList = carList;
	}

	public ProfitabilityDetail getProfitabilityAveragePrice() {
		return profitabilityAveragePrice;
	}

	public void setProfitabilityAvaregePrice(ProfitabilityDetail profitabilityAvaregePrice) {
		this.profitabilityAveragePrice = profitabilityAvaregePrice;
	}

	public double getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(double avaregePrice) {
		this.averagePrice = avaregePrice;
	}

	public SalesData getSalesData() {
		return salesData;
	}

	public void setSalesData(SalesData salesData) {
		this.salesData = salesData;
	}
	
	
}
