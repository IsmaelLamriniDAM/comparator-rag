package epp.tgf.app.chainOfResponsability.priceMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.spy;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.junit.jupiter.MockitoExtension;

import comparator.ia.app.dtos.chain.price.PriceRange;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.util.chainOfResponsability.priceMessage.*;

@ExtendWith(MockitoExtension.class)
class FindOutPriceOperationTest {
	
	private static final String MESSAGE_USER = "Quiero comparme un BMw de <price>";
	private static final String[] PRICES = {"20 000 euros", "30000euros", "4.450 euros", "1200,50 euROS", "entre 3000 euros a 10.00euros", "20000 a 30000 euros",
			"12000 a 20 mIL euros", "20 000 a 3K", "10000a20000euros", "40 000 euros hasta 60000 euros", "3k a 5k"};

	private static final Pattern NUM_PATTERN = Pattern.compile("\\d+([ .,]*\\d+)*\\s*(k|mil)?");
	private static final Pattern K_PATTERN = Pattern.compile("[k(mil)]+");
	
	private OperationPriceBuy operationPriceBuy;
	private OperationPriceSell operationPriceSell;
	private OperationPriceBoth operationPriceBoth;
	private OperationPriceUnknown operationPriceUnknown;
	
	@BeforeEach
	void setUp() {
		operationPriceBuy = spy(new OperationPriceBuy());
		operationPriceSell = spy(new OperationPriceSell());
		operationPriceBoth = spy(new OperationPriceBoth());
		operationPriceUnknown = spy(new OperationPriceUnknown());
		
		operationPriceBuy.setNextResponsability(operationPriceSell);
		operationPriceSell.setNextResponsability(operationPriceBoth);
		operationPriceBoth.setNextResponsability(operationPriceUnknown);
	}
	
	@Test
	@DisplayName("Verifica el orden de la cadena de responsabilidades.")
	void testOrderResponsabilities() {
		InOrder order = inOrder(operationPriceBuy, operationPriceSell,
				operationPriceBoth, operationPriceUnknown);
		
		operationPriceBuy.searchPrice(MESSAGE_USER, Operation.UNKNOWN);
		
		order.verify(operationPriceBuy).searchPrice(MESSAGE_USER, Operation.UNKNOWN);
		order.verify(operationPriceSell).searchPrice(MESSAGE_USER, Operation.UNKNOWN);
		order.verify(operationPriceBoth).searchPrice(MESSAGE_USER, Operation.UNKNOWN);
		order.verify(operationPriceUnknown).searchPrice(MESSAGE_USER, Operation.UNKNOWN);
	}
	@Test
    @DisplayName("Comprueba que el metodo devuelva el rango de precio establecido por el usuario.")
    void getRangePriceForMessage() {
		FindOutPriceOperation findOutPriceOperation = new OperationPriceBoth();
		for(String price : PRICES) {
			String message = MESSAGE_USER.replace("<price>", price);
			
			PriceRange rangeActual = findOutPriceOperation.getRangePriceForMessage(message);
			
			PriceRange rangeExpect = getRangePriceExpect(price);
			
			assertEquals(rangeExpect.max(), rangeActual.max(), "Es maximo esperado debe ser igual al valor maximo dado.");
			assertEquals(rangeExpect.min(), rangeActual.min(), "Es maximo esperado debe ser igual al valor maximo dado.");
			
		}
    }
	private PriceRange getRangePriceExpect(String message) {
		String messsageLower = message.toLowerCase();
		List<Double> nums = new LinkedList<>();
		Matcher matcher = NUM_PATTERN.matcher(messsageLower);
		while(matcher.find()) {
			StringBuilder matcherSb = new StringBuilder(matcher.group());
			Matcher matcherWithK = K_PATTERN.matcher(matcherSb);
			while(matcherWithK.find()) {
				matcherSb = new StringBuilder(matcherSb.toString().replaceAll("[k(mil)]+", "000"));
			}
			String num = changeSignals(matcherSb.toString());
			nums.add(Double.valueOf(num.replaceAll("[^\\d.]", "")));
		}
		nums.sort(Comparator.naturalOrder());
		
		return new PriceRange(nums.getLast(), nums.getFirst());
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
