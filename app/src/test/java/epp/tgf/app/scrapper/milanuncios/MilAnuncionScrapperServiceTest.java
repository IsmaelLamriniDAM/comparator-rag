package epp.tgf.app.scrapper.milanuncios;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import comparator.ia.app.dtos.scrapper.response.milanuncios.CochesMilAnunciosDTO;
import comparator.ia.app.dtos.scrapper.response.milanuncios.CochesMilAnunciosDTO.Ads;
import comparator.ia.app.dtos.scrapper.response.milanuncios.CochesMilAnunciosDTO.Pagination;
import comparator.ia.app.dtos.scrapper.response.milanuncios.CochesMilAnunciosDTO.Photos;
import comparator.ia.app.dtos.scrapper.response.milanuncios.CochesMilAnunciosDTO.Ads.Attributes;
import comparator.ia.app.dtos.scrapper.response.milanuncios.CochesMilAnunciosDTO.Ads.Category;
import comparator.ia.app.dtos.scrapper.response.milanuncios.CochesMilAnunciosDTO.Ads.Price;
import comparator.ia.app.dtos.scrapper.response.milanuncios.CochesMilAnunciosDTO.Ads.Attributes.Field;
import comparator.ia.app.dtos.scrapper.response.milanuncios.CochesMilAnunciosDTO.Ads.Attributes.Value;
import comparator.ia.app.dtos.scrapper.response.milanuncios.CochesMilAnunciosDTO.Ads.Price.Cash;
import comparator.ia.app.dtos.scrapper.response.milanuncios.CochesMilAnunciosDTO.Pagination.TotalHits;
import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.enums.WebName;
import comparator.ia.app.repository.VehicleRepository;
import comparator.ia.app.service.scrap.vehicle.MilAnuncionScrapperService;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@ExtendWith(MockitoExtension.class)
class MilAnuncionScrapperServiceTest {
	
	private static final String URL_WEB_NOT_TOKEN = "https://searchapi.gw.milanuncios.com/v3/classifieds?category=13&sort=random&transaction=supply&publicationType=lastWeek&sellerType=private&priceFrom=1";
	private static final String URL_WEB_TOKEN =  "https://searchapi.gw.milanuncios.com/v3/classifieds?category=13&sort=random&transaction=supply&publicationType=lastWeek&sellerType=private&priceFrom=1&nextToken=";
	private static final String TOKEN_NEXT_PAGINATION = "token";
	
	
	private static final String[] ID_ADS = {"11111", "22222", "33333", "44444"}; 
	private static final String[] BRANDS_EXAMPLES = {"MERCEDES BENZ", "citroen", "peugeot", null};
	private static final String[] MODELS_EXAMPLES = {"CLASE A", null,  "206plus", "berlingofirst"};
	private static final String[] TYPES_FIELD = {"kilometers", "year", "fuel", "hp"};
	private static final String[] TYPES_FUEL = {"diesel", "gasoline", "hybrid", "glp", "gnc", "other"};
	
	
	private static final Integer TOTAL_HITS =  (ID_ADS.length + 1) * (ID_ADS.length + 1);
	private static final Integer NUMBERS_ADS_CREATION = ID_ADS.length + 1;
	private static final int NUMBERS_IMAGE_URLS = 3;
	
	@InjectMocks
	private MilAnuncionScrapperService scrapperMilAnunciosService;
	
	@Mock
	private VehicleRepository vehicleRepository;

	@Mock
	private WebClient webClient;
	
	@Mock
	private RequestHeadersUriSpec request;
	
	@Mock
	private ResponseSpec responseSpec; 
	
	@Mock
	private Mono<CochesMilAnunciosDTO> mono;
	
	private List<VehicleEntity> listActualVehiclesScrapper = null;
	
	@BeforeEach
	void setUp() {
		configureWebClientMock();
		listActualVehiclesScrapper = scrapperMilAnunciosService.scrap();
	}
	
	private void configureWebClientMock() {
		CochesMilAnunciosDTO milAnunciosFirstDTO = creationDTO();
		CochesMilAnunciosDTO milAnunciosSecondDTO = creationDTO();
		CochesMilAnunciosDTO milAnunciosDTOWithoutToken =  quitarToken(creationDTO());
		when(webClient.get()).thenReturn(request);
		when(request.uri(URL_WEB_NOT_TOKEN)).thenReturn(request);
		when(request.uri(URL_WEB_TOKEN + TOKEN_NEXT_PAGINATION)).thenReturn(request);
		when(request.accept(MediaType.APPLICATION_JSON)).thenReturn(request);
		when(request.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToMono(CochesMilAnunciosDTO.class)).thenReturn(mono);
		when(mono.retryWhen(any(Retry.class))).thenReturn(mono);
		when(mono.block()).thenReturn(milAnunciosFirstDTO, milAnunciosSecondDTO, milAnunciosDTOWithoutToken);
	}
	
	@Test
	@DisplayName("Comprueba los campos que no deban ser nulos de VehicleEntity.")
	void testValidVehiclesEntities() {
		for(VehicleEntity ve : listActualVehiclesScrapper) {
			assertTrue(ve.getBrand() != null &&
					!ve.getBrand().isBlank()
					&& ve.getModel() != null
					&& !ve.getModel().isBlank()
					&& ve.getKilometers() != null
					&& ve.getKilometers() > 0 
					&& ve.getYear() != null
					&& ve.getYear() < LocalDateTime.now().plusYears(1).getYear()
					&& ve.getPrice() != null
					&& ve.getPrice() > 0
					&& ve.getWebName() == WebName.MILANUNCIOS);
		}
	}
	
	@Test
	@DisplayName("Verifica que las url se haya usado en el orden correcto.")
	void testVerifyUrlRequestOrder() {
		InOrder inOrder = inOrder(request);
		inOrder.verify(request, times(1)).uri(URL_WEB_NOT_TOKEN);
		inOrder.verify(request, atLeastOnce()).uri(URL_WEB_TOKEN + TOKEN_NEXT_PAGINATION);
	}
	
	@Test
	@DisplayName("Testea las veces que se ha ejecutado la petición a la web.")
	void testVerifyRequestTimes() {	
		verify(webClient, times(3)).get();
	}
	
	@Test
	@DisplayName("Comprueba que la marca se haya establecido con el patrón correcto.")
	void testBrandsIsCorrect() {
		for(VehicleEntity entity : listActualVehiclesScrapper) {
			for(String word: entity.getBrand().split("\\s+")) {
				assertNotEquals(word, word.toLowerCase());
				assertTrue(!word.equals("-"));
			}
		}
	}
	
	@Test
	@DisplayName("Comprueba que el modelo se haya establecido con el patrón correcto.")
	void testModelsIsCorrect() {
		for(VehicleEntity entity : listActualVehiclesScrapper) {
			for(char letter: entity.getModel().toCharArray()) {
				if(letter > 'A' && letter < 'Z' || letter > 'a' && letter < 'z' ) {
					assertTrue(Character.isUpperCase(letter));
				}
				assertNotEquals('-', letter);
			}
		}
	}

	/* ----- Creación de los DTOS   ----- */
	
	private CochesMilAnunciosDTO creationDTO() {
		CochesMilAnunciosDTO milAnunciosWithouthToken = new CochesMilAnunciosDTO();
		List<Ads> myListAds = new ArrayList<CochesMilAnunciosDTO.Ads>(creationListAds());
		milAnunciosWithouthToken.setAds(myListAds);
		milAnunciosWithouthToken.setPagination(creationPagination());
		milAnunciosWithouthToken.setPhotos(creationListPhotos(myListAds));
		return milAnunciosWithouthToken;
	}
	
	private List<Photos> creationListPhotos(List<Ads> ads) {
		List<Photos> listPhotos = new ArrayList<CochesMilAnunciosDTO.Photos>();
		for(Ads ad : ads) {
			Photos photo = new Photos();
			photo.setAdId(ad.getId());
			photo.setImageUrls(creationListUrlImage());
			listPhotos.add(photo);
		}
		return listPhotos;
	}

	private List<String> creationListUrlImage() {
		List<String> urlsimages = new ArrayList<String>();
		for(int i = 0; i < NUMBERS_IMAGE_URLS; i++) {
			urlsimages.add("urlImage" + i);
		}
		return urlsimages;
	}

	private Pagination creationPagination() {
		Pagination pagination = new Pagination();
		pagination.setNextToken(TOKEN_NEXT_PAGINATION);
		TotalHits totalHits = new TotalHits();
		totalHits.setValue(String.valueOf(TOTAL_HITS));
		pagination.setTotalHits(totalHits);
		return pagination;
	}

	private List<Ads> creationListAds() {
		List<Ads> myListAds = new ArrayList<>();
		for(int i = 0; i < NUMBERS_ADS_CREATION; i++) {
			Ads ad = new Ads();
			ad.setId(ID_ADS[i%ID_ADS.length]);
			Cash cash = new Cash();
			cash.setValue((i+1)*1000.0);
			Price price = new Price();
			price.setCash(cash);
			ad.setPrice(price);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
			ad.setPublicationDate(LocalDateTime.now().minusDays(i+1).format(dtf).toString());
			ad.setUpdateDate(LocalDateTime.now().toString());
			ad.setUrl("/audi-de-segunda-mano/" + BRANDS_EXAMPLES[i%BRANDS_EXAMPLES.length] + "-" + MODELS_EXAMPLES[i%MODELS_EXAMPLES.length] + "-" + ID_ADS[i%ID_ADS.length] + ".htm");
			ad.setTitle(BRANDS_EXAMPLES[i%BRANDS_EXAMPLES.length] + "-" +  MODELS_EXAMPLES[i%MODELS_EXAMPLES.length]);
			ad.setAttributess(creationAttributesLists());
			ad.setCategories(creationCategoriesList(i));
			myListAds.add(ad);
		}
		return myListAds;
	}

	private List<Category> creationCategoriesList(int index) {
		List<Category> listCategories = new ArrayList<CochesMilAnunciosDTO.Ads.Category>();
		Category categoryPositionOne = new Category();
		categoryPositionOne.setName("1");
		listCategories.add(categoryPositionOne);
		Category categoryPositionTwo = new Category();
		categoryPositionTwo.setName("2");
		listCategories.add(categoryPositionTwo);
		Category categoryPositionThree = new Category();
		categoryPositionThree.setName(BRANDS_EXAMPLES[index%BRANDS_EXAMPLES.length]);
		listCategories.add(categoryPositionThree);
		return listCategories;
	}

	private List<Attributes> creationAttributesLists() {
		List<Attributes> listAttributes = new ArrayList<CochesMilAnunciosDTO.Ads.Attributes>();
		for(int i = 0; i < TYPES_FIELD.length; i++) {
			Attributes attribute = new Attributes();
			Field field = new Field();
			Value value = new Value();
			switch (TYPES_FIELD[i]) {
				case "kilometers":
					field.setRaw("kilometers");
					attribute.setFiel(field);
					value.setRaw(String.valueOf((i+1) * 100000));
					attribute.setValue(value);
					break;
				case "year":
					field.setRaw("year");
					attribute.setFiel(field);
					value.setRaw(String.valueOf(LocalDateTime.now().minusYears(i+1).getYear()));
					attribute.setValue(value);
					break;
				case "fuel":
					field.setRaw("fuel");
					attribute.setFiel(field);
					value.setRaw(TYPES_FUEL[new Random().nextInt(TYPES_FUEL.length)]);
					attribute.setValue(value);
					break;
				case "hp":
					field.setRaw("hp");
					attribute.setFiel(field);
					value.setRaw(String.valueOf((i + 1) * 100));
					attribute.setValue(value);
					break;
			}
			listAttributes.add(attribute);
		}
		return listAttributes;
	}

	private CochesMilAnunciosDTO quitarToken(CochesMilAnunciosDTO milAnunciosDTO) {
		milAnunciosDTO.getPagination().setNextToken(null);
		return milAnunciosDTO;
	}

}