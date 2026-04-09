package epp.tgf.app.scrapper.ocasionPlus;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import comparator.ia.app.dtos.scrapper.response.ocasionplus.OcasionPlusDTO;
import comparator.ia.app.dtos.scrapper.response.ocasionplus.OcasionPlusDTO.Item;
import comparator.ia.app.dtos.scrapper.response.ocasionplus.OcasionPlusDTO.Item.Characteristics;
import comparator.ia.app.dtos.scrapper.response.ocasionplus.OcasionPlusDTO.Item.Images;
import comparator.ia.app.dtos.scrapper.response.ocasionplus.OcasionPlusDTO.Item.Price;
import comparator.ia.app.dtos.scrapper.response.ocasionplus.OcasionPlusDTO.Item.Characteristics.Engine;
import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.enums.WebName;
import comparator.ia.app.service.scrap.vehicle.OcasionPlusScrapperService;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@ExtendWith(MockitoExtension.class)
class OcasionPlusScrapperServiceTest {
	
	private static final String[] BRANDS_EXAMPLES = {"LAND-ROVER", "citroen", "peugeot", null, "bmw"};
	private static final String[] MODELS_EXAMPLES = {"RANGE-ROVER-VELAR", null,  "206plus", "berlingofirst", "serie 7"};
	private static final Double[] PRICES_EXAMPLES = {1000.0, 2000.0,  3000.0, null, 20000.0};
	private static final Long[] KMS_EXAMPLES = {100000L, 200000L,  null, 300000L, 10L};
	private static final String[] REGISTER_DAY_EXAMPLES = {"2023-05-02T00:00:00.000Z", "2023-05-02T00:00:00.000Z"
			, "2023-05-02T00:00:00.000Z", "2023-05-02T00:00:00.000Z", null};
	private static final String[] TYPES_FUEL = {"diesel", "gasoline", "hybrid", "glp", "gnc", "other"};
	
	private static final int NUM_TOTAL_CREATION_ITEMS = BRANDS_EXAMPLES.length;
	private static final int NUM_IMAGES_CREATION = 2;
	
	@InjectMocks
	private OcasionPlusScrapperService serviceOcasion;
	
	@Mock
	private WebClient client;
	
	@Mock
	private RequestHeadersUriSpec requestHeaders;
	
	@Mock
	private ResponseSpec responseSpec;
	
	@Mock
	private Mono<OcasionPlusDTO> mono;
	
	private List<VehicleEntity> vehicleEntitiesScrapper = null;
	
	@BeforeEach
	void setUp() {
		configureMockWebClient();
		vehicleEntitiesScrapper = serviceOcasion.scrap();
	}

	@Test
	void testValidVehiclesEntities() {
		for(VehicleEntity v : vehicleEntitiesScrapper) {
			assertTrue(v.getBrand() != null && !v.getBrand().isBlank()
					&& v.getModel() != null && !v.getModel().isBlank()
					&& v.getPrice() != null && v.getPrice() > 0
					&& v.getYear() != null && v.getYear() < LocalDateTime.now().plusYears(1).getYear()
					&& v.getKilometers() != null && v.getKilometers() >= 0
					&& v.getWebName() == WebName.OCASIONPLUS);
		}
	}
	
	@Test
	@DisplayName("Testea las veces que se ha ejecutado la petición a la web.")
	void testVerifyRequestTimes() {	
		verify(client, times(2)).get();
	}
	
	@Test
	@DisplayName("Comprueba que la marca se haya establecido con el patrón correcto.")
	void testBrandsIsCorrect() {
		for(VehicleEntity entity : vehicleEntitiesScrapper) {
			for(String word: entity.getBrand().split("\\s+")) {
				assertNotEquals(word, word.toLowerCase());
				assertTrue(!word.equals("-"));
			}
		}
	}
	
	@Test
	@DisplayName("Comprueba que el modelo se haya establecido con el patrón correcto.")
	void testModelsIsCorrect() {
		for(VehicleEntity entity : vehicleEntitiesScrapper) {
			for(char letter: entity.getModel().toCharArray()) {
				if(letter > 'A' && letter < 'Z' || letter > 'a' && letter < 'z' ) {
					assertTrue(Character.isUpperCase(letter));
				}
				assertNotEquals('-', letter);
			}
		}
	}
	
	private void configureMockWebClient() {
		OcasionPlusDTO firstDTO = creationDTO();
		OcasionPlusDTO secondDTO = creationWithoutDataDTO();
		
		when(client.get()).thenReturn(requestHeaders);
		when(requestHeaders.uri(anyString())).thenReturn(requestHeaders);
		when(requestHeaders.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToMono(OcasionPlusDTO.class)).thenReturn(mono);
		when(mono.retryWhen(any(Retry.class))).thenReturn(mono);
		when(mono.block()).thenReturn(firstDTO, secondDTO);
		
	}

	private OcasionPlusDTO creationWithoutDataDTO() {
		OcasionPlusDTO dto = new OcasionPlusDTO();
		dto.setData(null);
		return dto;
	}

	private OcasionPlusDTO creationDTO() {
		OcasionPlusDTO dto = new OcasionPlusDTO();
		List<Item> items = new ArrayList<>(creationItems());
		dto.setData(items);
		return dto;
	}

	private List<Item> creationItems() {
		List<Item> items = new ArrayList<>();
		for(int i = 0; i < NUM_TOTAL_CREATION_ITEMS; i++) {
			Item item = new Item();
			item.setBrand(BRANDS_EXAMPLES[i]);
			item.setModel(MODELS_EXAMPLES[i]);
			Price price = new Price();
			price.setCash(PRICES_EXAMPLES[i]);
			item.setPrice(price);
			Characteristics characteristics = new Characteristics();
			characteristics.setKms(KMS_EXAMPLES[i]);
			characteristics.setRegistrationDate(REGISTER_DAY_EXAMPLES[i]);
			Engine engine = new Engine();
			engine.setCv(i*100.0);
			engine.setFuel(TYPES_FUEL[new Random().nextInt(TYPES_FUEL.length)]);
			characteristics.setEngine(engine);
			item.setCharacteristics(characteristics);
			item.setPublicationDate("2026-01-15T00:00:00.000Z");
			item.setImages(creationImages());
			items.add(item);
		}
		return items;
	}

	private List<Images> creationImages() {
		List<Images> images = new ArrayList<OcasionPlusDTO.Item.Images>();
		for(int i = 0; i < NUM_IMAGES_CREATION; i++) {
			Images image = new Images();
			image.setThumb("imagen" + i);
			images.add(image);
		}
		return images;
	}

}
