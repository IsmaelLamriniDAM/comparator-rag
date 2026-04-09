package comparator.ia.app.controller.historyChatIa;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import comparator.ia.app.dtos.history.HistoryDto;
import comparator.ia.app.dtos.history.HistoryMessageDto;
import comparator.ia.app.handler.exceptions.auth.AuthenticationFailedException;
import comparator.ia.app.service.history.HistoryChatIaService;
import comparator.ia.app.util.auth.SessionManager;
import jakarta.servlet.http.HttpSession;

@RestController
public class HistoryChatControllerImp implements HistoryChatController{
	
	private final HistoryChatIaService historyService;
	
	private final SessionManager sessionManager;
	
	HistoryChatControllerImp(HistoryChatIaService historyService, SessionManager sessionManager) {
		this.historyService = historyService;
		this.sessionManager = sessionManager;
	}

	@Override
	public ResponseEntity<List<HistoryDto>> getAllHistories(HttpSession session) {
		if(!sessionManager.checkSession(session)) {
			throw new AuthenticationFailedException();
		}
		
		List<HistoryDto> dtos = historyService.findAllHistories(session);
		
		return ResponseEntity.ok(dtos);
	}

	@Override
	public ResponseEntity<List<HistoryMessageDto>> getHistory(String idHistory, HttpSession session) {
		if(!sessionManager.checkSession(session)) {
			throw new AuthenticationFailedException();
		}
		
		List<HistoryMessageDto> dtos = historyService.findMessageHistory(idHistory, session);
		
		return ResponseEntity.ok(dtos);
	}

	@Override
	public ResponseEntity<Void> deleteHistory(String idHistory, HttpSession session) {
		if(!sessionManager.checkSession(session)) {
			throw new AuthenticationFailedException();
		}
		
		historyService.deleteHistory(idHistory, session);
		
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> deleteAllHistory(HttpSession session) {
		if(!sessionManager.checkSession(session)) {
			throw new AuthenticationFailedException();
		}
		
		historyService.deleteAllHistories(session);
		return ResponseEntity.noContent().build();
	}

}
