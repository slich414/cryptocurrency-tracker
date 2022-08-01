package pl.slichota.licencjant.pracalicencjacka.validator;

import pl.slichota.licencjant.pracalicencjacka.domain.portfolio.UserCompletedPurchase;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class CompareDatesValidatior implements ConstraintValidator<CompareDatesConstraint, UserCompletedPurchase> {


    @Override
    public void initialize(CompareDatesConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserCompletedPurchase userCompletedPurchase, ConstraintValidatorContext constraintValidatorContext) {
        return !userCompletedPurchase.getTransactionTime().after(userCompletedPurchase.getSoldTime());
    }


}
