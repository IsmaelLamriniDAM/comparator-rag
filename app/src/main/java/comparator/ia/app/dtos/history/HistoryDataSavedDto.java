package comparator.ia.app.dtos.history;

import java.util.List;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Information the conversation saved")
public record HistoryDataSavedDto(
		@ArraySchema(
		        schema = @Schema(description = "Chronological list of messages", 
		        		example = """
		                [
		                  {
		                    "message": "Hola me quiero comprar un bmw.",
		                    "rol": "USER"
		                  },
		                  {
		                    "message": "¡Excelente elección! Los BMW son conocidos por su dinamismo. ¿Buscas algún modelo o serie en particular?",
		                    "rol": "ASSISTANT"
		                  }
		                ]
		                """)
		    ) List<MessageAndRol> messagesAndRol,
		@Schema(name = "historyTitle", example = "CHAT 1") String title,
		@Schema(name = "idHistory", example = "131141-4134131-1313131-41241312") String idHistory) {
}
