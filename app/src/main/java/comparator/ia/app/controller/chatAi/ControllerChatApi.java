package comparator.ia.app.controller.chatAi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import comparator.ia.app.dtos.chat.RequestDataChatDto;
import comparator.ia.app.dtos.history.HistoryDataSavedDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@Tag(name = "Chat IA", description = "Controllar Chat IA")
@RequestMapping("/api/v1/ia")
public interface ControllerChatApi {

	@Operation(
		    summary = "Chat IA",
		    description = "This is an endpoint for interacting with the AI. If everything goes well, the conversation will automatically be saved in the history."
		)
		@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200", 
		        description = "Data the history saved",
		        content = @Content(
		            mediaType = "application/json",
		            schema = @Schema(implementation = HistoryDataSavedDto.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "401", 
		        description = "Unauthorized - Valid session is required",
		        content = @Content()
		    )
		})
    @PostMapping("/chat")
    ResponseEntity<HistoryDataSavedDto> chat(@Parameter(description = "Data request chat ia") @RequestBody RequestDataChatDto dataDto,
    		@Parameter(description = "user session", hidden = true) HttpSession session);
    
	@Operation(
		    summary = "Greeting IA",
		    description = "This endpoint welcomes the chat with an AI message."
		)
		@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200", 
		        description = "Gretting message IA",
		        content = @Content(
		        		mediaType = "text/plain", 
		                schema = @Schema(
		                    type = "string", 
		                    example = """
		                        ¡Bienvenido a ComparadorAi!
		                        Somos una herramienta de análisis de datos...
		                        ¿Estás buscando comprar o vender un coche? ¡Comienza ahora!
		                        """
		                )
		    )),
		    @ApiResponse(
		        responseCode = "401", 
		        description = "Unauthorized - Valid session is required",
		        content = @Content()
		    )
		})
	/**
	 * @deprecated This endpoint is obsolete.
	 */
	@Deprecated(since = "0.0.1", forRemoval = false)
    @GetMapping("/greeting")
    ResponseEntity<String> getGreeting(@Parameter(description = "user session", hidden = true) HttpSession session);

}
