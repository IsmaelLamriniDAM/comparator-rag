package comparator.ia.app.controller.brandModel;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import comparator.ia.app.dtos.brandModel.BrandModelSend;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@Tag(name = "Brand and Model", description = "Controllar Brand and Model")
@RequestMapping("api/v1/catalog")
public interface BrandModelControllerApi {
	
	@Operation(
		    summary = "Get all Brands with their models",
		    description = "Retrieves a complete list of vehicle brands along with their associated models."
		)
		@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200", 
		        description = "List of brands with models retrieved successfully",
		        content = @Content(
		            mediaType = "application/json",
		            array = @ArraySchema(schema = @Schema(implementation = BrandModelSend.class))
		        )
		    ),
		    @ApiResponse(
		        responseCode = "401", 
		        description = "Unauthorized - Valid session is required",
		        content = @Content()
		    )
		})
	@GetMapping("/brandModels")
	ResponseEntity<List<BrandModelSend>> getBrandWithHisModels(@Parameter(description = "user session", hidden = true) HttpSession session);
	
	
}
