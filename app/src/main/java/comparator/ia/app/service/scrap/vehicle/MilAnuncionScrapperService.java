package comparator.ia.app.service.scrap.vehicle;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import comparator.ia.app.dtos.scrapper.response.milanuncios.CochesMilAnunciosDTO;
import comparator.ia.app.dtos.scrapper.response.milanuncios.CochesMilAnunciosDTO.Ads;
import comparator.ia.app.dtos.scrapper.response.milanuncios.CochesMilAnunciosDTO.Photos;
import comparator.ia.app.dtos.scrapper.response.milanuncios.CochesMilAnunciosDTO.Ads.Attributes;
import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.enums.WebName;
import comparator.ia.app.service.alias.brand.BrandAliasService;
import comparator.ia.app.service.alias.model.ModelAliasService;
import comparator.ia.app.service.scrap.configuration.WebClientConfigurable;
import comparator.ia.app.service.vector.VectorService;
import comparator.ia.app.util.strategy.vehicle.api.VehicleScrapperStrategy;
import reactor.util.retry.Retry;

@Service
public class MilAnuncionScrapperService implements VehicleScrapperStrategy, WebClientConfigurable {
	
	private static final String BASE_URL_ADS_MILANUNCIOS = "https://searchapi.gw.milanuncios.com";
	private static final String BASE_URL_MILANUNCIOS = "https://www.milanuncios.com";
	private static final String URL_WEB_NOT_TOKEN = "https://searchapi.gw.milanuncios.com/v3/classifieds?category=13&sort=random&transaction=supply&publicationType=lastWeek&sellerType=private&priceFrom=1";
	private static final String URL_WEB_TOKEN =  "https://searchapi.gw.milanuncios.com/v3/classifieds?category=13&sort=random&transaction=supply&publicationType=lastWeek&sellerType=private&priceFrom=1&nextToken=";
	private static final String RULE_IMAGE_MILANUNCIOS = "?rule=hw396_70";
	private static final String IMAGE_DEFAULT_MILANUNCIOS = "https://www.reactivaonline.com/wp-content/uploads/2021/10/milanuncios-ecommerce-1160x812.jpg";
	
	private static final Logger logger = LoggerFactory.getLogger(MilAnuncionScrapperService.class);
	
	private final WebClient webClient;
	
	private final  VectorService vectorService;
	
	private final BrandAliasService brandAliasService;
	
	private final ModelAliasService modelAliasService;
	
	public MilAnuncionScrapperService(VectorService vectorService, BrandAliasService brandAliasService, ModelAliasService modelAliasService) {
		this.webClient = configure();
		this.vectorService = vectorService;
		this.brandAliasService = brandAliasService;
		this.modelAliasService = modelAliasService;
	}
	
	private List<VehicleEntity> createdVehicle(List<Ads> ads, List<Photos> photos) {
		List<VehicleEntity> vehiclesList = new ArrayList<VehicleEntity>();
		
		for(Ads ad : ads) {
			VehicleEntity vehicle = new VehicleEntity();
			
			Map<String, String> map = new HashMap<String, String>();
			for(Attributes atributte : ad.getAttributes()) {
				map.put(atributte.getField().getRaw(), atributte.getValue().getRaw());
			}
			
			List<String> brand = ad.getCategories().stream().filter(c -> c.getName() != null).map(m -> m.getName().toUpperCase()).map(n -> n.replaceAll("-+", " ")).collect(Collectors.toCollection(LinkedList::new));
			setBrand(vehicle, brand);
			
			setModel(ad, vehicle);
			
			vehicle.setPrice(ad.getPrice().getCash().getValue());
			vehicle.setKilometers(Integer.valueOf(map.getOrDefault("kilometers", "0")));
			vehicle.setYear(Integer.valueOf(map.getOrDefault("year", "0")));
			vehicle.setFuelType(VehicleScrapperStrategy.getFuelType(map.getOrDefault("fuel", "other")));
			vehicle.setHorsePower(Double.valueOf(map.getOrDefault("hp", "0")));
			vehicle.setPublishedDate(LocalDateTime.parse(ad.getPublicationDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
			vehicle.setWebVehicleId(ad.getId());
			vehicle.setWebVehicleUrl(BASE_URL_MILANUNCIOS + ad.getUrl());
			vehicle.setWebName(WebName.MILANUNCIOS);
			Optional<String> possibleImg = putImgInVehicle(vehicle.getWebVehicleId(), photos);
			if(possibleImg.isPresent()) {
				vehicle.setWebVehicleImg("https://" + possibleImg.get() + RULE_IMAGE_MILANUNCIOS);
			} else {
				vehicle.setWebVehicleImg(IMAGE_DEFAULT_MILANUNCIOS);
			}
			
			vehiclesList.add(vehicle);
		}
		
		return vehiclesList;
	}

	private void setModel(Ads ad, VehicleEntity vehicle) {
		if(vehicle.getBrand() != null) {
			String possibleModel = searchModelUrl(ad.getUrl(), vehicle.getBrand()).toUpperCase();
			String model = vectorService.getModelVector(possibleModel, vehicle.getBrand().toUpperCase());
			if(model.isBlank()) {
				vehicle.setModel("MODELO");
				logger.warn("MODELO QUE NO SE HA ENCONTRADO: {} y pertenece a esta marca: {}", possibleModel, vehicle.getBrand());
			} else {
				vehicle.setModel(model);
				modelAliasService.saveAlias(model, possibleModel, vehicle.getBrand().toUpperCase());
			}
		}
	}

	private void setBrand(VehicleEntity vehicle, List<String> brand) {
		if(!brand.isEmpty()) {
			String possibleBrand = vectorService.getBrandVector(brand.getLast().toUpperCase());
			if(possibleBrand.isBlank()) {
				vehicle.setBrand(null);
			} else {
				vehicle.setBrand(possibleBrand);
				brandAliasService.saveAlias(possibleBrand, brand.getLast().toUpperCase());
			}
		}
	}
	
	private String searchModelUrl(String url, String brand) {
		if(brand == null) return "";
		String[] urlSplit = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("-")).split("-");
		StringBuilder brandSb = new StringBuilder();
		for(int i = 0; i < urlSplit.length; i++) {
			brandSb.append(urlSplit[i]);
			if(brand.equalsIgnoreCase(brandSb.toString())) {
				return Arrays.stream(urlSplit).skip(i + 1).collect(Collectors.joining(" ")).toUpperCase();
			}
			brandSb.append(" ");
		}
		return "";
	}

	private boolean checkVehicle(VehicleEntity v) {
	    return v.getBrand() != null && !v.getBrand().isBlank() 
	            && v.getModel() != null && !v.getModel().isBlank() 
	            && v.getPrice() != null && v.getPrice() > 0
	            && v.getYear() != null && v.getYear() <= LocalDateTime.now().getYear()
	            && v.getKilometers() != null && v.getKilometers() >= 0
	            && v.getHorsePower() != null && v.getHorsePower() > 0;
	}
	
	private Optional<String> putImgInVehicle(String webVehicleId, List<Photos> photos) {
		if(photos == null || photos.isEmpty()) return Optional.of(IMAGE_DEFAULT_MILANUNCIOS);
		return photos.stream()
		.filter(p -> p.getAdId().equals(webVehicleId))
		.map(
				p -> p.getImageUrls()
				.stream()
				.filter(i -> i != null)
				.findAny()
		).findFirst().get();
	}

	private CochesMilAnunciosDTO getResponse(Optional<String> token) {
		if (!token.isPresent()) {
			return webClient.get().uri(URL_WEB_NOT_TOKEN).accept(MediaType.APPLICATION_JSON).retrieve()
					.bodyToMono(CochesMilAnunciosDTO.class).retryWhen(Retry.backoff(3, Duration.ofSeconds(2))).block();
		}
		return webClient.get().uri(URL_WEB_TOKEN + token.get()).accept(MediaType.APPLICATION_JSON).retrieve()
		.bodyToMono(CochesMilAnunciosDTO.class).retryWhen(Retry.backoff(3, Duration.ofSeconds(2))).block();
	}
	
	@Override
	public List<VehicleEntity> scrap() {
		int countScrapperCar = 0;
		int totalVehiclesInWeb = 0;
		Optional<String> token = Optional.empty();
		boolean hasNextToken = true;
		List<VehicleEntity> listVehiclesEntities = new ArrayList<>();
		
		while(countScrapperCar <= totalVehiclesInWeb && hasNextToken) {
			
			CochesMilAnunciosDTO response = getResponse(token);
			
			List<VehicleEntity> listVehiclesScrappNow = createdVehicle(response.getAds(), response.getPhotos());
			listVehiclesScrappNow.stream().filter(this::checkVehicle).forEach(listVehiclesEntities::add);
		
			if(!token.isPresent()) {
				totalVehiclesInWeb = Integer.valueOf(response.getPagination().getTotalHits().getValue());
			} 
			
			token = Optional.ofNullable(response
					.getPagination()
					.getNextToken());
			
			if(token.isPresent()) {
				hasNextToken = true;
			} else {
				hasNextToken = false;
			}
			
			countScrapperCar += listVehiclesScrappNow.size();
		}
		
		return VehicleScrapperStrategy.filter(listVehiclesEntities);
	}

	@Override
	public WebClient configure() {
		return WebClient.builder()
				.baseUrl(BASE_URL_ADS_MILANUNCIOS)
				.exchangeStrategies(ExchangeStrategies
						  .builder()
						  .codecs(codecs -> codecs
					            .defaultCodecs()
					            .maxInMemorySize(16 * 1024 * 1024))
						    .build())
				.defaultHeaders(h -> {
				    h.add("accept", "application/json, text/plain, */*");
				    h.add("referer", "https://www.milanuncios.com/");
				    h.add("sec-ch-ua", "\"Google Chrome\";v=\"143\", \"Chromium\";v=\"143\", \"Not A(Brand\";v=\"24\"");
				    h.add("sec-ch-ua-mobile", "?1");
				    h.add("sec-ch-ua-platform", "\"Android\"");
				    h.add("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Mobile Safari/537.36");
				    h.add("x-adevinta-amcvid", "34707364894736631824574564460101588357");
				    h.add("x-adevinta-channel", "web-desktop");
				    h.add("x-adevinta-euconsent-v2", "CQc_PEAQc_PEAAHABBENCKFsAP_gAEPgAAiQL3tR_G__bWlr-bb3aftkeYxP9_hr7sQxBgbJk24FzLvW7JwXx2E5NAzatqIKmRIAu3TBIQNlHJDURVCgKIgFryDMaEyUoTNKJ6BkiFMZI2NYCFxvm4tjWQCY4vr99lc1mB-N7dr82dzyy6hHn3a5_2S1UJCdIYetDfv8ZBKT-9IEd_x8v4v4_F7pE2-eS1n_pGvp4j9-YnM_dBmxt-TSff7Pn__rl_e7X_vc_n37v94XH77v_-__f_-7___2b_-4L2AAmGhUQRlkQIBAoGEECABQVhABQIAgAASBogIATBgU5AwAXWEyAEAKAAYIAQAAgwABAAAJAAhEAFABAIAQIBAoAAwAIAgIACBgADABYiAQAAgOgYpgQQCBYAJGZVBpgSgAJBAS2VCCUDAgrhCEWeAQQIiYKAAAEgAoCAAB4LAQkkBKxIIAuIJoAACAAAKIESBFIWYAgqDNFoCwJOAyNMAwfMEySnQZIEwQkJJsQm9CYeKQogQQ5AbFLMAdPEFAAAAA.f_wACHwAAAAA");
				    h.add("x-adevinta-session-id", "f5fbe823-4fd7-487b-b589-ed02ea39afd4");
				    h.add("x-adevinta-skip-csrf", "true");
				    h.add("x-ma-client", "DESKTOP_WEB");
				})
				.build();
	}

}
