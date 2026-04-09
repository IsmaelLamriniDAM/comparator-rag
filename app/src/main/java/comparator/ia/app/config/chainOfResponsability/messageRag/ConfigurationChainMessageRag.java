package comparator.ia.app.config.chainOfResponsability.messageRag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import comparator.ia.app.util.chainOfResponsability.messageRag.MessageBothRag;
import comparator.ia.app.util.chainOfResponsability.messageRag.MessageBuyRag;
import comparator.ia.app.util.chainOfResponsability.messageRag.MessageErrorRag;
import comparator.ia.app.util.chainOfResponsability.messageRag.MessageSellRag;
import comparator.ia.app.util.chainOfResponsability.messageRag.MessageStatisticsCreatorRag;

@Configuration
public class ConfigurationChainMessageRag {
	
	private final ChatModel chatModel;
	private final ChatClient chatClient;
	
	ConfigurationChainMessageRag(ChatModel chatModel) {
		this.chatModel = chatModel;
		this.chatClient = ChatClient.builder(chatModel).build();
	}

	@Bean
	MessageStatisticsCreatorRag configure() {
		MessageStatisticsCreatorRag principal = new MessageBuyRag(chatModel, chatClient);
		MessageSellRag sell = new MessageSellRag(chatModel, chatClient);
		MessageBothRag both = new MessageBothRag(chatModel, chatClient);
		MessageErrorRag error = new MessageErrorRag(chatModel, chatClient);
		
		principal.setNext(sell);
		sell.setNext(both);
		both.setNext(error);
		
		
		return principal;
	}
	
}
