package comparator.ia.app.service.chat;

import java.io.IOException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;
import comparator.ia.app.util.chainOfResponsability.messageRag.MessageStatisticsCreatorRag;

@Service
public class ChatService implements ChatServiceApi {
	
	public final Logger logger = LoggerFactory.getLogger(ChatService.class);
	
    private final ChatModel chatModel;
    
    private final MessageStatisticsCreatorRag creatorMessage;
    
    @Value("classpath:promts/prompt.greeting.txt")
    private Resource promptGreeting;
    
    public ChatService(ChatModel chatModel, MessageStatisticsCreatorRag creatorMessage) {
		this.chatModel = chatModel;
		this.creatorMessage = creatorMessage;
    }
    
    @Override
    public String getAnswer(StadisticsVehicleChatDto dto){
    	logger.info("GENERATING REQUEST AI...");
    	
    	return creatorMessage.buildMessage(dto);
    }
    

	@Override
	public String getGreeting() {
		logger.info("== GENERANDO MENSAJE DE BIENVENIDA ==");
		   try {
		        String template = this.promptGreeting.getContentAsString(Charset.defaultCharset());

		        return chatModel.call(new Prompt(template))
		                .getResult()
		                .getOutput()
		                .getText();

		    } catch (IOException e) {
		        throw new IllegalStateException("No se pudo leer prompt.greeting.txt", e);
		    }
	}

	@Override
	public String getAnswerUnknownOperation() {
		logger.info("== GENERANDO MESANJE DE ERROR DE OPERACION ==");
		return "No se ha reconocido la operación que se quiere realizar. (COMPRA / VENTA / AMBOS)";
	}

}
