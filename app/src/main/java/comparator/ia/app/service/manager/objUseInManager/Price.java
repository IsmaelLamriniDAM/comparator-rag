package comparator.ia.app.service.manager.objUseInManager;

import comparator.ia.app.dtos.chain.price.PriceRange;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Price")
public class Price {
	
	@Schema(name = "Range price buy")
	private PriceRange rangeBuy;
	
	@Schema(name = "Range price sell")
	private PriceRange rangeSell;
	
	public PriceRange getRangeBuy() {
		return rangeBuy;
	}
	public void setRangeBuy(PriceRange rangeBuy) {
		this.rangeBuy = rangeBuy;
	}
	public PriceRange getRangeSell() {
		return rangeSell;
	}
	public void setRangeSell(PriceRange rangeSell) {
		this.rangeSell = rangeSell;
	}
	@Override
	public String toString() {
		return "Price [rangeBuy=" + rangeBuy + ", rangeSell=" + rangeSell + "]";
	}
	
}
