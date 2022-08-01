package pl.slichota.licencjant.pracalicencjacka.validator;

import pl.slichota.licencjant.pracalicencjacka.validator.DateTimeConstraint;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;



public class DateTimeValidator implements ConstraintValidator<DateTimeConstraint, Date> {
    @Override
    public void initialize(DateTimeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        Date date1 = new Date();

        return !date.after(date1);
    }


}
