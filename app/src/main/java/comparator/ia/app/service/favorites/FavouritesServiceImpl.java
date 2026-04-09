package comparator.ia.app.service.favorites;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import comparator.ia.app.dtos.chain.HorsePower;
import comparator.ia.app.dtos.chain.Kilometers;
import comparator.ia.app.dtos.chain.price.PriceRange;
import comparator.ia.app.dtos.favoritesSearch.FavouritesReceiveDto;
import comparator.ia.app.dtos.favoritesSearch.FavouritesReturnDto;
import comparator.ia.app.entities.FavouritesEntity;
import comparator.ia.app.handler.exceptions.auth.AuthenticationFailedException;
import comparator.ia.app.repository.FavouriteRepository;
import comparator.ia.app.service.auth.AuthService;
import comparator.ia.app.service.manager.objUseInManager.Price;
import comparator.ia.app.util.auth.SessionManager;
import jakarta.servlet.http.HttpSession;

@Service
public class FavouritesServiceImpl implements FavouritesService{

	private final FavouriteRepository favouriteRepository;
	private final AuthService userService;
	private final SessionManager sessionManager;
	
	FavouritesServiceImpl(FavouriteRepository favouriteRepository, AuthService userService, SessionManager sessionManager){
		this.favouriteRepository = favouriteRepository;
		this.userService = userService;	
		this.sessionManager = sessionManager;
	}
	
	@Override
	public void removeAll(UUID id) {	
		if (!userService.existsUser(id)) {
			throw new AuthenticationFailedException();		
		}
		favouriteRepository.deleteByUser_Id(id);
	}

	@Override
	public void removeById(Long id, HttpSession session) {
		favouriteRepository.deleteById(sessionManager.decodeId(session, id));
	}

	@Override 
	public long add(FavouritesReceiveDto favouritesReceiveDto, HttpSession session) {
		UUID userId = sessionManager.getKeySession(session)
		        .orElseThrow(() -> new AuthenticationFailedException("Sesión inválida o expirada"));
		if (!userService.existsUser(userId)) {
			session.invalidate();
			throw new AuthenticationFailedException("El usuario vinculado a esta sesión ya no existe.");
		}
		
		FavouritesEntity favouriteEntity = new FavouritesEntity();		
		favouriteEntity.setBrand(favouritesReceiveDto.getBrand());
		favouriteEntity.setModel(favouritesReceiveDto.getModel());
		favouriteEntity.setMaxHp(favouritesReceiveDto.getHp().max());
		favouriteEntity.setMinHp(favouritesReceiveDto.getHp().min());
		favouriteEntity.setPriceMaxBuy(favouritesReceiveDto.getPrice().getRangeBuy() == null ? null : favouritesReceiveDto.getPrice().getRangeBuy().max());
		favouriteEntity.setPriceMinBuy(favouritesReceiveDto.getPrice().getRangeBuy() == null ? null : favouritesReceiveDto.getPrice().getRangeBuy().min());
		favouriteEntity.setPriceMaxSell(favouritesReceiveDto.getPrice().getRangeSell() == null ? null : favouritesReceiveDto.getPrice().getRangeSell().max());
		favouriteEntity.setPriceMinSell(favouritesReceiveDto.getPrice().getRangeSell() == null ? null : favouritesReceiveDto.getPrice().getRangeSell().min());
		favouriteEntity.setMaxKilometers(favouritesReceiveDto.getKm().max());
		favouriteEntity.setMinKilometers(favouritesReceiveDto.getKm().min());
		favouriteEntity.setYear(favouritesReceiveDto.getYear());
		favouriteEntity.setFuelType(favouritesReceiveDto.getFuelType());
		favouriteEntity.setOperation(favouritesReceiveDto.getOperation());
		favouriteEntity.setRecommendation(favouritesReceiveDto.getRecommendation());
		favouriteEntity.setUser(userService.getUserEntity(userId));
		 	
		return sessionManager.encodeId(session, favouriteRepository.save(favouriteEntity).getId());
	}

	@Override
	public List<FavouritesReturnDto> getAll(UUID id, HttpSession session) {
		
		return favouriteRepository.findAllByUser_Id(id).stream().map(search -> {
			FavouritesReturnDto dto = new FavouritesReturnDto();
			dto.setBrand(search.getBrand());
			dto.setModel(search.getModel());
			Price price = new Price();
			price.setRangeBuy(new PriceRange(search.getPriceMaxBuy(), search.getPriceMinBuy()));
			price.setRangeSell(new PriceRange(search.getPriceMaxSell(), search.getPriceMinSell()));
			dto.setOperation(search.getOperation());
			dto.setHp(new HorsePower(search.getMaxHp(), search.getMinHp()));
			dto.setKm(new Kilometers(search.getMaxKilometers(), search.getMinKilometers()));
			dto.setPrice(price);
			dto.setYear(search.getYear());
			dto.setFuelType(search.getFuelType());
			dto.setRecommendation(search.getRecommendation());
			dto.setId(sessionManager.encodeId(session, search.getId()));
			return dto;
		}).toList();
	}

}
