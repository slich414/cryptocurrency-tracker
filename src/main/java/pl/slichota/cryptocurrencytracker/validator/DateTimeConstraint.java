package pl.slichota.cryptocurrencytracker.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;



@Documented
@Constraint(validatedBy = DateTimeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)


public @interface DateTimeConstraint {
    String message() default "Selected date is greater than today";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
