package comparator.ia.app.util.chainOfResponsability.createDataOperation;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;

public class CreateYearField extends CreateDataOperation {

    private static final Pattern YEAR_PATTERN = Pattern.compile("(del|año)\\s*\\d{4}");
    public static final int YEAR_CAR_EXIST_OLDEST = 1884;

    @Override
    public void buildFields(String message, SimilarCarOperationDTO car) {
    	logger.info("creating year field");
    	
    	String lowerMessage = message.toLowerCase();
        Matcher matcher = YEAR_PATTERN.matcher(lowerMessage);
        if(matcher.find()) {
            int year = getMatcher(matcher.group());
            if(year >= YEAR_CAR_EXIST_OLDEST && year <= LocalDateTime.now().getYear()) {
                car.setYear(year);
            }
        }
        logger.info("created year field -> {}", car.getYear());
        nextField.buildFields(message, car);
    }

    private int getMatcher(String input) {
        return Integer.valueOf(input.replaceAll("\\D", "").trim());
    }
}
