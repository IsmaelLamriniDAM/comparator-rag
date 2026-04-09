package comparator.ia.app.util.chainOfResponsability.createDataOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comparator.ia.app.dtos.chain.price.PriceRange;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.service.manager.objUseInManager.Price;
import comparator.ia.app.util.chainOfResponsability.priceMessage.FindOutPriceOperation;

public class CreatePriceField extends CreateDataOperation {
	
	private final Logger logger = LoggerFactory.getLogger(CreatePriceField.class);
	
	private final FindOutPriceOperation findPriceOperation;
	
	public CreatePriceField(FindOutPriceOperation findPriceOperation) {
		this.findPriceOperation = findPriceOperation;
	}

	@Override
	public void buildFields(String message, SimilarCarOperationDTO car) {
		logger.info("creating price field.");
		
		Operation optCurrent =  car.getOperation();
		Price price = findPriceOperation.searchPrice(message, optCurrent);
		
		if(isPriceCorrect(optCurrent, price)) {
			car.setPrice(price);
		} else {
			Price priceRetun = new Price();
			priceRetun.setRangeBuy(new PriceRange(null, null));
			priceRetun.setRangeSell(new PriceRange(null, null));
			car.setPrice(priceRetun);
		}
		logger.info("created price field -> {}", car.getPrice());
		nextField.buildFields(message, car);
	}

	private boolean isPriceCorrect(Operation optCurrent, Price price) {
		return !(price == null 
				|| (price.getRangeBuy() == null && price.getRangeSell() == null) 
				|| (price.getRangeBuy() == null && optCurrent == Operation.BUY) 
				|| (price.getRangeSell() == null && optCurrent == Operation.SELL)
				|| (price.getRangeBuy() == null && price.getRangeSell() == null && optCurrent == Operation.BOTH)
				|| optCurrent == Operation.UNKNOWN);
	}

}
