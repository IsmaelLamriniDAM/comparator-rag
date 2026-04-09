package comparator.ia.app.service.scrap.configuration;

import org.springframework.web.reactive.function.client.WebClient;

public interface WebClientConfigurable {
	WebClient configure();
}
