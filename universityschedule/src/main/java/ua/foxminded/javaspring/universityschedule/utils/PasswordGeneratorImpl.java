package ua.foxminded.javaspring.universityschedule.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PasswordGeneratorImpl implements PasswordGenerator {

    private final Random random = new Random();

    @Override
    public char[] generate() {
        int length = 12;
        char[] password = new char[length];
        for(int i = 0; i < length; i++) {
            password[i] = (char) getRandomNum('!', '~');
        }
        return password;
    }

    private int getRandomNum(int min, int max) {
        return random.nextInt(max + 1 - min) + min;
    }
}
