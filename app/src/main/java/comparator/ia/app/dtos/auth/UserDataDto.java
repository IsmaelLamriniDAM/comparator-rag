package comparator.ia.app.dtos.auth;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Information about the logged-in user")
public record UserDataDto(
		@Schema(name = "username", example = "Arthur") String name,
		@Schema(name = "phoneNumber", example = "123234532@") String phoneNumber,
		@Schema(
			    description = "Fecha y hora de la última actualización del registro", 
			    example = "2026-04-09T10:15:30", 
			    type = "string", 
			    format = "date-time"
			) LocalDateTime updatedAt,
		@Schema(name = "numComparinsons", example = "100") Integer numComparinsons) {

}
