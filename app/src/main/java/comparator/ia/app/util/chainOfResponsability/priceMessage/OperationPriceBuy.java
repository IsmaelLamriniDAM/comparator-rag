package comparator.ia.app.util.chainOfResponsability.priceMessage;

import comparator.ia.app.dtos.chain.price.PriceRange;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.service.manager.objUseInManager.Price;

public class OperationPriceBuy extends FindOutPriceOperation{

	@Override
	public Price searchPrice(String message, Operation opt) {
		if(opt == Operation.BUY) {
			logger.info("creating price -> {}", Operation.BUY);
			Price price = new Price();
			PriceRange range = getRangePriceForMessage(message);
			
			if(range.max() == null || range.min() == null) {
				logger.warn("not found price -> {}",  Operation.BUY);
				return next.searchPrice(message, opt);
			} 
			price.setRangeBuy(range);
			return price;
		}
		
		return next.searchPrice(message, opt);
	}


}
