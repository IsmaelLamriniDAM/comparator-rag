package comparator.ia.app.service.wikipedia.infoBrand;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import comparator.ia.app.dtos.wiki.infoBrand.WikiInfoBrandDto;
import reactor.util.retry.Retry;

@Service
public class WikipediaServiceImp implements WikipediaService {
	
	private static final Logger logger = LoggerFactory.getLogger(WikipediaServiceImp.class);
	
	private static final String URL_SEARCH_INFO = "https://es.wikipedia.org/w/rest.php/v1/search/page?q=";
	
	private static final String LIMIT_RESULTS = "&limit=1";
	
	private final WebClient webClient;
	
	WikipediaServiceImp(){
		webClient = WebClient
				.builder()
				.defaultHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36")
				.build();
	}

	@Override
	public String getDescriptionWikiBrand(String name) {
		logger.info("Search info Wiki by this name: {}", name);
		
		WikiInfoBrandDto wikiInfo = webClient
				.get()
				.uri(URL_SEARCH_INFO + "marca de coche llamado " + name.toLowerCase() + LIMIT_RESULTS)
				.retrieve()
				.bodyToMono(WikiInfoBrandDto.class)
				.retryWhen(Retry.backoff(5, Duration.ofSeconds(2))
						 .maxBackoff(Duration.ofSeconds(30))
			                .filter(ex ->
			                    ex instanceof WebClientResponseException.TooManyRequests
			                ))
				.block();
		
		if(wikiInfo == null || wikiInfo.getPages() == null || wikiInfo.getPages().isEmpty() 
				|| wikiInfo.getPages().getFirst().getDescription() == null || wikiInfo.getPages().getFirst().getDescription().isEmpty()) {
			logger.warn("Not found description for this name: {}", name);
			return "";
			}
		
		String description = wikiInfo.getPages().getFirst().getDescription();
		logger.info("Searched info brand succesfully: {}", description);
		return description;
	}

}
