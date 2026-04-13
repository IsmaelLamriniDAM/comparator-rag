package comparator.ia.app.util.chainOfResponsability.createDataOperation;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import comparator.ia.app.dtos.chain.Kilometers;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;

public class CreateKmField extends CreateDataOperation {

    private static final Pattern KM_PATTERN = Pattern.compile("\\d*\\s?(.)?\\s?\\d+\\s*(a|-|y|hasta)?\\s*\\d+\\s?(.)\\s?\\d*\\s*(km|kilometros)");
    private static final Pattern NUM_PATTERN = Pattern.compile("\\d+([ .,]*\\d+)*");

    @Override
    public void buildFields(String message, SimilarCarOperationDTO car) {
    	logger.info("creating km field");
    	
        List<Integer> kms = new LinkedList<>();
        Matcher matcher = KM_PATTERN.matcher(message.toLowerCase());
        while(matcher.find()) {
            Matcher numMatcher = NUM_PATTERN.matcher(matcher.group().replace("\\.", " "));
            while(numMatcher.find()) {
                int km = getKmMatch(numMatcher.group());
                kms.add(km);
            }
        }
        
        if(!kms.isEmpty()) {
        	kms.sort(Comparator.naturalOrder());
        	car.setKm(new Kilometers(kms.getLast(), kms.getFirst()));
        } else {
        	car.setKm(new Kilometers(null, null));
        }
        
        logger.info("created km field -> {}", car.getKm());
        // EL ULTIMO DE LA CADENA
    }

    private int getKmMatch(String km) {
        return Integer.valueOf(km.replaceAll("\\D", "").trim());
    }
}
