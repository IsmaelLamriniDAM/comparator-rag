package comparator.ia.app.controller.brandModel;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import comparator.ia.app.dtos.brandModel.BrandModelSend;
import comparator.ia.app.handler.exceptions.auth.AuthenticationFailedException;
import comparator.ia.app.service.brand.BrandService;
import comparator.ia.app.util.auth.SessionManager;
import jakarta.servlet.http.HttpSession;

@RestController
public class BrandModelController implements BrandModelControllerApi {

	private final BrandService service;
	
	private final SessionManager sessionManager;
	
	BrandModelController(BrandService service, SessionManager sessionManager) {
		this.service = service;
		this.sessionManager = sessionManager;}
	
	
	@Override
	public ResponseEntity<List<BrandModelSend>> getBrandWithHisModels(HttpSession session) {
		if(sessionManager.checkSession(session)) {
			return ResponseEntity.ok(service.getAllBrandsWithHisModels());
		}
		
		throw new AuthenticationFailedException();
	}


}
