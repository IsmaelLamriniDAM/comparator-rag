package epp.tgf.app.chainOfResponsability.createDataOperation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateHpField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateYearField;
@ExtendWith(MockitoExtension.class)
class CreateYearFieldTest {
	
	private static final String MESSAGE_USER = "Quiero comprarme un toyota de 2000 euros <año> .";
	private static final String[] YEARS_SUCCESSFUL = {"DEL 2019", "del2020", "del año 2022", "delaño2020"};
	private static final String[] YEARS_BAD = {"DEL 2027", "DEL AÑO 1", "", "año", "del", "del"};

	@InjectMocks
	private CreateYearField createYearField;
	
	@Mock
	 private CreateHpField createVhField;
	
	@BeforeEach
	void setUp() {
		createYearField.setNextField(createVhField);
	}
	
	@Test
	@DisplayName("Comprueba el sucesor de esta responsabilidad.")
	void testSucessorResponsability() {
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		createYearField.buildFields(MESSAGE_USER, dto);
		
		verify(createVhField).buildFields(MESSAGE_USER, dto);
	}
	
	@Test
	@DisplayName("Comprueba si setea el año (si es correcta (year >= 1884 && year <= año actual)) del coche propuesto por el usuario.")
	void testBuildFieldYear() {
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		for(String partSentenceWithYear : YEARS_SUCCESSFUL) {
			String message = MESSAGE_USER.replace("<año>", partSentenceWithYear);
			
			System.out.println("MENSAJE DEL USUARIO .-> " + message);
			
			createYearField.buildFields(message, dto);
			
			assertEquals(getYearForMessage(partSentenceWithYear), dto.getYear(), "Aqui el año esperado el igual al actual.");
		}
	}
	
	@Test
	@DisplayName("Verifica que setee un valor nulo si no encuenta el año o si esta es incorrecta.(year >= 1884 && year <= año actual)")
	void testBuildFieldNotYear() {
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		for (String partSentenceWithYear : YEARS_BAD) {
			String message = MESSAGE_USER.replace("<año>", partSentenceWithYear);

			System.out.println("MENSAJE DEL USUARIO .-> " + message);

			createYearField.buildFields(message, dto);

			assertEquals(null, dto.getYear());
		}
	}

	private Integer getYearForMessage(String year) {
		return Integer.valueOf(year.replaceAll("\\D", ""));
	}

}
