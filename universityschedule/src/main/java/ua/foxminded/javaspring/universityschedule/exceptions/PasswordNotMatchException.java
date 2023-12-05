package ua.foxminded.javaspring.universityschedule.exceptions;

public class PasswordNotMatchException extends RuntimeException {

    public PasswordNotMatchException(String errorMessage) {
        super(errorMessage);
    }
}
