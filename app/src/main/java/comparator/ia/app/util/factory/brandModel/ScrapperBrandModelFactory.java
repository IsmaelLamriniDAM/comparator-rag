package comparator.ia.app.util.factory.brandModel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import comparator.ia.app.handler.exceptions.DaySelectionScrapperNotExists;
import comparator.ia.app.service.scrap.brandModel.StrategyScrapBrandModelCochesNet;
import comparator.ia.app.util.strategy.brandModel.StrategyScrapBrandModel;

public class ScrapperBrandModelFactory {
	
	private static final Map<Integer, Supplier<StrategyScrapBrandModel>> mapStrategy = new HashMap<Integer, Supplier<StrategyScrapBrandModel>>(){
		{
			put(1, StrategyScrapBrandModelCochesNet::new);
		}
	};
	
	private ScrapperBrandModelFactory() {}
	
	public static StrategyScrapBrandModel getStrategyScrap(Integer day) {
		Supplier<StrategyScrapBrandModel> strategy = mapStrategy.get(day);
		
		if(strategy == null || strategy.get() == null) {
			throw new DaySelectionScrapperNotExists("Strategy Day does not exist");
		}
		
		return strategy.get();
	}
}
