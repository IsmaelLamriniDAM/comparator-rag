package comparator.ia.app.service.scrap.vehicle;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;

import comparator.ia.app.dtos.scrapper.request.CochesNetRequestDTO;
import comparator.ia.app.dtos.scrapper.response.cochesnet.CochesNetResponseDTO;
import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.enums.WebName;
import comparator.ia.app.service.alias.brand.BrandAliasService;
import comparator.ia.app.service.alias.model.ModelAliasService;
import comparator.ia.app.service.scrap.configuration.WebClientConfigurable;
import comparator.ia.app.service.vector.VectorService;
import comparator.ia.app.util.strategy.vehicle.api.VehicleScrapperStrategy;
import reactor.util.retry.Retry;

@Service("cochesnet")
public class CochesNetScrapperService implements VehicleScrapperStrategy, WebClientConfigurable {
	private static final String BASE_URL_COCHES_NET = "https://web.gw.coches.net";
	private static final String SEARCH_ENDPOINT = "/search/listing";
	
	private final WebClient client;
	private final Gson gson = new Gson();
	
	private final  VectorService vectorService;
	
	private final BrandAliasService brandAliasService;
	
	private final ModelAliasService modelAliasService;
	
	
	public CochesNetScrapperService(VectorService vectorService, ModelAliasService modelAliasService, BrandAliasService brandAliasService) {
		super();
		this.client = configure();
		this.vectorService = vectorService;
		this.brandAliasService = brandAliasService;
		this.modelAliasService = modelAliasService;
	}
	
	private CochesNetResponseDTO fetchPage(int page) {
		CochesNetRequestDTO req = new CochesNetRequestDTO();
		
		CochesNetRequestDTO.Pagination pagination = new CochesNetRequestDTO.Pagination();
		pagination.setPage(page);
		pagination.setSize(30);
		
		CochesNetRequestDTO.Sort sort = new CochesNetRequestDTO.Sort(); 
		sort.setOrder("desc"); 
		sort.setTerm("relevance");
		
		CochesNetRequestDTO.Filters filters = new CochesNetRequestDTO.Filters();
		CochesNetRequestDTO.Filters.Range nullRange = new CochesNetRequestDTO.Filters.Range();
		nullRange.setFrom(null);
		nullRange.setTo(null);
		
		filters.setPrice(nullRange);
		filters.setBodyTypeIds(List.of());
		filters.setCategories(new CochesNetRequestDTO.Filters.Categories());
		filters.getCategories().setCategory1Ids(List.of(2500));
		filters.setDrivenWheelsIds(List.of());
		filters.setEntry(null);
		filters.setEnvironmentalLabels(List.of());
		filters.setEquipments(List.of());
		filters.setFuelTypeIds(List.of());
		filters.setHasPhoto(false);
		filters.setHasWarranty(false);
		filters.setHp(nullRange);
		filters.setCertified(false);
		filters.setKm(nullRange);
		filters.setLuggageCapacity(nullRange);
		filters.setMaxTerms(null);
		filters.setOnlyPeninsula(false);
		filters.setOfferTypeIds(List.of(0,1,2,3,4,5));
		filters.setProvinceIds(List.of());
		filters.setSearchText("");
		filters.setSellerTypeId(0);
		filters.setTransmissionTypeId(null);
		filters.setYear(nullRange);
		
		req.setPagination(pagination);
		req.setSort(sort);
		req.setFilters(filters);
		
		
		String payload = gson.toJson(req);
		return client.post()
					.uri(SEARCH_ENDPOINT)
					.bodyValue(payload)
					.retrieve()
					.bodyToMono(CochesNetResponseDTO.class)
					.retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
					.block();
	}
	
	private boolean checkValidVehicle(CochesNetResponseDTO.Item v) {
		return v != null && v.getModel() != null && v.getMake() != null &&
				   v.getPrice().getAmount() != null &&
				   v.getPublishedDate() != null && v.getKm() != null
				   && v.getResources() != null && v.getYear() != null 
				   && v.getYear() <= LocalDateTime.now().getYear();
	}
	
	
	private VehicleEntity toVehicle(CochesNetResponseDTO.Item v) {
		VehicleEntity vehicle = new VehicleEntity();
		int i = 0;
		
		vehicle.setBrand(searchBrand(v.getMake().toUpperCase()));
		vehicle.setModel(searchModel(v.getModel().toUpperCase(), vehicle.getBrand()));
		vehicle.setPrice(v.getPrice().getAmount());
		vehicle.setYear(v.getYear().intValue());
		vehicle.setKilometers(v.getKm().intValue());
		
		vehicle.setHorsePower((v.getHp() != null ? v.getHp() : null));
		vehicle.setFuelType(VehicleScrapperStrategy.getFuelType(v.getFuelType()));
		vehicle.setWebName(WebName.COCHESNET);
		vehicle.setWebVehicleUrl("https://www.coches.net" + v.getUrl());
		vehicle.setWebVehicleId(v.getId());
		vehicle.setPublishedDate(Instant.parse(v.getPublishedDate()).atZone(ZoneId.systemDefault()).toLocalDateTime());
		while(!v.getResources().get(i).getType().equals("IMAGE")) {
			i++;
		}
		vehicle.setWebVehicleImg(v.getResources().get(i).getUrl());
		
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
		int totalPages = 1;
		
		while(nextPage <= totalPages) {
			CochesNetResponseDTO json = fetchPage(nextPage);
			totalPages = json.getMeta().getTotalPages();
			
			json.getItems().stream()
				.filter(this::checkValidVehicle)
				.map(this::toVehicle)
				.forEach(v -> {
					if(existstBrandAndModel(v)) {
						vehicles.add(v);
					}
				});
			nextPage++;
		}
		
		return VehicleScrapperStrategy.filter(vehicles);
	}

	@Override
	public WebClient configure() {
		return WebClient.builder()
				.baseUrl(BASE_URL_COCHES_NET)
				.exchangeStrategies(ExchangeStrategies
						  .builder()
						  .codecs(codecs -> codecs
					            .defaultCodecs()
					            .maxInMemorySize(16 * 1024 * 1024))
						    .build())
				.defaultHeaders(h -> {
					h.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/141.0.0.0 Safari/537.36");
					h.add("x-adevinta-channel", "web-desktop" );
					h.add("x-adevinta-page-url", "https://www.coches.net/");
					h.add("x-adevinta-referer", "https://www.coches.net/search/?MakeIds%5B0%5D=4&ModelIds%5B0%5D=0&Versions%5B0%5D=&Section1Id=2500&pg=2");
					h.add("x-adevinta-session-id", "bf757f63-28a1-4378-be1e-bb7888c382b8");
					h.add("x-schibsted-tenant", "coches");
					h.add("content-type", "application/json");
				})
				.build();
	}

}
