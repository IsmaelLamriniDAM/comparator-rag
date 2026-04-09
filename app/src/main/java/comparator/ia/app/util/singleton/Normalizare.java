package comparator.ia.app.util.singleton;

import java.text.Normalizer;

public class Normalizare {
	
	private static Normalizare instance;
	
	private Normalizare() {}
	
	public static Normalizare getInstance() {
		if(instance == null) {
			instance = new Normalizare();
		}
		return instance;
	}
	
	public String normalizater(String input) {
		if (input == null) return null;

	    String s = input.trim();
	    if (s.isEmpty()) return s;

	    s = Normalizer.normalize(s, Normalizer.Form.NFD);
	    s = s.replaceAll("\\p{M}", "");
	    s = s.toUpperCase();
	    s = s.replaceAll("[\\s\\-_./,;:|\\()\\[\\]{}]+", " ");
	    s = s.replaceAll("[^A-Z0-9 +]", "");
	    s = s.replaceAll("\\s+", " ").trim();
	    
	    return s;
	}
	
}
