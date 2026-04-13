package comparator.ia.app.util.schedule.vehicle;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import comparator.ia.app.service.alias.brand.BrandAliasService;
import comparator.ia.app.service.vector.VectorService;
import comparator.ia.app.util.factory.vehicle.ScrapperVehicleFactory;
import comparator.ia.app.util.strategy.vehicle.api.VehicleScrapperStrategy;


@EnableScheduling
@Configuration
public class ScheduleVehicleScrapper {
	
	private Scrapper scrapper;
	private final ScrapperVehicleFactory factory;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(ScheduleVehicleScrapper.class);
	
	public ScheduleVehicleScrapper(Scrapper scrapper, ScrapperVehicleFactory factory) {
		this.factory = factory;
		this.scrapper = scrapper;
	}
	
	// cron = "0 0 03 * * 2-4"
	@Scheduled(cron = "0 20 * * * *")
	private void scrapping() {
		logger.info("Iniciando el scrapper a las {}", LocalDateTime.now());
		VehicleScrapperStrategy strategy = factory.get(3/* LocalDateTime.now().getDayOfWeek().getValue()*/);
		
		scrapper.scrap(strategy);
	}	
}
