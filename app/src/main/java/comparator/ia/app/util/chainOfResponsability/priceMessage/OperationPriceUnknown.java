package comparator.ia.app.util.chainOfResponsability.priceMessage;

import comparator.ia.app.dtos.chain.price.PriceRange;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.service.manager.objUseInManager.Price;

public class OperationPriceUnknown extends FindOutPriceOperation{

	@Override
	public Price searchPrice(String message, Operation opt) {
		logger.warn("not found range price or Operation unknown");
		Price price = new Price();
		price.setRangeBuy(new PriceRange(null, null));
		price.setRangeSell(new PriceRange(null, null));
		return price;
	}

}
