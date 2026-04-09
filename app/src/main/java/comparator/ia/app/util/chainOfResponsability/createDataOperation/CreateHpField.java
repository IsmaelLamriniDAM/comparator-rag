package comparator.ia.app.util.chainOfResponsability.createDataOperation;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import comparator.ia.app.dtos.chain.HorsePower;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;

public class CreateHpField extends CreateDataOperation {

    private static final Pattern PATTERN_VH = Pattern.compile("\\d*\\s?(.)?\\s?\\d+\\s*([a\\-y]|hasta)?\\s*\\d+(.)?\\d*\\s*(ph|caballos)");
    private static final Pattern NUM_PATTERN = Pattern.compile("\\d+([ .]*\\d+)*");

    @Override
    public void buildFields(String message, SimilarCarOperationDTO car) {
    	logger.info("creating ph field");
    	
        List<Integer> vhs = new LinkedList<>();
        Matcher matcher = PATTERN_VH.matcher(message.toLowerCase());
        while(matcher.find()) {
        	String matched = matcher.group().replace("\\.", " ");
            Matcher numMatcher = NUM_PATTERN.matcher(matched);
            while(numMatcher.find()) {
                vhs.add(getVhMatch(numMatcher.group()));
            }
        }
        
        if(!vhs.isEmpty()) {
        	vhs.sort(Comparator.naturalOrder());
        	car.setHp(new HorsePower(vhs.getLast(), vhs.getFirst()));
        } else {
        	car.setHp(new HorsePower(null, null));
        }
        
        logger.info("created hp field -> {}", car.getHp());
        nextField.buildFields(message, car);
    }

    private int getVhMatch(String hp) {
        return Integer.valueOf(hp.replaceAll("\\D", "").trim());
    }
}
