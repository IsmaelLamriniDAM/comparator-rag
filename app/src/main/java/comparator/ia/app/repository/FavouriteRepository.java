package comparator.ia.app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import comparator.ia.app.entities.FavouritesEntity;
import comparator.ia.app.entities.UserEntity;

public interface FavouriteRepository extends JpaRepository<FavouritesEntity, Long>{
	void deleteAllByUser(UserEntity entity);
	
	void deleteByIdAndUser_Id(long id, UUID idSession);
	
	@Transactional
	void deleteByUser_Id(UUID id);
	
	List<FavouritesEntity>findAllByUser_Id(UUID id);
}
