package epp.tgf.app.chainOfResponsability.createDataOperation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import comparator.ia.app.dtos.chain.price.PriceRange;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.service.manager.objUseInManager.Price;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateFuelField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreatePriceField;
import comparator.ia.app.util.chainOfResponsability.priceMessage.FindOutPriceOperation;
@ExtendWith(MockitoExtension.class)
class CreatePriceFieldTest {
	
	private static final String MESSAGE_USER = "MENSAJE 1";
	
	@InjectMocks
	private CreatePriceField createPriceField;
	
	@Mock
	private CreateFuelField createFuelField;
	
	@Mock
	private FindOutPriceOperation findPriceOperation;
	
	@BeforeEach
	void setUp() {
		createPriceField.setNextField(createFuelField);
	}
	
	@Test
	@DisplayName("Comprueba que setea el precio correcto segun la operacion del usuario anteriormente dicha.")
	void testBuildFieldPrice() {
		for(Operation opt : Operation.values()) {
			SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
			dto.setOperation(opt);
			Price priceExpect = new Price(); 
			setValueAccordingOpt(priceExpect, opt);
			
			when(findPriceOperation.searchPrice(MESSAGE_USER, opt)).thenReturn(priceExpect);
			
			createPriceField.buildFields(MESSAGE_USER, dto);

			if (opt == Operation.UNKNOWN) {
				assertEquals(null, dto.getPrice().getRangeBuy().max(), "Cuando la operacion es desconocida el valor del precio max debe ser nulo.");
				assertEquals(null, dto.getPrice().getRangeBuy().min(), "Cuando la operacion es desconocida el valor del precio debe min ser nulo.");
			} else {
				assertEquals(priceExpect, dto.getPrice(), "Cuando la operacion es conocida debe retornar el precio.");
				assertEquals(opt, dto.getOperation(), "La operacion debe ser la misma.");
			}
			
			verify(findPriceOperation).searchPrice(MESSAGE_USER, opt);
			verify(createFuelField).buildFields(MESSAGE_USER, dto);
		}

	}

	private void setValueAccordingOpt(Price priceExpect, Operation opt) {
		switch (opt) {
		case Operation.BUY:
			priceExpect.setRangeBuy(new PriceRange(1.0,1.0));
			break;
		case Operation.SELL:
			priceExpect.setRangeSell(new PriceRange(1.0,1.0));
			break;
		case Operation.BOTH:
			priceExpect.setRangeSell(new PriceRange(1.0,1.0));
			priceExpect.setRangeBuy(new PriceRange(1.0,1.0));
			break;
		default:
			break;
		}
		
	}
	
}
