package comparator.ia.app.util.chainOfResponsability.messageRag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;

import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;

public class MessageErrorRag extends MessageStatisticsCreatorRag{

	public MessageErrorRag(ChatModel chatModel, ChatClient chatClient) {
		super(chatModel, chatClient);
	}

	@Override
	public String buildMessage(StadisticsVehicleChatDto dto) {
		return "Siento decirte que no he podido entender tu mensaje. Por favor escribalo de nuevo.";
	}

}
