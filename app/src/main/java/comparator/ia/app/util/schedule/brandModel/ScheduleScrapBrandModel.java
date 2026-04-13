package comparator.ia.app.util.schedule.brandModel;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import comparator.ia.app.util.factory.brandModel.ScrapperBrandModelFactory;
import comparator.ia.app.util.strategy.brandModel.StrategyScrapBrandModel;

@EnableScheduling
@Configuration
public class ScheduleScrapBrandModel {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleScrapBrandModel.class);

	private final ScrapperBrandModel scrapper;
	
	ScheduleScrapBrandModel(ScrapperBrandModel scrapper) {
		this.scrapper = scrapper;
	}
	
	// "0 0 5 * 1,6 1"
	@Scheduled(cron = "0 0 5 * 1,6 1")
	private void scrapping() {
		logger.info("Iniciando el scrapper de marcas y modelos a las {}", LocalDateTime.now());
		StrategyScrapBrandModel strategy = ScrapperBrandModelFactory.getStrategyScrap(1/* LocalDateTime.now().getDayOfWeek().getValue()*/);
		scrapper.scrap(strategy);
	}	
	
}
