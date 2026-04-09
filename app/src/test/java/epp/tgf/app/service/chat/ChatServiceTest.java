package epp.tgf.app.service.chat;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import comparator.ia.app.service.chat.ChatServiceApi;

@Import(ChatServiceApi.class)
class ChatServiceTest {
	
	@Autowired
	private ChatServiceApi chatService;

	@Test
	void testGetGreeting() {
		fail();
	}

}
