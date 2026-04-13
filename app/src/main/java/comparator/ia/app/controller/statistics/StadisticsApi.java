package comparator.ia.app.controller.statistics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import comparator.ia.app.dtos.logic.CarCaractersiticsDto;
import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RequestMapping("/api/v1/vehicle") 
public interface StadisticsApi {
	@Operation(summary = "Compare vehicle features.",
			description = "Endopint to analyze profitability with the characteristics of the car received.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true)
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Comparation successfully completed."),
			@ApiResponse(responseCode = "400", description = "The received data is invalid."),
			@ApiResponse(responseCode = "401", description = "The user is not logged in.") 
	})
	@PostMapping("/compare")
	ResponseEntity<StadisticsVehicleChatDto> vehicleCompareForm(@Valid @RequestBody SimilarCarOperationDTO similarCar, HttpSession session);


}
