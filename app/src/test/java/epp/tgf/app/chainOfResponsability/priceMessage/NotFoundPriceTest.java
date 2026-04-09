package epp.tgf.app.chainOfResponsability.priceMessage;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import comparator.ia.app.enums.Operation;
import comparator.ia.app.service.manager.objUseInManager.Price;
import comparator.ia.app.util.chainOfResponsability.priceMessage.OperationPriceUnknown;

@Import(OperationPriceUnknown.class)
class NotFoundPriceTest {
	
	@Autowired
	private OperationPriceUnknown operationPriceUnknown;

	@Test
	@DisplayName("Verify that method searchPrice returned to Price default.")
	void testReturnedPriceDefault() {
		operationPriceUnknown = new OperationPriceUnknown();
		Price price =  operationPriceUnknown.searchPrice("THIS MESSAGE IS SO BAD", Operation.UNKNOWN);
		
		assertNotNull(price, "Price not should NULL");
		assertNotNull(price.getRangeBuy(), "RangeBuy not should NULL");
		assertNotNull(price.getRangeSell(), "RangeSell not should NULL");
		
		assertNull(price.getRangeBuy().max(), "RangeBuy in the value max should NULL");
		assertNull(price.getRangeBuy().min(), "RangeBuy in the value min should NULL");
		assertNull(price.getRangeSell().max(), "RangeSell in the value max should NULL");
		assertNull(price.getRangeSell().min(), "RangeSell in the value min should NULL");
		
		
	}

}
