package comparator.ia.app.service.scrap.vehicle;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import comparator.ia.app.dtos.scrapper.response.wallapop.WallapopDTO;
import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.enums.WebName;
import comparator.ia.app.service.alias.brand.BrandAliasService;
import comparator.ia.app.service.scrap.configuration.WebClientConfigurable;
import comparator.ia.app.service.vector.VectorService;
import comparator.ia.app.util.strategy.vehicle.api.VehicleScrapperStrategy;
import reactor.util.retry.Retry;

@Service("wallapop")
public class WallapopScrapperService implements VehicleScrapperStrategy, WebClientConfigurable {
	private static final String BASE_URL_WALLAPOP = "https://api.wallapop.com";
	private static final String SEARCH_ENDPOINT = "/api/v3/search?category_id=100&source=seo_landing";
	
	private final WebClient client;

	public WallapopScrapperService() {
		super();
		this.client = configure();
	}
	
	private String buildUrl(String nextPage) {
	    if (nextPage == null || nextPage.isBlank()) {
	        return SEARCH_ENDPOINT;
	    }
	    return SEARCH_ENDPOINT + "&next_page=" + nextPage;
	}
	
	private WallapopDTO fetchPage(String url) {
		  return client.get()
	        		.uri(url)
	        		.retrieve()
	        		.bodyToMono(WallapopDTO.class)
	        		.retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
	        		.block();
	}
	
	private boolean checkValidVehicle(WallapopDTO.Data.Section.Payload.Item v) {
		return v != null && v.getType_attributes() != null && v.getPrice() != null
				&& v.getType_attributes().getModel() != null && v.getType_attributes().getBrand() != null
				&& v.getType_attributes().getYear() != null && v.getType_attributes().getKm() != null;
	}
	
	private VehicleEntity toVehicle(WallapopDTO.Data.Section.Payload.Item v) {
		VehicleEntity vehicle = new VehicleEntity();
		vehicle.setModel(v.getType_attributes().getModel().toLowerCase());
        vehicle.setBrand(v.getType_attributes().getBrand().toLowerCase());
        vehicle.setPrice(v.getPrice().getAmount());
        vehicle.setYear(v.getType_attributes().getYear().intValue());
        vehicle.setKilometers(v.getType_attributes().getKm().intValue());
        
        vehicle.setHorsePower((v.getType_attributes().getHorsepower() != null ?  v.getType_attributes().getHorsepower() : null));
        vehicle.setFuelType(VehicleScrapperStrategy.getFuelType(v.getType_attributes().getEngine()));
        vehicle.setWebName(WebName.WALLAPOP);
        vehicle.setWebVehicleUrl("https://es.wallapop.com/item/" + v.getWeb_slug());
        vehicle.setWebVehicleId(v.getId());
        vehicle.setPublishedDate(Instant.ofEpochMilli(Long.valueOf(v.getCreated_at())).atZone(ZoneId.systemDefault()).toLocalDateTime());
        vehicle.setWebVehicleImg(v.getImages().getFirst().getUrls().getBig());
        return vehicle;
	}

	
	@Override
	public List<VehicleEntity> scrap() {
		List<VehicleEntity> vehicles = new ArrayList<>();
		String nextPage = "";
		boolean hasNextPage = true;
		int count = 0;
		
		while(hasNextPage) {
			String url = buildUrl(nextPage);
			WallapopDTO json = fetchPage(url);
	        nextPage = json.getMeta().getNext_page();
	       
			json.getData().getSection().getPayload().getItems().stream()
					.filter(this::checkValidVehicle)
					.map(this::toVehicle)
					.forEach(vehicles::add);
			
			
			count += vehicles.size() - count;
	
	        if(nextPage == null || nextPage.length() == 0) {
	        	hasNextPage = false;
	        }
		}
		
		return  VehicleScrapperStrategy.filter(vehicles);
	}

	@Override
	public WebClient configure() {
		return WebClient.builder()
				.baseUrl(BASE_URL_WALLAPOP)
				.exchangeStrategies(ExchangeStrategies
						  .builder()
						  .codecs(codecs -> codecs
					            .defaultCodecs()
					            .maxInMemorySize(16 * 1024 * 1024))
						    .build())
				.defaultHeaders(h -> {
					h.add("host", "api.wallapop.com");
					h.add("origin", "https://es.wallapop.com" );
					h.add("referer", "https://es.wallapop.com/");
					h.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537");
					h.add("x-deviceos", "0");
				})
				.build();
	}
	
}