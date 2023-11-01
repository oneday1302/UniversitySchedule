package ua.foxminded.javaspring.universityschedule.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.foxminded.javaspring.universityschedule.configs.ServiceTestConfig;
import ua.foxminded.javaspring.universityschedule.dto.UserDTO;
import ua.foxminded.javaspring.universityschedule.entities.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = ServiceTestConfig.class)
public class UserServiceImplTest {

    @Autowired
    private UserService service;

    @Test
    void editUserData_shouldReturnIllegalArgumentException_whenInputFirstParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.editUserData(null, new UserDTO());
        });
    }

    @Test
    void editUserData_shouldReturnIllegalArgumentException_whenInputSecondParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.editUserData(new User(), null);
        });
    }

    @Test
    void editUserData_shouldReturnIllegalArgumentException_whenInputAllParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.editUserData(null, null);
        });
    }

    @Test
    void editUserData_shouldReturnUpdatedUser_whenInputParamUserAndUserDTO() {
        User user = new User();
        UserDTO dto = new UserDTO();
        assertEquals(user, service.editUserData(user, dto));
    }
}
