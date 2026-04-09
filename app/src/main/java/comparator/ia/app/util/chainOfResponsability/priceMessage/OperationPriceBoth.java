package comparator.ia.app.util.chainOfResponsability.priceMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import comparator.ia.app.dtos.chain.price.PriceRange;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.service.manager.objUseInManager.Price;

public class OperationPriceBoth extends FindOutPriceOperation {
	
	private static final Pattern REGEX_BUY = Pattern.compile("(comprarlo|comprar|quiero\\s*comprarlo|comprarme)");
	private static final Pattern REGEX_SELL = Pattern.compile("(venderlo|vender|quiero\\s*venderlo)");
	
	@Override
	public Price searchPrice(String message, Operation opt) {
		if(opt == Operation.BOTH) {
			logger.info("creating price -> {}", Operation.BOTH);
			Price price = new Price();
			List<String> messageForParts = new ArrayList<>(Arrays.stream(message.split("\\s+")).toList()).reversed();
			List<String> listParts = new ArrayList<>();
			for(String part :messageForParts) {
				listParts.add(part);
				boolean isBuyMatched = REGEX_BUY.matcher(part).find();
				boolean isSellMatched = REGEX_SELL.matcher(part).find();
				
				if(isBuyMatched) {
					setRangePrice(listParts, price, Operation.BUY);
					listParts = new ArrayList<>();
				} else if(isSellMatched) {
					setRangePrice(listParts, price, Operation.SELL);
					listParts = new ArrayList<>();
				}
			}
			
			if(price.getRangeBuy() != null && price.getRangeSell() != null) {
				return price;
			}
			logger.info("not found price -> {}",  Operation.BOTH);
		}
		return next.searchPrice(message, opt);
	}
	
	private void setRangePrice(List<String> listParts, Price price, Operation opt) {
		Collections.reverse(listParts);
		String messageSend = listParts.stream().collect(Collectors.joining(" "));
		PriceRange range = getRangePriceForMessage(messageSend);
		if (opt == Operation.BUY) {
			price.setRangeBuy(range);
		} else {
			price.setRangeSell(range);
		}
	}
}
