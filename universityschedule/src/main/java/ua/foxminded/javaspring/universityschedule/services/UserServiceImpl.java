package ua.foxminded.javaspring.universityschedule.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.universityschedule.entities.User;
import ua.foxminded.javaspring.universityschedule.repositories.UserRepository;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public void update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }

    @Override
    public void remove(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.delete(user);
    }

    @Override
    public void removeById(long id) {
        repository.deleteById(id);
    }
}
