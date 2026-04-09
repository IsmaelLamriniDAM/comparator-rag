package epp.tgf.app.chainOfResponsability.createDataOperation;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.repository.BrandRepository;
import comparator.ia.app.repository.ModelRepository;
import comparator.ia.app.service.alias.brand.BrandAliasService;
import comparator.ia.app.service.alias.model.ModelAliasService;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateBrandField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateFuelField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateHpField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateKmField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateModelField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateOperationField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreatePriceField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateYearField;
import comparator.ia.app.util.chainOfResponsability.priceMessage.FindOutPriceOperation;

@ExtendWith(MockitoExtension.class)
class CreateDataOperationTest {
	
	private static final String MESSAGE_USER = "MENSSAGE USER";
	
    @Mock
    private ModelAliasService modelAliasService;
    
    @Mock
    private BrandAliasService brandAliasService;
    
    @Mock 
    private FindOutPriceOperation findOutPriceMock;
    
    @Mock 
    private BrandRepository brandRepo;
    
    @Mock 
    private ModelRepository modelRepoMock;

    private CreateOperationField createOperationField;
    private CreateBrandField createBrandField;
    private CreateModelField createModelField;
    private CreatePriceField createPriceField;
    private CreateFuelField createFuelField;
    private CreateYearField createYearField;
    private CreateHpField createVhField;
    private CreateKmField createKmField;

    @BeforeEach
    void setUp() {
        createKmField = spy(new CreateKmField());
        createVhField = spy(new CreateHpField());
        createYearField = spy(new CreateYearField());
        createFuelField = spy(new CreateFuelField());
        createPriceField = spy(new CreatePriceField(findOutPriceMock));
        createModelField = spy(new CreateModelField(modelAliasService, modelRepoMock, brandRepo));
        createBrandField = spy(new CreateBrandField(brandAliasService));
        createOperationField = spy(new CreateOperationField());

        createOperationField.setNextField(createBrandField);
        createBrandField.setNextField(createModelField);
        createModelField.setNextField(createPriceField);
        createPriceField.setNextField(createFuelField);
        createFuelField.setNextField(createYearField);
        createYearField.setNextField(createVhField);
        createVhField.setNextField(createKmField);

    }

    @Test
    @DisplayName("Verificamos que se ejecutan los metodos en el orden correcto.")
    void testBuildFieldsOrder() {
        SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
        
        createOperationField.buildFields(MESSAGE_USER, dto);

        InOrder order = inOrder(createOperationField, createBrandField, createModelField, 
                               createPriceField, createFuelField, createYearField, 
                               createVhField, createKmField);
        
        order.verify(createOperationField).buildFields(MESSAGE_USER, dto);
        order.verify(createBrandField).buildFields(MESSAGE_USER, dto);
        order.verify(createModelField).buildFields(MESSAGE_USER, dto);
        order.verify(createPriceField).buildFields(MESSAGE_USER, dto);
        order.verify(createFuelField).buildFields(MESSAGE_USER, dto);
        order.verify(createYearField).buildFields(MESSAGE_USER, dto);
        order.verify(createVhField).buildFields(MESSAGE_USER, dto);
        order.verify(createKmField).buildFields(MESSAGE_USER, dto);
    }
}
