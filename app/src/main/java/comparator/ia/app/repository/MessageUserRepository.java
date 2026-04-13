package comparator.ia.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import comparator.ia.app.entities.HistoryIaEntity;
import comparator.ia.app.entities.MessageIaEntity;
import comparator.ia.app.entities.MessageUserEntity;

public interface MessageUserRepository extends JpaRepository<MessageUserEntity, UUID> {
	
	MessageUserEntity findByHistory(HistoryIaEntity history);
}
