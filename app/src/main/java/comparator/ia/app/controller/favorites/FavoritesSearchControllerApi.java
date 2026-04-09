package comparator.ia.app.controller.favorites;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import comparator.ia.app.dtos.auth.UserDataDto;
import comparator.ia.app.dtos.favoritesSearch.FavouritesReceiveDto;
import comparator.ia.app.dtos.favoritesSearch.FavouritesReturnDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Tag(name = "Favourite Search", description = "Controllar Favourite Search")
@RequestMapping("/api/v1/fav") 
public interface FavoritesSearchControllerApi { 
	
	@Operation(summary = "Endpoint for get list data search fav.", description = "Return a list of saved favorite searches.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Return list", content = { 
		            @Content(
		                    mediaType = "application/json", 
		                    schema = @Schema(implementation = FavouritesReturnDto.class)
		                ) 
		            }),
			@ApiResponse(responseCode = "401", description = "the credentials sent it are incorrects. This throw exception (AuthenticationFailedException)",
					content = @Content())
	})
	@GetMapping("/all")
    ResponseEntity<List<FavouritesReturnDto>> searchFavsAll(@Parameter(description = "user session", hidden = true) HttpSession session);


	@Operation(summary = "Endpoint for add search fav.", description = "Will add search fav.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Return list", content = { 
		            @Content(
		                    mediaType = "text/plain", 
		                    schema = @Schema(implementation = Long.class, example = "5")
		                ) 
		            }),
			@ApiResponse(responseCode = "401", description = "the credentials sent it are incorrects. This throw exception (AuthenticationFailedException)",
					content = @Content())
	})
    @PostMapping("/add")
    ResponseEntity<Long> searchFavAdd(
    		@Parameter(description = "Data search") @Valid @RequestBody FavouritesReceiveDto favouritesReceiveDto,
    		@Parameter(description = "user session", hidden = true) HttpSession session);
	
	@Operation(summary = "Endpoint for delete one history fav.", description = "Will detele one history fav.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Search fav has been  Deleted  correct"),
			@ApiResponse(responseCode = "401", description = "the credentials sent it are incorrects. This throw exception (AuthenticationFailedException)",
					content = @Content())
	})
    @DeleteMapping("/remove/{id}")
    ResponseEntity<Void> searchFavRemoveOne(
    		@Parameter(required = true, description = "Unique ID of the story you wish to delete", example = "5")
    		@PathVariable(required = true) Long id,
    		@Parameter(description = "user session", hidden = true) HttpSession session);
    
	@Operation(summary = "Endpoint for delete ALL histories fav.", description = "Will detele ALL histories fav.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Searchs fav have been deleted all correct"),
			@ApiResponse(responseCode = "401", description = "the credentials sent it are incorrects. This throw exception (AuthenticationFailedException)",
					content = @Content())
	})
    @DeleteMapping("/removeAll")
    ResponseEntity<Void> searchFavRemoveAll(@Parameter(description = "user session", hidden = true) HttpSession session);
}
