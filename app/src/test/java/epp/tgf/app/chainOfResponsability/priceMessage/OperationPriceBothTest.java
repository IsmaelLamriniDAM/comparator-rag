package epp.tgf.app.chainOfResponsability.priceMessage;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import comparator.ia.app.enums.Operation;
import comparator.ia.app.service.manager.objUseInManager.Price;
import comparator.ia.app.util.chainOfResponsability.priceMessage.OperationPriceBoth;
import comparator.ia.app.util.chainOfResponsability.priceMessage.OperationPriceUnknown;
@ExtendWith(MockitoExtension.class)
class OperationPriceBothTest {
	
	private static final String MESSAGE_USER = "Quiero comprarme un BMw de <compra> y luego venderlo a <venta>.";
	private static final String[] PRICES = {"20 000 euros", "30000euros", "4.450 euros", "1200,50 euROS", "entre 3000 euros a 10.00euros", "20000 a 30000 euros",
			"12000 a 20 mIL euros", "20 000 a 3K", "10000a20000euros", "40 000 euros hasta 60000 euros", "3k a 5k"};
	
	
	private static final Operation OPERATION_BOTH = Operation.BOTH;
	
	@InjectMocks
	private OperationPriceBoth operationPriceBoth;
	
	@Mock
	private OperationPriceUnknown operationPriceUnknown;

	@Test
	@DisplayName("Comprueba que al no ser la Operacion esperada para esta respansibilidad delege a su sucesor.")
	void testDelegationToHisSucessor() {
		for(Operation opt : Operation.values()) {
			operationPriceBoth.searchPrice(MESSAGE_USER, opt);
			if(opt != OPERATION_BOTH) {
				verify(operationPriceUnknown, times(1)).searchPrice(MESSAGE_USER, opt);
			}
			verify(operationPriceUnknown, never()).searchPrice(MESSAGE_USER, OPERATION_BOTH);
			clearInvocations(operationPriceUnknown);
		}
	}
	
	@Test
	@DisplayName("Comprueba que al ser la operacion BOTH me settee de manera correcta el precio del rango tanto de compra como de venta.")
	void testSearchPriceSell() {
		int indexStart = 0;
		int indexEnd = PRICES.length - 1;
		
		while(indexStart != indexEnd) {
			String message = MESSAGE_USER.replace("<compra>", PRICES[indexStart]).replace("<venta>", PRICES[indexEnd]);
			
			Price priceActual = operationPriceBoth.searchPrice(message, OPERATION_BOTH);
			
			assertNotNull(priceActual.getRangeBuy(), "El campo de RangeBuy no debe ser nulo pues se trata de una compra.");
			assertNotNull(priceActual.getRangeSell(), "El campo RangeSell debe ser nulo, pues se trata de una compra.");
			indexStart++;
			indexEnd--;
		}
	}
	
	@Test
	@DisplayName("Comprueba que el valor del rango seteado sea null pues el usuario no lo ha especificado.")
	void testNotSearchPriceBoth() {
		Price priceActual = operationPriceBoth.searchPrice(MESSAGE_USER, OPERATION_BOTH);
		
		assertNull(priceActual.getRangeBuy().max(), "El valor min del rango buy debe ser nulo pues no se menciona ningun precio para este campo.");
		assertNull(priceActual.getRangeBuy().min(), "El valor max del rango buy debe ser nulo pues no se menciona ningun precio para este campo.");
		assertNull(priceActual.getRangeSell().max(), "El valor max del rango sell debe ser nulo pues no se menciona ningun precio para este campo.");
		assertNull(priceActual.getRangeSell().min(), "El valor min del rango sell debe ser nulo pues no se menciona ningun precio para este campo.");
	}

}
