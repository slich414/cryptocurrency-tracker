package pl.slichota.cryptocurrencytracker.validator;

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
