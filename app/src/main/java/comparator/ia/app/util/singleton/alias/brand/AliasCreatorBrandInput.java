package comparator.ia.app.util.singleton.alias.brand;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comparator.ia.app.util.singleton.Normalizare;
import comparator.ia.app.util.singleton.alias.Aliazable;
import comparator.ia.app.util.singleton.alias.model.AliasCreatorModelInput;

public final class AliasCreatorBrandInput implements Aliazable{
	
	private static final Logger logger = LoggerFactory.getLogger(AliasCreatorBrandInput.class);
	
	private static AliasCreatorBrandInput instance;
	
	private AliasCreatorBrandInput() {}
	
	public static AliasCreatorBrandInput getInstance() {
		if(instance == null) {
			instance = new AliasCreatorBrandInput();
		}
		return instance;
	}

	@Override
	public Set<String> createAlias(String input) {
		logger.info("Gererating alias brands...");
		
		Set<String> alias = new HashSet<>();
		String normalizated = Normalizare.getInstance().normalizater(input);
		alias.add(normalizated);
		alias.add(normalizated.replaceAll("\\s+", ""));
		alias.add(normalizated.replaceAll("\\s+", "").chars().mapToObj(o -> (char)o).map(String::valueOf).collect(Collectors.joining(" ")));
		alias.addAll(Arrays.stream(normalizated.split("\\s+")).toList());
		
		logger.info("Gererated alias brands.");
		return alias;
	}

}
