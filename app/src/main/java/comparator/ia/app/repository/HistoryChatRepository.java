package comparator.ia.app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import comparator.ia.app.entities.HistoryIaEntity;

public interface HistoryChatRepository extends JpaRepository<HistoryIaEntity,UUID> {
	
	List<HistoryIaEntity> findAllByUser_id(UUID id);
	
	HistoryIaEntity findByIdShareAndUser_id(String idHistoryShare, UUID id);
	
	@Query(value = "SELECT COUNT(*) FROM history_ia", nativeQuery = true)
	int countRegisterHistories();
	
	boolean existsByTitle(String title);
	
	boolean existsByIdShare(String idHistory);
	
	HistoryIaEntity findByIdShare(String idHistory);
	
	@Transactional
	@Modifying
	int deleteByUser_id(UUID idUser);

}
