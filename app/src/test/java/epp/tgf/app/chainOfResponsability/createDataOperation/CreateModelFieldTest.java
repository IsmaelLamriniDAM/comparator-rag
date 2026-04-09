package epp.tgf.app.chainOfResponsability.createDataOperation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.service.vector.VectorService;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateModelField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreatePriceField;
@ExtendWith(MockitoExtension.class)
class CreateModelFieldTest {
	
	private static final String MESSAGE_USER_WITH_MODELS = "Quiero un coche del modelo serie 3.";
	private static final String MESSAGE_USER_NOT_MODELS = "Quiero un coche del modelo EEP-IGROUP 3.";
	
	@InjectMocks
	private CreateModelField createModelField;
	
	@Mock
	private CreatePriceField createPriceField;
	
	@Mock
	private VectorService vectorService;
	
	@BeforeEach
	void setUp() {
		createModelField.setNextField(createPriceField);
	}
	
	@Test
	@DisplayName("Verifica que me setea los modelos de coches en mi dto y confirma que su sucesor es CreatePriceField.")
	void testBuildFieldModelsPut() {
		String brand = "MERCEDES BEZ";
		String modelsExpect = "BMW";
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		dto.setBrand(brand);
		
		when(vectorService.getModelVector(MESSAGE_USER_WITH_MODELS, brand)).thenReturn(modelsExpect);
		
		createModelField.buildFields(MESSAGE_USER_WITH_MODELS, dto);
		
		assertEquals(modelsExpect, dto.getModel(), "La lista de modelos que me devuelve el DTO tiene que ser igual al que espero.");
		
		verify(createPriceField).buildFields(MESSAGE_USER_WITH_MODELS, dto);
		verify(vectorService).getModelVector(MESSAGE_USER_WITH_MODELS, brand);
	}
	
	@Test
	@DisplayName("Verifica que me setea una lista vacia al no contener modelos, es decir, una lista vacia y confirma que su sucesor es CreatePriceField.")
	void testBuildFieldModelsNotPut() {
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		dto.setBrand(null);
		
		when(vectorService.getModelVector(MESSAGE_USER_NOT_MODELS, null)).thenReturn("");
		
		createModelField.buildFields(MESSAGE_USER_NOT_MODELS, dto);
		
		assertNotNull(dto.getModel());
		
		verify(vectorService).getModelVector(MESSAGE_USER_NOT_MODELS, null);
		verify(createPriceField).buildFields(MESSAGE_USER_NOT_MODELS, dto);
	}

}
