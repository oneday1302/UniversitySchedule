package ua.foxminded.javaspring.universityschedule.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PasswordGeneratorImpl implements PasswordGenerator {

    private final Random random = new Random();

    @Override
    public String generate() {
        StringBuilder password = new StringBuilder();
        for(int i = 0; i < 12; i++) {
            password.append((char)getRandomNum('!', '~'));
        }

        return password.toString();
    }

    private int getRandomNum(int min, int max) {
        return random.nextInt(max + 1 - min) + min;
    }
}
