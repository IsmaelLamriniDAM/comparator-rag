package comparator.ia.app.dtos.chain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Range km")
public record Kilometers(
		@Schema(name = "max", example = "50000") Integer max,
		@Schema(name = "min", example = "10000") Integer min) {
}
