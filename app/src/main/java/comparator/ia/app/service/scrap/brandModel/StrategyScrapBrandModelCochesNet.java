package comparator.ia.app.service.scrap.brandModel;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import comparator.ia.app.dtos.brand.BrandModel;
import comparator.ia.app.dtos.scrapper.response.brandModel.cochesNet.ResponseBrandsCochesNet;
import comparator.ia.app.dtos.scrapper.response.brandModel.cochesNet.ResponseModelsCochesNet;
import comparator.ia.app.dtos.scrapper.response.brandModel.cochesNet.ResponseBrandsCochesNet.DataBrand;
import comparator.ia.app.dtos.scrapper.response.brandModel.cochesNet.ResponseModelsCochesNet.InfoModel;
import comparator.ia.app.service.scrap.configuration.WebClientConfigurable;
import comparator.ia.app.util.singleton.Normalizare;
import comparator.ia.app.util.strategy.brandModel.StrategyScrapBrandModel;

@Service
public class StrategyScrapBrandModelCochesNet implements StrategyScrapBrandModel, WebClientConfigurable{
	
	private static final String BASE_URL = "https://web.gw.coches.net/vehicle-specs/v1/";
	
	private final WebClient webClient;
	
	public StrategyScrapBrandModelCochesNet(){
		this.webClient = configure();
	}
	

	@Override
	public Set<BrandModel> scrapBrandModel() {
		Set<BrandModel> brandModels = new HashSet<>();
		
		ResponseBrandsCochesNet responseBrand = webClient.get().uri("makes?section1Id=2500").retrieve().bodyToMono(ResponseBrandsCochesNet.class).block();
		if(responseBrand != null && responseBrand.getItems() != null && !responseBrand.getItems().isEmpty()) {
			for(DataBrand data : responseBrand.getItems()) {
				Set<String> models = new HashSet<>();
				ResponseModelsCochesNet responseModel =  webClient.get().uri("models/form?section1Id=2500&makeId=" + data.getMakeId()).retrieve().bodyToMono(ResponseModelsCochesNet.class).block();
				
				if(responseModel != null && responseModel.getDatalist() != null && !responseModel.getDatalist().isEmpty()) {
					for(InfoModel info : responseModel.getDatalist()) {
						models.add(Normalizare.getInstance().normalizater(info.getText()));
					}
				}
				
				brandModels.add(new BrandModel(Normalizare.getInstance().normalizater(data.getName()),  models));
			}
		}
		return brandModels;
	}

	@Override
	public WebClient configure() {
		return WebClient.builder()
				.baseUrl(BASE_URL)
				.exchangeStrategies(ExchangeStrategies
						  .builder()
						  .codecs(codecs -> codecs
					            .defaultCodecs()
					            .maxInMemorySize(16 * 1024 * 1024))
						    .build())
				.defaultHeaders(h -> {
					 h.add("accept", "application/json");
		             h.add("accept-language", "es-ES,es;q=0.9");
		             h.add("user-agent",
		                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36");
		             h.add("origin", "https://www.coches.net");
		             h.add("referer", "https://www.coches.net/");
		             h.add("x-schibsted-tenant", "coches");
				})
				.build();
	}

}
