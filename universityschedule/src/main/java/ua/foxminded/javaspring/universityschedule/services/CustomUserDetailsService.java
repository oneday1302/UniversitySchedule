package ua.foxminded.javaspring.universityschedule.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.universityschedule.entities.User;
import ua.foxminded.javaspring.universityschedule.repositories.UserRepository;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        User user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return new CustomUserDetails(user);
    }
}
