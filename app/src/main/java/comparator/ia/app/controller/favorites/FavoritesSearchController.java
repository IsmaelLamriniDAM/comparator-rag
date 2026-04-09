package comparator.ia.app.controller.favorites;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import comparator.ia.app.dtos.favoritesSearch.FavouritesReceiveDto;
import comparator.ia.app.dtos.favoritesSearch.FavouritesReturnDto;
import comparator.ia.app.handler.exceptions.auth.AuthenticationFailedException;
import comparator.ia.app.service.favorites.FavouritesService;
import comparator.ia.app.util.auth.SessionManager;
import jakarta.servlet.http.HttpSession;

@RestController
public class FavoritesSearchController implements FavoritesSearchControllerApi{

	private final SessionManager sessionManager;
	
	private final FavouritesService favouritesService;
	
	FavoritesSearchController(SessionManager sessionManager, FavouritesService favouritesService){
		this.sessionManager = sessionManager;
		this.favouritesService = favouritesService;}
	
	@Override
	public ResponseEntity<List<FavouritesReturnDto>> searchFavsAll(HttpSession session) {
		if(!sessionManager.checkSession(session)) {
			throw new AuthenticationFailedException();
		}		
		return ResponseEntity.ok(favouritesService.getAll(sessionManager.getKeySession(session).get(), session)	);
	}

	@Override
	public ResponseEntity<Long> searchFavAdd(FavouritesReceiveDto favouritesReceiveDto, HttpSession session) {
		if(!sessionManager.checkSession(session)) {
			throw new AuthenticationFailedException();
		}
		
		return ResponseEntity.ok(favouritesService.add(favouritesReceiveDto, session));
	}

	@Override
	public ResponseEntity<Void> searchFavRemoveOne(Long id, HttpSession session) {
		if(!sessionManager.checkSession(session)) {
			throw new AuthenticationFailedException();
		}
		favouritesService.removeById(id, session);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> searchFavRemoveAll(HttpSession session) {
		if(!sessionManager.checkSession(session)) {
			throw new AuthenticationFailedException();
		}
		
		favouritesService.removeAll(sessionManager.getKeySession(session).get());
		return ResponseEntity.noContent().build();
	}

}
