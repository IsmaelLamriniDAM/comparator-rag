package epp.tgf.app.chainOfResponsability.priceMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.never;
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
import comparator.ia.app.util.chainOfResponsability.priceMessage.OperationPriceBoth;
import comparator.ia.app.util.chainOfResponsability.priceMessage.OperationPriceSell;
@ExtendWith(MockitoExtension.class)
class OperationPriceSellTest {
	
	private static final Operation OPERATION_SELL = Operation.SELL;
	
	private static final String MESSAGE_USER = "Quiero VENDER un BMw de <price>";
	private static final String[] PRICES_SELLS = {"20 000 euros", "30000euros", "4.450 euros", "1200,50 euROS", "entre 3000 euros a 10.00euros", "20000 a 30000 euros",
			"12000 a 20 mIL euros", "20 000 a 3K", "10000a20000euros", "40 000 euros hasta 60000 euros", "3k a 5k"};
	
	@InjectMocks
	private OperationPriceSell operationPriceSell;
	
	@Mock
	private OperationPriceBoth operationPriceBoth;

	@Test
	@DisplayName("Comprueba que al no ser la Operacion esperada para esta respansibilidad delege a su sucesor.")
	void testDelegationToHisSucessor() {
		for(Operation opt : Operation.values()) {
			operationPriceSell.searchPrice(MESSAGE_USER+" de 14000 euros", opt);
			if(opt != OPERATION_SELL) {
				verify(operationPriceBoth, times(1)).searchPrice(MESSAGE_USER+" de 14000 euros", opt);
			}
			verify(operationPriceBoth, never()).searchPrice(MESSAGE_USER+" de 14000 euros", OPERATION_SELL);
		}
	}
	
	@Test
	@DisplayName("Comprueba que sete de manera correcta el rango de venta propuesta por el usuario.")
	void testSearchPriceSell() {
		for(String price : PRICES_SELLS) {
			String message = MESSAGE_USER.replace("<price>", price);
			Price priceActual = operationPriceSell.searchPrice(message, OPERATION_SELL);
			
			assertNotNull(priceActual.getRangeSell(), "El campo de RangeBuy no debe ser nulo pues se trata de una compra.");
			
			assertNull(priceActual.getRangeBuy(), "Aqui el campo RangeBuy debe ser nulo pues se trata de un venta.");
		}
	}
	
	@Test
	@DisplayName("Verifica que no se setee ningun rango de precio al no especificarse.")
	void testNotSearchPriceSell() {
		Price price = new Price();
		price.setRangeBuy(new PriceRange(null, null));
		price.setRangeSell(new PriceRange(null, null));
		when(operationPriceBoth.searchPrice(MESSAGE_USER, Operation.UNKNOWN)).thenReturn(price);
		
		Price priceActual = operationPriceSell.searchPrice(MESSAGE_USER, OPERATION_SELL);
		
		assertNull(priceActual.getRangeSell().max(), "El campo RangeBuy max debe ser nulo, pues nos especifica.");
		assertNull(priceActual.getRangeSell().min(), "El campo RangeBuy min debe ser nulo, pues nos especifica.");
		
		assertNull(priceActual.getRangeBuy().max(), "El campo RangeSell debe ser nulo, pues se trata de una compra.");
		assertNull(priceActual.getRangeBuy().min(), "El campo RangeSell debe ser nulo, pues se trata de una compra.");
	}
	
}
