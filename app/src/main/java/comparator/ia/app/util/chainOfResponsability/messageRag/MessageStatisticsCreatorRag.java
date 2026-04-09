package comparator.ia.app.util.chainOfResponsability.messageRag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;

public abstract class MessageStatisticsCreatorRag {

	protected final ChatModel chatModel;
	protected final ChatClient chatClient;
	protected MessageStatisticsCreatorRag next;
	
	protected static final Gson GSON = new GsonBuilder()
			.serializeNulls()
			.setPrettyPrinting()
			.create();
	
    protected MessageStatisticsCreatorRag(ChatModel chatModel, ChatClient chatClient) {
		this.chatModel = chatModel;
		this.chatClient = chatClient;}
    
	public abstract String buildMessage(StadisticsVehicleChatDto dto);
	
	protected String sendMessage(String promptNew) {
		PromptTemplate promt = new PromptTemplate(promptNew.replaceAll("[{]", "<").replaceAll("[}]", ">"));
		
		ChatResponse chatResponse = this.chatClient.prompt(promt.create()).call().chatResponse();
		if(chatResponse == null ) {
			throw new RuntimeException();
		}
		return chatResponse.getResult().getOutput().getText();
		
	}
	
	public void setNext(MessageStatisticsCreatorRag next) {
		this.next = next;
	}
	
	protected String buildNumCarComparedJson(Long num) {
		return GSON.toJson(num);
	}
	
	protected String buildQuestionJson(StadisticsVehicleChatDto dto) {
		return GSON.toJson(dto.getQuestion());
	}
}
