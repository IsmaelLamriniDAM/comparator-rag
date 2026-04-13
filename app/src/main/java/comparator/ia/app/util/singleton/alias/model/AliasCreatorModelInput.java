package comparator.ia.app.util.singleton.alias.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comparator.ia.app.util.singleton.Normalizare;
import comparator.ia.app.util.singleton.alias.Aliazable;

public final class AliasCreatorModelInput implements Aliazable {
	
	private static final Logger logger = LoggerFactory.getLogger(AliasCreatorModelInput.class);
	
	private static AliasCreatorModelInput instance;
	
	private AliasCreatorModelInput() {}
	
	public static AliasCreatorModelInput getInstance() {
		if(instance == null) {
			instance = new AliasCreatorModelInput();
		}
		return instance;
	}

	@Override
	public Set<String> createAlias(String input) {
		logger.info("Generating alias models..");
		Set<String> alias = new HashSet<>();
		String normalizated = Normalizare.getInstance().normalizater(input);
		alias.add(normalizated);
		alias.add(normalizated.replaceAll("\\s+", ""));
		alias.add(normalizated.replaceAll("\\s+", "").chars().mapToObj(o -> (char)o).map(String::valueOf).collect(Collectors.joining(" ")));
		
		logger.info("generated alias models: {}", alias);
		return alias;
	}
	
}
