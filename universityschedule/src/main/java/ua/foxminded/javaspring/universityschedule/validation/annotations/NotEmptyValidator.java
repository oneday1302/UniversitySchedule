package ua.foxminded.javaspring.universityschedule.validation.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyValidator implements ConstraintValidator<NotEmpty, char[]> {

    @Override
    public boolean isValid(char[] chars, ConstraintValidatorContext constraintValidatorContext) {
        if (chars == null) {
            return false;
        }
        return chars.length > 0;
    }
}
