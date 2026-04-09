package comparator.ia.app.service.scrap.vehicle;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import comparator.ia.app.dtos.scrapper.response.ocasionplus.OcasionPlusDTO;
import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.enums.WebName;
import comparator.ia.app.service.alias.brand.BrandAliasService;
import comparator.ia.app.service.alias.model.ModelAliasService;
import comparator.ia.app.service.scrap.configuration.WebClientConfigurable;
import comparator.ia.app.service.vector.VectorService;
import comparator.ia.app.util.strategy.vehicle.api.VehicleScrapperStrategy;
import reactor.util.retry.Retry;
@Service
public class OcasionPlusScrapperService implements VehicleScrapperStrategy, WebClientConfigurable {
	private static final String BASE_URL_OCASION_PLUS = "https://zeus.ocasionplus.com";
	private static final String SEARCH_ENDPOINT = "/vehicles/search/CAR?page=";
	
	private final WebClient client;
	
	private final  VectorService vectorService;
	
	private final BrandAliasService brandAliasService;
	
	private final ModelAliasService modelAliasService;
	
	public OcasionPlusScrapperService(VectorService vectorService, ModelAliasService modelAliasService, BrandAliasService brandAliasService) {
		super();
		this.client = configure();
		this.vectorService = vectorService;
		this.brandAliasService = brandAliasService;
		this.modelAliasService = modelAliasService;
	}

	private String buildUrl(int nextPage) {
	    return SEARCH_ENDPOINT + nextPage;
	}
	
	private OcasionPlusDTO fetchPage(String url) {
		return client.get()
				.uri(url)
				.retrieve()
				.bodyToMono(OcasionPlusDTO.class)
				.retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
				.block();
	}
	
	private boolean checkValidVehicle(OcasionPlusDTO.Item v) {
		return v != null && v.getModel() != null && v.getBrand() != null
				&& v.getCharacteristics().getRegistrationDate() != null && v.getPrice().getCash() != null
				&& v.getCharacteristics().getKms() != null;
	}
	
	private VehicleEntity toVehicle(OcasionPlusDTO.Item v) {
		VehicleEntity vehicle = new VehicleEntity();
		vehicle.setBrand(searchBrand(v.getBrand().toUpperCase()));
		vehicle.setModel(searchModel(v.getModel().toUpperCase(), vehicle.getBrand()));
		vehicle.setPrice(v.getPrice().getCash());
		vehicle.setYear(Integer.valueOf(v.getCharacteristics().getRegistrationDate().split("-")[0]));
		vehicle.setKilometers(v.getCharacteristics().getKms().intValue());
		
		vehicle.setHorsePower((v.getCharacteristics().getEngine().getCv() != null ? v.getCharacteristics().getEngine().getCv() : null));
		vehicle.setFuelType(VehicleScrapperStrategy.getFuelType(v.getCharacteristics().getEngine().getFuel()));
		vehicle.setWebName(WebName.OCASIONPLUS);
		vehicle.setWebVehicleUrl("https://www.ocasionplus.com/coches-segunda-mano/" + v.getSlug());
		vehicle.setWebVehicleId(v.getId());
		vehicle.setPublishedDate(Instant.parse(v.getPublicationDate()).atZone(ZoneId.systemDefault()).toLocalDateTime());
		vehicle.setWebVehicleImg(v.getImages().getFirst().getThumb());
		
		return vehicle;
	}
	
	private String searchBrand (String brand) {
		if( brand == null || brand.isBlank()) {
			return null;
		}
		String possibleBrand = vectorService.getBrandVector(brand);
		if(possibleBrand == null || possibleBrand.isBlank()) {
			return null;
		}
		
		brandAliasService.saveAlias(possibleBrand, brand);
		return possibleBrand.toUpperCase();
	}
	
	private String searchModel(String model, String brand) {
		if(model == null || model.isBlank() || brand == null || brand.isBlank()) {
			return null;
		}
		
		String possibleModel = vectorService.getModelVector(model, brand);
		
		if(possibleModel == null || possibleModel.isBlank()) {
			return null;
		}
		
		modelAliasService.saveAlias(possibleModel, model, brand);
		return possibleModel.toUpperCase();
	}
	
	private boolean existstBrandAndModel(VehicleEntity v) {
		return v.getBrand() != null && ! v.getBrand().isBlank() &&  v.getModel() != null && !v.getModel().isBlank(); 
	}


	@Override
	public List<VehicleEntity> scrap() {
		List<VehicleEntity> vehicles = new ArrayList<>();
		int nextPage = 1;
		boolean hasNextPage = true;

		
		while(hasNextPage) {
			String url = buildUrl(nextPage);
	        OcasionPlusDTO json = fetchPage(url);
	        
	        if (json == null || json.data == null || json.data.isEmpty()) {
	            hasNextPage = false; 
	        } else {
				json.data.stream()
					.filter(this::checkValidVehicle)
					.map(this::toVehicle)
					.filter(this::existstBrandAndModel)
					.forEach(vehicles::add);
	        }
	        nextPage++; 
		}
		
		
		return VehicleScrapperStrategy.filter(vehicles);
	}

	@Override
	public WebClient configure() {
		return WebClient.builder()
				.baseUrl(BASE_URL_OCASION_PLUS)
				.exchangeStrategies(ExchangeStrategies
						  .builder()
						  .codecs(codecs -> codecs
					            .defaultCodecs()
					            .maxInMemorySize(16 * 1024 * 1024))
						    .build())
				.defaultHeaders(h -> {
					h.add("origin", "https://www.ocasionplus.com" );
					h.add("referer", "https://www.ocasionplus.com/");
					h.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36");
				})
				.build();
	}

}
