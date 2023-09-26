package ua.foxminded.javaspring.universityschedule.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ua.foxminded.javaspring.universityschedule.dto.UserDTO;
import ua.foxminded.javaspring.universityschedule.entities.User;

public interface UserService {

    User editUserData(User user, UserDTO dto);

    public User findByUsername(String username);

    User findById(long id);

    void removeById(long id);
}
