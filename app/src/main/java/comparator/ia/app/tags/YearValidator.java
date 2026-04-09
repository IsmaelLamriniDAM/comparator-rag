package comparator.ia.app.tags;

import java.time.Year;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class YearValidator implements ConstraintValidator<BeforeActualYear, Integer> {

	
	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		return value <= Year.now().getValue();
	}
	
}
