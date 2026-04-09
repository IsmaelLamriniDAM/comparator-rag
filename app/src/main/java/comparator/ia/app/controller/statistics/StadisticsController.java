package comparator.ia.app.controller.statistics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.handler.exceptions.auth.AuthenticationFailedException;
import comparator.ia.app.service.stadistic.StadisticsServiceApi;
import comparator.ia.app.service.user.UserService;
import comparator.ia.app.util.auth.SessionManager;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@RestController
@Tag(name = "Controller-Compare-stadistics")
public class StadisticsController implements StadisticsApi{

	private final StadisticsServiceApi statistics;
	
	private final SessionManager sesionManager;
	
	private final UserService userService;
	
	public StadisticsController(StadisticsServiceApi statistics, SessionManager sesionManager, UserService userService) {
		this.statistics = statistics;
		this.sesionManager = sesionManager;
		this.userService = userService;
	}

	@Override
	public ResponseEntity<StadisticsVehicleChatDto> vehicleCompareForm(@Valid @RequestBody SimilarCarOperationDTO similarCar, HttpSession session) {
		if(!sesionManager.checkSession(session)) {
			throw new AuthenticationFailedException();
		} 
		
		userService.addComparisons(sesionManager.getKeySession(session).get());
		return ResponseEntity
				.ok(statistics.getStatistics(similarCar));
	}

}
 