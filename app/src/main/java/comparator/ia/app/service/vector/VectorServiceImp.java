package comparator.ia.app.service.vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.BatchingStrategy;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import comparator.ia.app.dtos.brand.BrandModel;
import comparator.ia.app.service.brand.BrandService;
import comparator.ia.app.service.wikipedia.infoBrand.WikipediaService;

@Service
public class VectorServiceImp implements VectorService {
	
	private static final String MODEL_KEY_METADATA = "MODEL";

	private static final String KEY_BRAND_METADATA = "BRAND";
	
	private static final String CONFUSION_WIKI = "página de desambiguación de Wikimedia";

	public enum Field {
		BRAND, MODEL;
	}

	private static final Logger logger = LoggerFactory.getLogger(VectorServiceImp.class);
	
	private static final double SIMILATITY_THRESHOLD_BRAND = 0.5;
	private static final double SIMILATITY_THRESHOLD_MODEL = 0.4;
	
	private static final int TOP_K = 1;
	
	@Qualifier("beanBrandVector") private final VectorStore vectorStoreBrand;
	
	@Qualifier("beanModelVector") private final VectorStore vectorStoreModel;
	
	private final BatchingStrategy batch;
	
	private final BrandService dataBrandService;
	
	private final WikipediaService wikiService;

    public VectorServiceImp(VectorStore vectorStoreBrand, VectorStore vectorStoreModel,
    		BatchingStrategy batch,  BrandService dataBrandService, WikipediaService wikiService) {
        this.vectorStoreBrand = vectorStoreBrand;
		this.vectorStoreModel = vectorStoreModel;
		this.batch = batch;
		this.dataBrandService = dataBrandService;
		this.wikiService = wikiService;
    }
    
    @Override
    public void createEmbbeding(Set<BrandModel> brandsAndHisModels) {
    	logger.info("INCRUSTANDO DATOS");
    	Set<String> brands = brandsAndHisModels.stream().map(bm -> bm.brand().toUpperCase()).collect(Collectors.toSet());
    	startEmbbedingBrand(brands);
    	
    	
    	startEmbbedingModel(brandsAndHisModels);
    	
    }
    
	private void startEmbbedingBrand(Set<String> brandModels) {
		logger.info("INCRUSTANDO MARCAS");
		
		List<Document> documents = new ArrayList<>();
		
		for(String brand : brandModels) {
			
			String descriptionWikiPedia =  wikiService.getDescriptionWikiBrand(brand.toUpperCase());
			StringBuilder description = new StringBuilder("La marca de este coche se llama: " + brand.toUpperCase() + ". Y se trata de una ");
			
			if(descriptionWikiPedia == null || descriptionWikiPedia.isBlank() || descriptionWikiPedia.equalsIgnoreCase(CONFUSION_WIKI)) {
				description = new StringBuilder("Es una marca de automovil llamado " + brand.toUpperCase());
			} else {
				description.append(descriptionWikiPedia);
			}
			
			Document doc = new Document(description.toString(), Map.of(
					KEY_BRAND_METADATA, brand.toUpperCase()
            		));
			
			if(doc != null && doc.getText() != null && !doc.getText().trim().isBlank()) {
				documents.add(doc);
			}
			
		}
		
		saveEmbbedDB(documents, Field.BRAND);
	}
    
    private void startEmbbedingModel(Set<BrandModel> brandsAndHisModels) {
    	logger.info("INCRUSTANDO MODELOS");
    	List<Document> documents = new ArrayList<>();
    	
		for (BrandModel brandHisModel : brandsAndHisModels) {
			for(String nameModel : brandHisModel.model()) {
				documents.add(new Document(
						"Modelo de automovil de la marca " + brandHisModel.brand().toUpperCase() + " y el nombre del modelo es  "
								+ nameModel.toUpperCase(),
								Map.of(MODEL_KEY_METADATA, nameModel.toUpperCase(), 
										KEY_BRAND_METADATA, brandHisModel.brand().toUpperCase())));
			}
		}

		saveEmbbedDB(documents, Field.MODEL);
	}

	private void saveEmbbedDB(List<Document> documents, Field field) {
		for(List<Document> listDocs :batch.batch(documents)) {
			if (field.equals(Field.BRAND)) {
				vectorStoreBrand.add(listDocs);
			} else {
				vectorStoreModel.add(listDocs);
			}
		}
	}

	@Override
	public String getBrandVector(String question) {
//		logger.info("BUSCANDO SIMILUTES DE MARCAS A LA PREGUNTA DEL USUARIO.");
		List<Document> docs = vectorStoreBrand
	              .similaritySearch(SearchRequest.
	            		  builder().
	            		  query(normalitationUserMessage(question))
	            		  .topK(TOP_K).similarityThreshold(SIMILATITY_THRESHOLD_BRAND)
	            		  .build()
	            		  );
		
		if(docs == null || docs.isEmpty()) return "";
		
		return docs.stream()
              .map(d -> d.getMetadata().get(KEY_BRAND_METADATA).toString()).collect(Collectors.joining());
	}

	@Override
	public String getModelVector(String question, String brand) {
//		logger.info("BUSCANDO SIMILUTES DE MODELOS A LA PREGUNTA DEL USUARIO.");
		
		StringBuilder condition = new StringBuilder();
		if(brand == null || brand.isBlank()) return "";
		
		condition.append("BRAND == ").append("'").append(brand.toUpperCase()).append("'");
//		logger.info("CONDITION SEARCH MODEL: {}", condition);
		
		List<Document> docs = vectorStoreModel
	              .similaritySearch(SearchRequest
	            		  .builder()
	            		  .query(normalitationUserMessage(question))
	            		  .topK(TOP_K)
	            		  .filterExpression(condition.toString().isEmpty() ? null : condition.toString())
	            		  .similarityThreshold(SIMILATITY_THRESHOLD_MODEL)
	            		  .build());
		
		if(docs == null || docs.isEmpty()) return "";
		
		return docs.stream()
	            .map(d -> d.getMetadata().get(MODEL_KEY_METADATA).toString())
	            .collect(Collectors.joining());
	}
    
}
