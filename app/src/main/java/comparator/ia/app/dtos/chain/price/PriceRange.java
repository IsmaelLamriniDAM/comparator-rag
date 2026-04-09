package comparator.ia.app.dtos.chain.price;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Range Price")
public record PriceRange(
		@Schema(name = "max", example = "30000") Double max, 
		@Schema(name = "min", example = "15000") Double min) {
}
