package comparator.ia.app.util.chainOfResponsability.createDataOperation;

import java.util.regex.Pattern;

import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.enums.Operation;

public class CreateOperationField extends CreateDataOperation {
	
	private static final Pattern REGEX_BUY = Pattern.compile("(comprarlo|comprar|quiero\\s*comprarlo|comprarme|obtener|adquirir|llevar|conseguir|conseguirlo)");
	private static final Pattern REGEX_SELL = Pattern.compile("(venderlo|vender|quiero\\s*venderlo|venden|pillar)");

	@Override
	public void buildFields(String message, SimilarCarOperationDTO car) {
		logger.info("creating operation field");
		
		String messageLower = message.toLowerCase();
		boolean itsBuy = REGEX_BUY.matcher(messageLower).find();
		boolean itsSell = REGEX_SELL.matcher(messageLower).find();

		if(itsSell && itsBuy) {
			car.setOperation(Operation.BOTH);
			logger.info("created operation field -> {}", Operation.BOTH);
			nextField.buildFields(message, car);
		} else if(itsBuy) {
			car.setOperation(Operation.BUY);
			logger.info("created operation field -> {}", Operation.BUY);
			nextField.buildFields(message, car);
		} else if(itsSell) {
			car.setOperation(Operation.SELL);
			logger.info("created operation field -> {}", Operation.SELL);
			nextField.buildFields(message, car);
		} else {
			car.setOperation(Operation.UNKNOWN);
			logger.info("created operation field -> {}", Operation.UNKNOWN);
			nextField.buildFields(message, car);
		}
	}

}
