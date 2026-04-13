package comparator.ia.app.controller.chatAi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import comparator.ia.app.dtos.chat.RequestDataChatDto;
import comparator.ia.app.dtos.history.HistoryDataSavedDto;
import comparator.ia.app.dtos.history.HistoryRequestDto;
import comparator.ia.app.handler.exceptions.auth.AuthenticationFailedException;
import comparator.ia.app.service.chat.ChatServiceApi;
import comparator.ia.app.service.history.HistoryChatIaService;
import comparator.ia.app.service.manager.ChatManagerService;
import comparator.ia.app.util.auth.SessionManager;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
public class ControllerChatAi implements ControllerChatApi {
	
	private final Logger logger = LoggerFactory.getLogger(ControllerChatAi.class);
	
	private final ChatManagerService managerChatService;
	private final ChatServiceApi chatService;
	private final SessionManager sessionManager;
	
	private final HistoryChatIaService historyChatService;

    ControllerChatAi(ChatManagerService managerChatService, ChatServiceApi chatService,
    		SessionManager sessionManager, HistoryChatIaService historyChatService) {
    	this.managerChatService = managerChatService;
		this.chatService = chatService;
		this.sessionManager = sessionManager;
		this.historyChatService = historyChatService;
    }

    @Override
    public ResponseEntity<HistoryDataSavedDto> chat(@Valid @RequestBody RequestDataChatDto dataDto, HttpSession session) {
		if (!sessionManager.checkSession(session)) {
			throw new AuthenticationFailedException();
		}
		String responseIa = managerChatService.getStatisticsFromMessage(dataDto.messageUser());

		HistoryDataSavedDto data = historyChatService
				.addHistory(new HistoryRequestDto(dataDto.messageUser(), responseIa, dataDto.idHistory()), session);

		return ResponseEntity.ok(data);

    }

	@Override
	public ResponseEntity<String> getGreeting(HttpSession session) {
		if(!sessionManager.checkSession(session)) {
			throw new AuthenticationFailedException();
		} 
		return ResponseEntity.ok(chatService.getGreeting());
	}
}
