package ua.foxminded.javaspring.universityschedule.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.universityschedule.dto.PasswordDTO;
import ua.foxminded.javaspring.universityschedule.dto.UserDTO;
import ua.foxminded.javaspring.universityschedule.entities.User;
import ua.foxminded.javaspring.universityschedule.repositories.UserRepository;

import java.nio.CharBuffer;
import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public User editUserData(User user, UserDTO dto) {
        if (user == null || dto == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        if (dto.getFirstName() != null) {
            user.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null)  {
            user.setLastName(dto.getLastName());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        return user;
    }

    @Override
    public void updatePassword(PasswordDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        User user = findById(dto.getId());
        if (!passwordIsMatches(CharBuffer.wrap(dto.getCurrentPassword()), user.getPassword())) {
            throw new IllegalArgumentException("Current password and old password are not matches!");
        }
        if (!passwordIsEquals(dto.getNewPassword(), dto.getPasswordConfirmation())) {
            throw new IllegalArgumentException("New password and password confirmation are not equals!");
        }

        user.setPassword(encoder.encode(CharBuffer.wrap(dto.getNewPassword())));
        repository.save(user);

        Arrays.fill(dto.getCurrentPassword(), '\0');
        Arrays.fill(dto.getNewPassword(), '\0');
        Arrays.fill(dto.getPasswordConfirmation(), '\0');
    }

    @Override
    public User findById(long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Entity no found."));
    }

    @Override
    public void removeById(long id) {
        repository.deleteById(id);
    }

    private boolean passwordIsMatches(CharSequence rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

    private boolean passwordIsEquals(char[] password1, char[] password2) {
        return Arrays.equals(password1, password2);
    }
}
