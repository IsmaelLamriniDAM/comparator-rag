package epp.tgf.app.chainOfResponsability.createDataOperation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateBrandField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateOperationField;

@ExtendWith(MockitoExtension.class)
class CreateOperationFieldTest {
	
	private static final String[] WORDS_USER_BUY = {"COMpRARME", "QuieroCOmprarme", "comPraR", "ObtenEr", "adquirir", "llevar", "conseguir", "conseguirlo"};
	private static final String[] WORDS_USER_SELL = {"vender", "QuiEro\\s*Vender", "venden", "pillar"};
	private static final String[] WORDS_USER_BOTH = {"vender comprar", "adquirir pillar", "quiero conseguirlo para luego vender", "quiero comprarlo para luego venderlo a un millon de manzanas"};
	private static final String[] WORDS_USER_UNKNOWN = {"COMPRA", "vendel", "quiero malgastar mi dinero en manzanas", "no se de que va esto terricola", "EEP-IGROUP"};
	private static final String MESSAGE_LAST_PART = " un coche de 14000euros.";
	
	@InjectMocks
	private CreateOperationField createOperationField;
	
	@Mock
	private CreateBrandField createBrandField;

	void setUp() {
		createOperationField.setNextField(createBrandField);
	}
	
	@Test
	@DisplayName("Comprueba que el deseo del usuario es hacer una compra.")
	void testBuildFieldOperationBuy() {
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		
		for(String word : WORDS_USER_BUY) {
			StringBuilder sbWord = new StringBuilder(word);
			sbWord.append(MESSAGE_LAST_PART);
			
			createOperationField.buildFields(sbWord.toString(), dto);
			
			assertEquals(Operation.BUY, dto.getOperation());
			
			verify(createBrandField, times(1)).buildFields(sbWord.toString(), dto);
		}
		
	}
	
	@Test
	@DisplayName("Comprueba que el deseo del usuario es hacer una venta.")
	void testBuildFieldOperationSell() {
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		
		for(String word : WORDS_USER_SELL) {
			StringBuilder sbWord = new StringBuilder(word);
			sbWord.append(MESSAGE_LAST_PART);
			
			createOperationField.buildFields(sbWord.toString(), dto);
			
			assertEquals(Operation.SELL, dto.getOperation());
			
			verify(createBrandField, times(1)).buildFields(sbWord.toString(), dto);
		}
	}
	
	@Test
	@DisplayName("Comprueba que el deseo del usuario es comprar para luego vender.")
	void testBuildFieldOperationBoth() {
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		for(String word : WORDS_USER_BOTH) {
			StringBuilder sbWord = new StringBuilder(word);
			sbWord.append(MESSAGE_LAST_PART);
			
			createOperationField.buildFields(sbWord.toString(), dto);
			
			assertEquals(Operation.BOTH, dto.getOperation());
			
			verify(createBrandField, times(1)).buildFields(sbWord.toString(), dto);
		}
	}
	
	@Test
	@DisplayName("Comprueba que el mensaje del usuario no especifica lo operacion que quiere realizar.")
	void testBuildFieldOperationUnknown() {
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		for(String word : WORDS_USER_UNKNOWN) {
			StringBuilder sbWord = new StringBuilder(word);
			sbWord.append(MESSAGE_LAST_PART);
			
			createOperationField.buildFields(sbWord.toString(), dto);
			
			assertEquals(Operation.UNKNOWN, dto.getOperation());
			
			verify(createBrandField, times(1)).buildFields(sbWord.toString(), dto);
		}
		
	}

}
