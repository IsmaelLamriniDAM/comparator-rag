package comparator.ia.app.config.vectoreStore;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore.PgDistanceType;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore.PgIndexType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ConfigVectoreStore {
	
	private static final Integer DIMENSIONS_OLLAMA = 768;

	@Qualifier("beanBrandVector")
	@Bean
	public VectorStore vectorStoreBrand(JdbcTemplate jdbcTemplate, EmbeddingModel embeddingModel) {
		  return PgVectorStore.builder(jdbcTemplate, embeddingModel)
	                .initializeSchema(true)              
			        .distanceType(PgDistanceType.COSINE_DISTANCE)      
			        .indexType(PgIndexType.HNSW)                    
			        .schemaName("public")               
			        .vectorTableName("vector_store_brand")    
			        .maxDocumentBatchSize(10000)
			        .build();
	}
	@Qualifier("beanModelVector")
	@Bean
	public VectorStore vectorStoreModel(JdbcTemplate jdbcTemplate, EmbeddingModel embeddingModel) {
		  return PgVectorStore.builder(jdbcTemplate, embeddingModel)
	                .initializeSchema(true)            
			        .distanceType(PgDistanceType.COSINE_DISTANCE)       
			        .indexType(PgIndexType.HNSW)                     
			        .schemaName("public")               
			        .vectorTableName("vector_store_model")    
			        .maxDocumentBatchSize(10000)        
			        .build();
	}
	
}
