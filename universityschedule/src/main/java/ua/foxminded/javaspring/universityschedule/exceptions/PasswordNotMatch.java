package ua.foxminded.javaspring.universityschedule.exceptions;

public class PasswordNotMatch extends RuntimeException {

    public PasswordNotMatch(String errorMessage) {
        super(errorMessage);
    }
}
