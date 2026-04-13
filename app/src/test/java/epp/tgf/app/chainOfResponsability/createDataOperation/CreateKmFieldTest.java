package epp.tgf.app.chainOfResponsability.createDataOperation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import comparator.ia.app.dtos.chain.Kilometers;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateDataOperation;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateKmField;

@ExtendWith(MockitoExtension.class)
class CreateKmFieldTest {
	
	private static final String MESSAGE_USER = "Quiero un BMW de 120 000 euros de <km>";
	private static final String[] KMS = {"100 000KM", "100.000 km",
			"100000 kilometros", "30000kilometros", "120,000 km"};
	
	private static final String[] KMS_RANGE = {"DE ENTREE 20 000km a 50000 kilometros",
			"de entre un 10.000kiloMETROS a 100000KM", "de entre 200 a 10000km", "de entre 80 km hasta 100kilometROS",
			"200,000 a 220.000km"};
	
	private static final Pattern NUM_PATTERN = Pattern.compile("\\d+([ .,]*\\d+)*");
	
	@InjectMocks
	private CreateKmField createKmField;
	
	@Mock
	private CreateDataOperation createDataOperation;
	
	@Test
	@DisplayName("Verifica que setee de manera correcta el valor unico de km del usuario."
			+ "Tanto el valor del max como del min son iguales.")
	void testBuildFieldKm() {
		for(String km : KMS) {
			SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
			String message = MESSAGE_USER.replace("<km>", km);
			
			createKmField.buildFields(message , dto);
			
			Kilometers expectKm = getKilometersForMessage(km);
			
			assertEquals(expectKm.max(), dto.getKm().max(), "Aqui el km maximo debe ser igual al esperado");
			assertEquals(expectKm.min(), dto.getKm().min(), "Aqui el km minimo debe ser igual al esperado");
		}
	}
	
	@Test
	@DisplayName("Comprueba que seete de forma correcta el rango de km propuesta por el usuario.")
	void testBuildFieldKmRange() {
		for(String km : KMS_RANGE) {
			SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
			String message = MESSAGE_USER.replace("<km>", km);
			
			createKmField.buildFields(message , dto);
			
			Kilometers expectKm = getKilometersForMessage(km);
			
			assertEquals(expectKm.max(), dto.getKm().max(), "Aqui el km maximo debe ser igual al esperado");
			assertEquals(expectKm.min(), dto.getKm().min(), "Aqui el km minimo debe ser igual al esperado");
		}
		
	}
	
	@Test
	@DisplayName("Verifica que no construye el campo km max y min al no indicarse nada al respecto.")
	void testNotBuildKm() {
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		
		createKmField.buildFields(MESSAGE_USER , dto);
		
		assertEquals(null, dto.getKm().max());
		assertEquals(null, dto.getKm().min());
	}
	
	private Kilometers getKilometersForMessage(String message) {
		List<Integer> nums = new LinkedList<>();
		Matcher matcher = NUM_PATTERN.matcher(message);
		while(matcher.find()) {
			nums.add(cleanNumForMessage(matcher.group()));
		}
		
		nums.sort(Comparator.naturalOrder());
		return new Kilometers(nums.getLast(), nums.getFirst());
	}
	
	private Integer cleanNumForMessage(String message) {
		return Integer.valueOf(message.replaceAll("\\D", ""));
	}

}
