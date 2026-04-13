package comparator.ia.app.service.history;

import java.util.List;

import comparator.ia.app.dtos.history.HistoryDataSavedDto;
import comparator.ia.app.dtos.history.HistoryDto;
import comparator.ia.app.dtos.history.HistoryMessageDto;
import comparator.ia.app.dtos.history.HistoryRequestDto;
import jakarta.servlet.http.HttpSession;

public interface HistoryChatIaService {
	
	HistoryDataSavedDto addHistory(HistoryRequestDto dataHistory, HttpSession session);
	
	List<HistoryDto> findAllHistories( HttpSession session);
	
	List<HistoryMessageDto> findMessageHistory(String idHistoryShare, HttpSession session);
	
	void deleteHistory(String idHistoryShare,   HttpSession session);
	
	void deleteAllHistories( HttpSession session);
	
}
