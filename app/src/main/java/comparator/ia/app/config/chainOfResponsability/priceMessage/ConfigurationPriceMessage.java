package comparator.ia.app.config.chainOfResponsability.priceMessage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import comparator.ia.app.util.chainOfResponsability.priceMessage.FindOutPriceOperation;
import comparator.ia.app.util.chainOfResponsability.priceMessage.OperationPriceBoth;
import comparator.ia.app.util.chainOfResponsability.priceMessage.OperationPriceBuy;
import comparator.ia.app.util.chainOfResponsability.priceMessage.OperationPriceSell;
import comparator.ia.app.util.chainOfResponsability.priceMessage.OperationPriceUnknown;

@Configuration
public class ConfigurationPriceMessage {
	
	
	@Bean
	public FindOutPriceOperation createInstance() {
		FindOutPriceOperation buy = new OperationPriceBuy();
		OperationPriceSell sell = new OperationPriceSell();
		OperationPriceBoth both = new OperationPriceBoth();
		OperationPriceUnknown unknown = new OperationPriceUnknown();
		
		buy.setNextResponsability(sell);
		sell.setNextResponsability(both);
		both.setNextResponsability(unknown);
		return buy;
	}
}
