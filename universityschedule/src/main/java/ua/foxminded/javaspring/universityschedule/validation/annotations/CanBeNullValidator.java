package ua.foxminded.javaspring.universityschedule.validation.annotations;

import ua.foxminded.javaspring.universityschedule.validation.annotations.CanBeNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CanBeNullValidator implements ConstraintValidator<CanBeNull, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
