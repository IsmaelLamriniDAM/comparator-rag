package comparator.ia.app.dtos.history;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HistoryRequestDto(@NotBlank @NotNull String messageUser, 
		String messageIa,
		String idHistory
		) {

}
