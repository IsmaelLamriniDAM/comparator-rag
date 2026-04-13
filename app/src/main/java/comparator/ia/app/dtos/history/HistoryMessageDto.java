package comparator.ia.app.dtos.history;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import comparator.ia.app.enums.RolChat;

public record HistoryMessageDto(String message,
		RolChat rol,
		@JsonIgnore LocalDateTime date_created) {

}
