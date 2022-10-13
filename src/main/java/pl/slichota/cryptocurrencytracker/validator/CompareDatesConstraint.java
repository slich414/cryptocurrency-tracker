package pl.slichota.cryptocurrencytracker.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
@Documented
@Constraint(validatedBy = CompareDatesValidatior.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)



public @interface CompareDatesConstraint {
    String message() default "Transaction time cannot be lower than sold time";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
