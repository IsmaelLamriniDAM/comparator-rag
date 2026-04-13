package comparator.ia.app.util.chainOfResponsability.priceMessage;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comparator.ia.app.dtos.chain.price.PriceRange;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.service.manager.objUseInManager.Price;

public abstract class FindOutPriceOperation {
	
	protected static final Logger logger = LoggerFactory.getLogger(FindOutPriceOperation.class);
	
	protected static final Pattern REGEX_PRICE = Pattern.compile("\\d+([, .]*\\d*(mil)?)?\\s*(euros?|dollars?|\\$|€|k\\b)");
	private static final Pattern REGEX_MIN_PRICE = Pattern.compile("\\d+[ .,]*\\d*\\s*[a]");
	private static final Pattern NUM_PATTERN = Pattern.compile("\\d+([ .,]*\\d+)*");
	private static final Pattern K_PATTERN = Pattern.compile("[k(mil)]+");
	
	protected FindOutPriceOperation next;
	
	public abstract Price searchPrice(String message, Operation opt);
	
	public void setNextResponsability(FindOutPriceOperation next) {
		this.next = next;
	}
	
	public PriceRange getRangePriceForMessage(String message) {
		logger.info("search range price for this message -> {}", message);
		
		String messageLower = message.toLowerCase();
		List<Double> prices = new LinkedList<>();
		Matcher matcherMax = REGEX_PRICE.matcher(messageLower);
		while(matcherMax.find()) {
			StringBuilder matcherSb = new StringBuilder(matcherMax.group());
			Matcher matcherWithK = K_PATTERN.matcher(matcherSb);
			while(matcherWithK.find()) {
				matcherSb = new StringBuilder(matcherSb.toString().replaceAll("[k(mil)]+", "000"));
			}
			
			Matcher matcherMin = REGEX_MIN_PRICE.matcher(messageLower.substring(0, matcherMax.start()).replaceAll("\s+", " "));
			if(matcherMin.find()) {
				if(matcherMax.start() - matcherMin.end() <= 2) {
					prices.add(cleanMessagePrice(matcherMin.group()));
				}
			}
			prices.add(cleanMessagePrice(matcherSb.toString()));
			
		}
		
		prices.sort(Comparator.naturalOrder());
		if(prices.isEmpty()) { 
			logger.warn("not found range price");
			return new PriceRange(null, null);
		}
		return new PriceRange(prices.getLast(), prices.getFirst());
	}
	
	private Double cleanMessagePrice(String pieceMessage) {
		String messageChangeSignals = changeSignals(pieceMessage);
		String clean = messageChangeSignals.replaceAll("[^\\d.]", "");
		return Double.valueOf(clean);
	}
	
	private String changeSignals(String num) {
		StringBuilder sb = new StringBuilder(num);
		Matcher matcher = NUM_PATTERN.matcher(num);
		while(matcher.find()) {
			int index = matcher.start();
			for(Character c : matcher.group().toCharArray()) {
				if(c == '.' || c == ',') {
					if(c < '.') {
						sb.setCharAt(index, '.');
					} else {
						sb.setCharAt(index, ',');
					}
				}
				index++;
			}
		}
		return sb.toString();
	}
}
