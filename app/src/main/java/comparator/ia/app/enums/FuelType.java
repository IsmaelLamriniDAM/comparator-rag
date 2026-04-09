package comparator.ia.app.enums;

public enum FuelType {
	GASOLINE, HYBRID, DIESEL,ELECTRIC, GNC, GLP, OTHER;

	
	public static FuelType getFuels(String fuel) {
		return switch (fuel.toUpperCase()) {
		case "GASOLINA" -> GASOLINE;
		case "DIESEL", "DIÉSEL" -> DIESEL;
		case "HRÍBRIDO", "HIBRIDO" -> HYBRID;
		case "GLP" -> GLP;
		case "ELECTRICO" -> ELECTRIC;
		case "GNC" -> GNC;
		default -> FuelType.OTHER;
		};
	}
}
