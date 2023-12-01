package ua.foxminded.javaspring.universityschedule.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.foxminded.javaspring.universityschedule.configs.ServiceTestConfig;
import ua.foxminded.javaspring.universityschedule.dto.PasswordDTO;
import ua.foxminded.javaspring.universityschedule.dto.UserDTO;
import ua.foxminded.javaspring.universityschedule.entities.User;
import ua.foxminded.javaspring.universityschedule.repositories.UserRepository;

import java.nio.CharBuffer;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ServiceTestConfig.class)
public class UserServiceImplTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService service;

    @Autowired
    private PasswordEncoder encoder;

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

    @Test
    void updatePassword_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.updatePassword(null);
        });
    }

    @Test
    void updatePassword_shouldReturnIllegalArgumentException_whenCurrentPasswordAndOldPasswordAreNotMatches() {
        PasswordDTO dto = new PasswordDTO();
        char[] currentPassword = {'1'};
        dto.setCurrentPassword(currentPassword);

        User user = new User();
        user.setPassword("2");
        when(repository.findById(dto.getId())).thenReturn(Optional.of(user));
        when(encoder.matches(CharBuffer.wrap(dto.getCurrentPassword()), user.getPassword())).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.updatePassword(dto);
        });
        assertEquals(exception.getMessage(), "Current password and old password are not matches!");
    }

    @Test
    void updatePassword_shouldUpdateUserPassword() {
        PasswordDTO dto = new PasswordDTO();
        char[] currentPassword = {'1'};
        dto.setCurrentPassword(currentPassword);
        char[] newPassword = {'2'};
        dto.setNewPassword(newPassword);
        char[] passwordConfirmation = {'2'};
        dto.setPasswordConfirmation(passwordConfirmation);

        User user = new User();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(user));
        when(encoder.matches(CharBuffer.wrap(dto.getCurrentPassword()), user.getPassword())).thenReturn(true);

        service.updatePassword(dto);
        verify(repository, times(1)).save(user);
    }
}
