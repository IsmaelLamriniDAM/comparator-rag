package comparator.ia.app.mapper.entityVehicle;

import java.time.LocalDateTime;

import org.mapstruct.Named;

public class MapperFieldVehicleEntity{

	@Named("publishDateToString")
	public static String publishDateToString(LocalDateTime date) {
		return date.toString();
	}
	
	@Named("removalDateToString")
	public static String removalDateToString(LocalDateTime date) {
		return date.toString();
	}
	
}
