package epp.tgf.app.scrapper.cochesnet;

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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import comparator.ia.app.dtos.scrapper.response.cochesnet.CochesNetResponseDTO;
import comparator.ia.app.dtos.scrapper.response.cochesnet.CochesNetResponseDTO.Item;
import comparator.ia.app.dtos.scrapper.response.cochesnet.CochesNetResponseDTO.Meta;
import comparator.ia.app.dtos.scrapper.response.cochesnet.CochesNetResponseDTO.Item.Price;
import comparator.ia.app.dtos.scrapper.response.cochesnet.CochesNetResponseDTO.Item.Resources;
import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.enums.WebName;
import comparator.ia.app.service.scrap.vehicle.CochesNetScrapperService;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@ExtendWith(MockitoExtension.class)
class CochesNetScrapperServiceTest {
	
	
	private static final String[] BRANDS_EXAMPLES = {"ALFAROMEO", "citroen", "peugeot", null, "CHRYSLER"};
	private static final String[] MODELS_EXAMPLES = {"156", null,  "206plus", "berlingofirst", "GRAND VOYAGER"};
	private static final Double[] PRICES_EXAMPLES = {1000.0, 2000.0,  3000.0, null, 20000.0};
	private static final Long[] KMS_EXAMPLES = {100000L, 200000L,  null, 300000L, 10L};
	
	private static final int TOTAL_PAGES = 2;
	private static final Integer TOTAL_ITEMS = BRANDS_EXAMPLES.length;

	@InjectMocks
	private CochesNetScrapperService serviceCochesNet;
	
	@Mock
	private WebClient client;
	
	@Mock
	private RequestHeadersUriSpec requestHeaders;
	
	@Mock
	private RequestBodyUriSpec requestBody;
	
	@Mock
	private ResponseSpec responseSpec;
	
	@Mock
	private Mono<CochesNetResponseDTO> mono;
	
	private List<VehicleEntity> vehicleEntitiesScrapper = null;
	
	@BeforeEach
	void setUp() {
		configureMockWebClient();
		vehicleEntitiesScrapper = serviceCochesNet.scrap();
	}
	
	@Test
	void testValidVehiclesEntities() {
		for(VehicleEntity v : vehicleEntitiesScrapper) {
			assertTrue(v.getBrand() != null && !v.getBrand().isBlank()
					&& v.getModel() != null && !v.getModel().isBlank()
					&& v.getPrice() != null && v.getPrice() > 0
					&& v.getYear() != null && v.getYear() < LocalDateTime.now().plusYears(1).getYear()
					&& v.getKilometers() != null && v.getKilometers() >= 0 && v.getWebName() == WebName.COCHESNET);
		}
	}
	
	@Test
	@DisplayName("Testea las veces que se ha ejecutado la petición a la web.")
	void testVerifyRequestTimes() {	
		verify(client, times(2)).post();
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
		CochesNetResponseDTO firstDTO = creationDTO();
		CochesNetResponseDTO secondDTO = creationDTO();
		
		
		when(client.post()).thenReturn(requestBody);
		when(requestBody.uri(anyString())).thenReturn(requestBody);
		when(requestBody.bodyValue(anyString())).thenReturn(requestHeaders);
		when(requestHeaders.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToMono(CochesNetResponseDTO.class)).thenReturn(mono);
		when(mono.retryWhen(any(Retry.class))).thenReturn(mono);
		when(mono.block()).thenReturn(firstDTO, secondDTO);
		
	}

	private CochesNetResponseDTO creationDTO() {
		CochesNetResponseDTO dto = new CochesNetResponseDTO();
		dto.setItems(creationItems());
		Meta meta = new Meta();
		meta.setTotalPages(TOTAL_PAGES);
		dto.setMeta(meta);
		return dto;
	}

	private List<Item> creationItems() {
		List<Item> items = new ArrayList<CochesNetResponseDTO.Item>();
		for(int i = 0; i < TOTAL_ITEMS; i++) {
			Item item = new Item();
			item.setKm(KMS_EXAMPLES[i]);
			item.setMake(BRANDS_EXAMPLES[i]);
			item.setModel(MODELS_EXAMPLES[i]);
			Price price = new Price();
			price.setAmount(PRICES_EXAMPLES[i]);
			item.setPrice(price);
			if(i == 1) {
				item.setYear(null);
			} else {
				item.setYear((i+1)*1000l);
			}
			item.setResources(creationResources());
			item.setPublishedDate("2023-05-02T00:00:00.000Z");
			items.add(item);
		}
		
		return items;
	}

	private List<Resources> creationResources() {
		List<Resources> resources = new ArrayList<CochesNetResponseDTO.Item.Resources>();
		for(int i = 0; i < 2; i++) {
			Resources resource = new Resources();
			if(i==0) {
				resource.setType("OTROTIPO");
				resource.setUrl("image" + i);
			} else {
				resource.setUrl("image" + i);
				resource.setType("IMAGE");
			}
			resources.add(resource);
		}
		return resources;
	}

}
