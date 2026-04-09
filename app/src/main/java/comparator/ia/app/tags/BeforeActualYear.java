package comparator.ia.app.tags;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = YearValidator.class)
public @interface BeforeActualYear {
	String message() default "exceed the current year";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
