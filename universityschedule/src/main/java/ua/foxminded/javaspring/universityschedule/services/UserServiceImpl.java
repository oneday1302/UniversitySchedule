package ua.foxminded.javaspring.universityschedule.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.universityschedule.dto.PasswordDTO;
import ua.foxminded.javaspring.universityschedule.dto.UserDTO;
import ua.foxminded.javaspring.universityschedule.entities.User;
import ua.foxminded.javaspring.universityschedule.exceptions.PasswordNotMatch;
import ua.foxminded.javaspring.universityschedule.repositories.UserRepository;

import java.nio.CharBuffer;

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
        if (!passwordMatches(CharBuffer.wrap(dto.getCurrentPassword()), user.getPassword())) {
            throw new PasswordNotMatch("Current password and old password are not matches!");
        }
        user.setPassword(encoder.encode(CharBuffer.wrap(dto.getNewPassword())));
        repository.save(user);
        dto.invalidate();
    }

    private boolean passwordMatches(CharSequence rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public User findById(long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Entity no found."));
    }

    @Override
    public void removeById(long id) {
        repository.deleteById(id);
    }
}
