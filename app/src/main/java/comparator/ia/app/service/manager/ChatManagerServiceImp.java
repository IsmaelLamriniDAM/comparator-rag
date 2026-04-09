package comparator.ia.app.service.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import comparator.ia.app.dtos.chat.QuestionUser;
import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.handler.exceptions.EmptyVehicleListException;
import comparator.ia.app.handler.exceptions.EmptyVehiclesInDBException;
import comparator.ia.app.service.chat.ChatServiceApi;
import comparator.ia.app.service.vehicle.VehicleService;
import comparator.ia.app.util.chainOfResponsability.createDataOperation.CreateDataOperation;
import comparator.ia.app.util.strategy.stadistics.CalculateStadistic;
import comparator.ia.app.util.strategy.stadistics.StadisticsInput;

@Service()
public class ChatManagerServiceImp implements ChatManagerService{
	
	public final Logger logger = LoggerFactory.getLogger(ChatManagerServiceImp.class);
	
	private final CalculateStadistic calculateOperation;
	private final ChatServiceApi serviceChatAi;
	private final CreateDataOperation createDataOperation;
	private final VehicleService vehicleService;
	
	public ChatManagerServiceImp(CalculateStadistic calculateOperation,
			 ChatServiceApi serviceChatAi, CreateDataOperation createDataOperation, VehicleService vehicleService) {
		this.calculateOperation = calculateOperation;
		this.serviceChatAi = serviceChatAi;
		this.createDataOperation = createDataOperation;
		this.vehicleService = vehicleService;
	}
	
	@Override
	public String getStatisticsFromMessage(String message){
		logger.info("MENSAJE DEL USUARIO -> {}", message);
		
		if(!vehicleService.existsVehiclesInDB()) {
			logger.warn("Not exists vehicles in DB therefore it not cant execute operation.");
			throw new EmptyVehiclesInDBException();
		}
		
		String messageLower = message.toLowerCase();
		
		SimilarCarOperationDTO jsonDTO = new SimilarCarOperationDTO();
		createDataOperation.buildFields(messageLower, jsonDTO);
		
		if(jsonDTO.getOperation() == Operation.UNKNOWN) return serviceChatAi.getAnswerUnknownOperation();
		
		StadisticsInput input = new StadisticsInput(jsonDTO);
		
		StadisticsVehicleChatDto dto = calculateOperation.calculate(input);
		
		dto.setMarketComparedVehicleCount(vehicleService.getCountVehiclesCompared(jsonDTO));
		
		logger.info("CALCULO REALIZADO.");
		
		dto.setQuestion(new QuestionUser(message, input.getSimilarVehicle().getPrice().getRangeSell() ,input.getSimilarVehicle().getPrice().getRangeBuy()));
		
		logger.info("PREGUNTA USUARIO -> {}", dto.getQuestion());
		return serviceChatAi.getAnswer(dto);
	}

}