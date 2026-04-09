package comparator.ia.app.dtos.stadistics.buy;

public class PriceDifferenceAboveMarketDTO {
	private Double percentage;
	private Double money;
	public Double getPercentage() {
		return percentage;
	}
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "PercentageSaveMoneyDTO [percentage=" + percentage + ", money=" + money + "]";
	}
	
}
