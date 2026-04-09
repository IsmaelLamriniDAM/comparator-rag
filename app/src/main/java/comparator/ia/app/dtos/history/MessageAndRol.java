package comparator.ia.app.dtos.history;

import comparator.ia.app.enums.RolChat;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Structure of an individual message and the role of the sender.")
public record MessageAndRol(
		@Schema(description = "Content of message", example = "Hola me quiero comprar un bmw.") String message,
		@Schema(description = "Role of the issuer", example = "USER", allowableValues = {"USER", "IA"}) RolChat rol) {

}
