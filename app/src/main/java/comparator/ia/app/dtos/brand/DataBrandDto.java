package comparator.ia.app.dtos.brand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataBrandDto(@NotBlank String communName) {
}
