package comparator.ia.app.dtos.brandModel;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Info Brands and his models")
public record BrandModelSend(
		@Schema(name = "brandName", example = "AIWAYS") String brand,
		@Schema(
		        description = "List of names of models associated with the brand.", 
		        example = "['U5', 'U6', 'U7']"
		    )
		List<String> models
		) {

}
