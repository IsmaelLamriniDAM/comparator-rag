package comparator.ia.app.service.favorites;

import java.util.List;
import java.util.UUID;

import comparator.ia.app.dtos.favoritesSearch.FavouritesReceiveDto;
import comparator.ia.app.dtos.favoritesSearch.FavouritesReturnDto;
import jakarta.servlet.http.HttpSession;

public interface FavouritesService {
	
	void removeAll(UUID id);
	
	void removeById(Long id, HttpSession session);
	
	long add(FavouritesReceiveDto favouritesReceiveDto, HttpSession session);
	
	List<FavouritesReturnDto> getAll(UUID id, HttpSession session);
	
}
