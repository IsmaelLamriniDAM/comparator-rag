package epp.tgf.app.chainOfResponsability.priceMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import comparator.ia.app.dtos.chain.price.PriceRange;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.service.manager.objUseInManager.Price;
import comparator.ia.app.util.chainOfResponsability.priceMessage.OperationPriceBuy;
import comparator.ia.app.util.chainOfResponsability.priceMessage.OperationPriceSell;

@ExtendWith(MockitoExtension.class)
class OperationPriceBuyTest {
	
	private static final String MESSAGE_USER = "Quiero comparme un BMw de <price>";
	private static final String[] PRICES = {"20 000 euros", "30000euros", "4.450 euros", "1200,50 euROS", "entre 3000 euros a 10.00euros", "20000 a 30000 euros",
			"12000 a 20 mIL euros", "20 000 a 3K", "10000a20000euros", "40 000 euros hasta 60000 euros", "3k a 5k"};
	
	private static final Operation OPERATION_BUY = Operation.BUY;
	
	@InjectMocks
	private OperationPriceBuy operationPriceBuy;
	@Mock
	private OperationPriceSell operationPriceSell;

	@Test
	@DisplayName("Comprueba que al no ser la Operacion esperada para esta respansibilidad delege a su sucesor.")
	void testDelegationToHisSucessor() {
		for(Operation opt : Operation.values()) {
			operationPriceBuy.searchPrice(MESSAGE_USER, opt);
			if(opt != OPERATION_BUY) {
				verify(operationPriceSell, times(1)).searchPrice(MESSAGE_USER, opt);
			}
		}
	}
	
	@Test
	@DisplayName("Comprueba que al ser la operacion BUY me settee de manera correcta el precio del rango impuesto por el usuario.")
	void testSearchPriceBuy() {
		for(String price : PRICES) {
			String message = MESSAGE_USER.replace("<price>", price);
			
			Price priceActual = operationPriceBuy.searchPrice(message, OPERATION_BUY);
			
			assertNotNull(priceActual.getRangeBuy(), "El campo de RangeBuy no debe ser nulo pues se trata de una compra.");
			
			assertNull(priceActual.getRangeSell(), "El campo RangeSell debe ser nulo, pues se trata de una compra.");
		}
	}
	
	@Test
	@DisplayName("Verifica que no se setee ningun rango de precio al no especificarse.")
	void testNotSearchPriceBuy() {
		Price price = new Price();
		price.setRangeBuy(new PriceRange(null, null));
		price.setRangeSell(new PriceRange(null, null));
		when(operationPriceSell.searchPrice(MESSAGE_USER, OPERATION_BUY)).thenReturn(price);
		
		Price priceActual = operationPriceBuy.searchPrice(MESSAGE_USER, OPERATION_BUY);
		
		assertNull(priceActual.getRangeBuy().max(), "El campo RangeBuy max debe ser nulo, pues nos especifica.");
		assertNull(priceActual.getRangeBuy().min(), "El campo RangeBuy min debe ser nulo, pues nos especifica.");
		
		assertNull(priceActual.getRangeSell().max(), "El campo RangeSell debe ser nulo, pues se trata de una compra.");
		assertNull(priceActual.getRangeSell().min(), "El campo RangeSell debe ser nulo, pues se trata de una compra.");
	}
	
	
}

