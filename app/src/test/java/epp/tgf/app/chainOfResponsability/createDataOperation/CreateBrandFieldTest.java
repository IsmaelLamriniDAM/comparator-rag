package epp.tgf.app.chainOfResponsability.createDataOperation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
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
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateBrandField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateModelField;
@ExtendWith(MockitoExtension.class)
class CreateBrandFieldTest {
	
	private static final String MESSAGE_USER_WIHT_BRAND_EXIST_ = "Quiero comprar un mercedes de 2000 euros";
	private static final String MESSAGE_USER_NOT_BRAND_EXIST_ = "Quiero comprar un EEP-IGROUP de 2000 euros";
	
	@InjectMocks
	private CreateBrandField createBrandField;
	
	@Mock private CreateModelField createModelField;

	@Mock
	private VectorService vectorService;
	
	
	@BeforeEach
	void setUp() {
		createBrandField.setNextField(createModelField);
	}
	
	@Test
	@DisplayName("Verificamos que meta en este método las marcas en SimilarCarOperationDTO y el sucesor de esta responsabilidad.")
	void testBuildFieldsBrandPutBrands() {
		String expectedBrand = "MERCEDES BENZ";
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		
		when(vectorService.getBrandVector(MESSAGE_USER_WIHT_BRAND_EXIST_)).thenReturn(expectedBrand);
		
		createBrandField.buildFields(MESSAGE_USER_WIHT_BRAND_EXIST_, dto);
		
		assertEquals(expectedBrand, dto.getBrand());
		assertFalse(dto.getBrand().isEmpty());
		
		verify(vectorService, times(1)).getBrandVector(MESSAGE_USER_WIHT_BRAND_EXIST_);
		verify(createModelField, times(1)).buildFields(MESSAGE_USER_WIHT_BRAND_EXIST_, dto);
	}
	
	@Test
	@DisplayName("Verificamos que meta una lista vacia al no contener marcas reconocibles en SimilarCarOperationDTO y el sucesor de esta responsabilidad.")
	void testBuildFieldsBrandNotPutBrands() {
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		
		when(vectorService.getBrandVector(MESSAGE_USER_NOT_BRAND_EXIST_)).thenReturn("");
		
		createBrandField.buildFields(MESSAGE_USER_NOT_BRAND_EXIST_, dto);
		
		assertNull(dto.getBrand());
		
		verify(vectorService, times(1)).getBrandVector(MESSAGE_USER_NOT_BRAND_EXIST_);
		verify(createModelField, times(1)).buildFields(MESSAGE_USER_NOT_BRAND_EXIST_, dto);
	}

}
