package epp.tgf.app.scrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import comparator.ia.app.handler.exceptions.DaySelectionScrapperNotExists;
import comparator.ia.app.service.scrap.vehicle.CochesNetScrapperService;
import comparator.ia.app.service.scrap.vehicle.MilAnuncionScrapperService;
import comparator.ia.app.service.scrap.vehicle.OcasionPlusScrapperService;
import comparator.ia.app.util.factory.vehicle.ScrapperVehicleFactory;
import comparator.ia.app.util.schedule.vehicle.Scrapper;

@ExtendWith(MockitoExtension.class)
class ScrapperFactoryTest {
	
	private static final Integer STRATEGY_DAY_NOT_EXIST = 8;
	private static final Integer STRATEGY_MONDAY_MILANUNCIOS = 1;
	private static final Integer STRATEGY_TUESDAY_COCHES_NET = 2;
	private static final Integer STRATEGY_WEDNESDAY_OCASION_PLUS = 3;
	
	@InjectMocks
	private Scrapper scrapper;
	
	@InjectMocks
	private ScrapperVehicleFactory factory;
	
	@Test
	void testStrategyCallNullStrategy() {
		assertThrows(DaySelectionScrapperNotExists.class,() -> 
				scrapper.scrap(factory.get(STRATEGY_DAY_NOT_EXIST)));
	}
	
	@Test
	void testStrategyCallMilAnuncios() {
		assertEquals(factory.get(STRATEGY_MONDAY_MILANUNCIOS).getClass()
				, MilAnuncionScrapperService.class);
	}
	
	@Test
	void testStrategyCallCochesNet() {
		assertEquals(factory.get(STRATEGY_TUESDAY_COCHES_NET).getClass()
				, CochesNetScrapperService.class);
	}
	
	@Test
	void testStrategyCallOcasionPlus() {
		assertEquals(factory.get(STRATEGY_WEDNESDAY_OCASION_PLUS).getClass()
				, OcasionPlusScrapperService.class);
	}
	
}
