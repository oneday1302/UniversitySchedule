package ua.foxminded.javaspring.universityschedule.services;

import ua.foxminded.javaspring.universityschedule.dto.PasswordDTO;
import ua.foxminded.javaspring.universityschedule.dto.UserDTO;
import ua.foxminded.javaspring.universityschedule.entities.User;

public interface UserService {

    User editUserData(User user, UserDTO dto);

    void updatePassword(PasswordDTO dto);

    User findById(long id);

    void removeById(long id);
}
