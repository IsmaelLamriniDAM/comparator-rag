package comparator.ia.app.service.history;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import comparator.ia.app.dtos.history.HistoryDataSavedDto;
import comparator.ia.app.dtos.history.HistoryDto;
import comparator.ia.app.dtos.history.HistoryMessageDto;
import comparator.ia.app.dtos.history.HistoryRequestDto;
import comparator.ia.app.dtos.history.MessageAndRol;
import comparator.ia.app.entities.HistoryIaEntity;
import comparator.ia.app.entities.MessageIaEntity;
import comparator.ia.app.entities.MessageUserEntity;
import comparator.ia.app.entities.UserEntity;
import comparator.ia.app.enums.RolChat;
import comparator.ia.app.repository.HistoryChatRepository;
import comparator.ia.app.repository.MessageIaRepository;
import comparator.ia.app.repository.MessageUserRepository;
import comparator.ia.app.repository.UserRepository;
import comparator.ia.app.util.auth.SessionManager;
import jakarta.servlet.http.HttpSession;

@Service
public class HistoryChatIaServiceImp implements HistoryChatIaService{
	
	private static final String TITLE_BASE = "CHAT - ";
	
	private Logger logger = LoggerFactory.getLogger(HistoryChatIaServiceImp.class);
	
	private final HistoryChatRepository historyRepo;
	
	private final MessageIaRepository messageIaRepo;
	
	private final MessageUserRepository messageUserRepo;
	
	private final SessionManager sessionManager;
	
	private final UserRepository userRepo;
	
	HistoryChatIaServiceImp(HistoryChatRepository historyRepo, SessionManager sessionManager
			, MessageUserRepository messageUserRepo, MessageIaRepository messageIaRepo, UserRepository userRepo) {
		this.historyRepo = historyRepo;
		this.messageIaRepo = messageIaRepo;
		this.messageUserRepo = messageUserRepo;
		this.sessionManager = sessionManager;
		this.userRepo = userRepo;
	}

	@Override
	@Transactional
	public HistoryDataSavedDto addHistory(HistoryRequestDto dataHistory,  HttpSession session) {
		if(dataHistory.messageIa() == null || dataHistory.messageIa().isBlank()) {
			logger.error("EL MENSAJE AQUI LLEGO NULLO");
		}
		
		UserEntity user = userRepo.findById(sessionManager.getKeySession(session).get()).get();
		if(isExistsTitle(dataHistory.idHistory())) {
			return updateHistory(dataHistory);
		}
		
		return savedNewHistory(dataHistory, user);
	}

	@Override
	public List<HistoryDto> findAllHistories(HttpSession session) {
		List<HistoryIaEntity> entities = historyRepo.findAllByUser_id(sessionManager.getKeySession(session).get());
		
		return entities.stream().map(dto -> {
			return new HistoryDto(dto.getIdShare(), dto.getTitle());
		}).toList();
	}

	@Override
	public List<HistoryMessageDto> findMessageHistory(String idHistory,  HttpSession session) {
		HistoryIaEntity entity = historyRepo.findByIdShareAndUser_id(idHistory, sessionManager.getKeySession(session).get());
		List<HistoryMessageDto> dtos = new ArrayList<>();
		
		entity.getMessagesIA().stream().forEach(e -> dtos.add(new HistoryMessageDto(e.getMessage(), e.getRol(), e.getDateCreated())));
		entity.getMessagesUser().stream().forEach(e -> dtos.add(new HistoryMessageDto(e.getMessage(), e.getRol(), e.getDateCreated())));
		
		return dtos.stream().sorted(Comparator.comparing(HistoryMessageDto::date_created)).toList();
	}

	@Override
	public void deleteHistory(String idHistory, HttpSession session) {
		HistoryIaEntity entity = historyRepo.findByIdShareAndUser_id(idHistory, sessionManager.getKeySession(session).get());
		historyRepo.delete(entity);
	}

	@Override
	public void deleteAllHistories( HttpSession session) {
		int rows = historyRepo.deleteByUser_id(sessionManager.getKeySession(session).get());
		if(rows > 1) {
			logger.info("SE BORRARON REGISTROS");
		} else {
			logger.info("NO SE BORRARON REGISTROS");
		}
		
	}
	
	private HistoryDataSavedDto savedNewHistory(HistoryRequestDto dataHistory, UserEntity user) {
		HistoryIaEntity historyEntity = new HistoryIaEntity();
		int numHistoriesInDB = historyRepo.countRegisterHistories();
		String title = TITLE_BASE + (numHistoriesInDB + 1);
		historyEntity.setTitle(title);
		historyEntity.setIdShare(UUID.randomUUID().toString());
		historyEntity.setUser(user);
		HistoryIaEntity historySaved = historyRepo.save(historyEntity);
		
		MessageUserEntity messageUserEntity = new MessageUserEntity();
		messageUserEntity.setDateCreated(LocalDateTime.now().minusSeconds(2));
		messageUserEntity.setHistory(historySaved);
		messageUserEntity.setMessage(dataHistory.messageUser());
		messageUserEntity.setRol(RolChat.USER);
		messageUserRepo.save(messageUserEntity);
		
		MessageIaEntity messageIa = new MessageIaEntity();
		messageIa.setDateCreated(LocalDateTime.now());
		messageIa.setHistory(historySaved);
		messageIa.setMessage(dataHistory.messageIa());
		messageIa.setRol(RolChat.IA);
		messageIaRepo.save(messageIa);
		
		List<MessageAndRol> messages = List.of(new MessageAndRol(dataHistory.messageUser(), RolChat.USER),
				new MessageAndRol(dataHistory.messageIa(), RolChat.IA));
		
		return new HistoryDataSavedDto(messages, title, historySaved.getIdShare());
	}
	
	private HistoryDataSavedDto updateHistory(HistoryRequestDto dataHistory) {
		HistoryIaEntity historySaved = historyRepo.findByIdShare(dataHistory.idHistory());
		
		MessageUserEntity messageUserEntity = new MessageUserEntity();
		messageUserEntity.setDateCreated(LocalDateTime.now().minusSeconds(2));
		messageUserEntity.setHistory(historySaved);
		messageUserEntity.setMessage(dataHistory.messageUser());
		messageUserEntity.setRol(RolChat.USER);
		messageUserRepo.save(messageUserEntity);
		
		MessageIaEntity messageIa = new MessageIaEntity();
		messageIa.setDateCreated(LocalDateTime.now());
		messageIa.setHistory(historySaved);
		messageIa.setMessage(dataHistory.messageIa());
		messageIa.setRol(RolChat.IA);
		messageIaRepo.save(messageIa);
		
		List<MessageAndRol> messages = List.of(new MessageAndRol(dataHistory.messageUser(), RolChat.USER),
				new MessageAndRol(dataHistory.messageIa(), RolChat.IA));
		
		return new HistoryDataSavedDto(messages, historySaved.getTitle(), historySaved.getIdShare());
	}
	
	private boolean isExistsTitle(String id) {
		return historyRepo.existsByIdShare(id);
	}
	
}
