package epp.tgf.app.chainOfResponsability.createDataOperation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.enums.FuelType;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateFuelField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateYearField;

@ExtendWith(MockitoExtension.class)
class CreateFuelFieldTest {
	
	private static final String MESSAGE_USER = "Quiero comprarme un toyota de 2000 euros.";
	private static final String[] TYPES_FUEL = {"gasolina", "diesel", "glp", "electrico", "gnc", "EPP-IGROUP"};
	
	@InjectMocks
	private CreateFuelField createFuelField;
	
	@Mock
	private CreateYearField createYearField;
	
	@BeforeEach
	void setUp () {
		createFuelField.setNextField(createYearField);
	}
	
	@Test
	@DisplayName("Comprueba el sucesor de esta responsabilidad.")
	void testSucessorResponsability() {
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		createFuelField.buildFields(MESSAGE_USER, dto);
		
		verify(createYearField).buildFields(MESSAGE_USER, dto);
	}
	
	@Test
	@DisplayName("Comprueba el tipo de gasolina que indica el usuario y el sucesor de esta responsabilidad.")
	void testBuildFieldFuel() {
		String[] splitSentence = MESSAGE_USER.split("\\s+");
		for(String fuel : TYPES_FUEL) {
			SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
			int indexRandom = new Random().nextInt(splitSentence.length);
			String previousWord = splitSentence[indexRandom];
			splitSentence[indexRandom] = fuel;
			String sentenceCreated = Arrays.stream(splitSentence).collect(Collectors.joining(" "));
			
			createFuelField.buildFields(sentenceCreated, dto);
			
			assertEquals(FuelType.getFuels(fuel.toUpperCase()).toString(), dto.getFuelType().toString(), "EL tipo de combustible que espero debe ser igual al actual.");
			
			splitSentence[indexRandom] = previousWord;
		}
		
	}

}
