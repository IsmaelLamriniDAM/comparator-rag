package comparator.ia.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import comparator.ia.app.entities.HistoryIaEntity;
import comparator.ia.app.entities.MessageIaEntity;

public interface MessageIaRepository extends JpaRepository<MessageIaEntity, UUID> {
	
	MessageIaEntity findByHistory(HistoryIaEntity history);
	
}
