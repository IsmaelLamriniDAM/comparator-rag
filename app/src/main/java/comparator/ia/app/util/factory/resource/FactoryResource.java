package comparator.ia.app.util.factory.resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import comparator.ia.app.enums.Operation;

public class FactoryResource {
	
	@Autowired
	@Value("classpath:promts/prompt_Buy.txt")
	private static Resource promptBuy;
	
	private static final Map<String, Resource> mapResource = new HashMap<>() {
		{
			put(Operation.BUY.toString(), promptBuy);
		}
	};
	
	public static Resource getResouce(String resource) {
		if(resource == null || resource.isBlank()) {
			throw new RuntimeException("Resource no encontrado -> " + resource);
		}
		
		Resource prompt = mapResource.get(resource);
		
		if(prompt == null) {
			throw new RuntimeException("Resource no encontrado -> " + resource);
		}
		
		return prompt;
	}
	
}
