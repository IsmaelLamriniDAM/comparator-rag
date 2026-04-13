package comparator.ia.app.controller.historyChatIa;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import comparator.ia.app.dtos.history.HistoryDto;
import comparator.ia.app.dtos.history.HistoryMessageDto;
import jakarta.servlet.http.HttpSession;

@RequestMapping("api/v1/history-chat")
public interface HistoryChatController {
	
	@GetMapping("/all")
	ResponseEntity<List<HistoryDto>> getAllHistories(HttpSession session);
	
	@GetMapping("/history")
	ResponseEntity<List<HistoryMessageDto>> getHistory(@RequestParam(required = true, name = "id") String idHistory, HttpSession session);
	
	@DeleteMapping("/delete/history")
	ResponseEntity<Void> deleteHistory(@RequestParam(required = true, name = "id") String idHistory, HttpSession session);
	
	@DeleteMapping("delete/all")
	ResponseEntity<Void> deleteAllHistory(HttpSession session);
	
}
