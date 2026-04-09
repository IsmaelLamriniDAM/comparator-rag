package comparator.ia.app.dtos.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Request data chat ia")
public record RequestDataChatDto(
		@Schema(name = "messageUser", example = "Quiero comprarme un bmw.", nullable = false) @NotNull @NotBlank String messageUser,
		@Schema(name = "idHistory", example = "131141-4134131-1313131-41241312",
		description = "This ID will be null on the first interaction with the AI ​​in a new conversation.") String idHistory) {

}
