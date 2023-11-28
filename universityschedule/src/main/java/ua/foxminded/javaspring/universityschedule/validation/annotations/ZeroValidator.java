package ua.foxminded.javaspring.universityschedule.validation.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ZeroValidator implements ConstraintValidator<Zero, Long> {

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        return aLong == 0;
    }
}
