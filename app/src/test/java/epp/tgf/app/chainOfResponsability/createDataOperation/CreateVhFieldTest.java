package epp.tgf.app.chainOfResponsability.createDataOperation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import comparator.ia.app.dtos.chain.HorsePower;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateHpField;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateKmField;

@ExtendWith(MockitoExtension.class)
class CreateVhFieldTest {
	
	private static final String MESSAGE_USER = "Quiero comprarme un toyota de <ph>";
	private static final String[] VALUE_NOT_RANGE_PH = {"20 caballos", "200 CAballos",
			"300 ph", "100ph", "100Caballos"};
	private static final String[] VALUE_RANGE_PH = {"100 Ph a 200 caballos", "10 a 100 caballos", "200 - 300 ph",
			"100 ph a 300 ph", "200PH A 400caballos", "20caballosa200ph", "20ph a 100ph",
			"100CABAllos a 200CaBallos", "de entre 200 hasta 400 ph"};
	
	private static final Pattern NUM_PATTERN = Pattern.compile("\\d+([ .]*\\d+)*");
	
	@InjectMocks
	private CreateHpField createVhField;

	@Mock
	private CreateKmField createKmField;
	
	@BeforeEach
	void setUp () {
		createVhField.setNextField(createKmField);
	}
	
	@Test
	@DisplayName("Comprueba el sucesor de esta responsabilidad.")
	void testSucessorResponsability() {
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		createVhField.buildFields(MESSAGE_USER, dto);
		
		verify(createKmField).buildFields(MESSAGE_USER, dto);
	}

	@Test
	@DisplayName("Comprueba que el valor unico vh se setee tanto en el max como en el min.")
	void testBuildFieldVh() {
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		
		for(String vhSuccessful : VALUE_NOT_RANGE_PH) {
			String message =  MESSAGE_USER.replace("<ph>", vhSuccessful);
			
			createVhField.buildFields(message ,dto);
			
			assertEquals(getVhForMessage(vhSuccessful).max(), dto.getHp().max(), "El vh maximo debe ser igual al esperado");
			assertEquals(getVhForMessage(vhSuccessful).min(), dto.getHp().min(), "El vh minimo debe ser igual al esperado");
		}
	}

	@Test
	@DisplayName("Comprueba que el valor expresado en un rango se setee de manera correcta.")
	void testBuildFieldVhRange() {
		for (String vhSuccessful : VALUE_RANGE_PH) {
			SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
			String message = MESSAGE_USER.replace("<ph>", vhSuccessful);

			createVhField.buildFields(message ,dto);
			
			HorsePower expectValue = getVhValueRangeForMessage(vhSuccessful);
			
			assertEquals(expectValue.max(), dto.getHp().max(), "Este assert comprueba que el valor maximo del rango dado sea el esperado.");
			assertEquals(expectValue.min(), dto.getHp().min(), "Este assert comprueba que el valor minimo del rango dado sea el esperado.");
		}
	}
	
	@Test
	@DisplayName("El valor del campo vh en max y min debe ser nulo al no encontrarse en el enunciado del usuario.")
	void testNotBuildFieldVh() {
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		
		createVhField.buildFields(MESSAGE_USER ,dto);
		
		assertEquals(null, dto.getHp().max());
		assertEquals(null, dto.getHp().min());
	}
	
	private HorsePower getVhValueRangeForMessage(String message) {
		List<Integer> nums = new LinkedList<>();
		Matcher matcher = NUM_PATTERN.matcher(message);
		while(matcher.find()) {
			nums.add(Integer.valueOf(matcher.group()));
		}
		
		nums.sort(Comparator.naturalOrder());
		return new HorsePower(nums.getLast(), nums.getFirst());
	}
	
	private HorsePower getVhForMessage(String vh) {
		Integer vhValue = Integer.valueOf(vh.replaceAll("\\D", ""));
		return new HorsePower(vhValue, vhValue);
	}

}
