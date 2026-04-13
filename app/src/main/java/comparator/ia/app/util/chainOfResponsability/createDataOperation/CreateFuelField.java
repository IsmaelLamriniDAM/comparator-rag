package comparator.ia.app.util.chainOfResponsability.createDataOperation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.enums.FuelType;

public class CreateFuelField extends CreateDataOperation {

    private static final Pattern PATTERN_FUEL_TYPE = Pattern.compile("(di([e]|[é])sel|gasolina|h([i]|[í])brido|el([e]|[é])ctrico|gnc|glp)");

    @Override
    public void buildFields(String message, SimilarCarOperationDTO car) {
    	logger.info("creating fuel field");
    	
        Matcher matcher = PATTERN_FUEL_TYPE.matcher(message.toLowerCase());
        if (matcher.find()) {
            car.setFuelType(FuelType.getFuels(matcher.group()));
        } else {
        	car.setFuelType(FuelType.OTHER);
        }
        logger.info("created fuel field -> {}", car.getFuelType());
        nextField.buildFields(message, car);
    }
    
}
