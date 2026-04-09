package comparator.ia.app.util.chainOfResponsability.createDataOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;

public abstract class CreateDataOperation {

	protected CreateDataOperation nextField;
	protected static final Logger logger = LoggerFactory.getLogger(CreateDataOperation.class);
	
	public void setNextField(CreateDataOperation next) {
		this.nextField = next;
	}
	
	public abstract void buildFields(String message, SimilarCarOperationDTO car);
	
	
}
