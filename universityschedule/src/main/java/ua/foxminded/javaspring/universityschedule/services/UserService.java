package ua.foxminded.javaspring.universityschedule.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ua.foxminded.javaspring.universityschedule.entities.User;

public interface UserService extends UserDetailsService {

    void update(User user);


    void remove(User user);

    void removeById(long id);
}
