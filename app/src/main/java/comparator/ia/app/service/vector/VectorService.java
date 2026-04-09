package comparator.ia.app.service.vector;

import java.util.List;
import java.util.Set;

import comparator.ia.app.dtos.brand.BrandModel;

public interface VectorService {

    void createEmbbeding(Set<BrandModel> vehicleDto);
    
    String getBrandVector(String question);
    String getModelVector(String question, String brands);
    
    default String normalitationUserMessage(String message) {
    	return message.toUpperCase().replaceAll("\\s+", " ").replaceAll("[.,]", " ");
    }
}
