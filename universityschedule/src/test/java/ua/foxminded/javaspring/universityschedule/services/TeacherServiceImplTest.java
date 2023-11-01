package ua.foxminded.javaspring.universityschedule.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.foxminded.javaspring.universityschedule.configs.ServiceTestConfig;
import ua.foxminded.javaspring.universityschedule.dto.TeacherDTO;
import ua.foxminded.javaspring.universityschedule.entities.Role;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.repositories.TeacherRepository;
import ua.foxminded.javaspring.universityschedule.utils.PasswordGenerator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ServiceTestConfig.class)
@MockBean(CourseService.class)
@MockBean(EmailService.class)
public class TeacherServiceImplTest {

    @Autowired
    private TeacherRepository repository;

    @Autowired
    private PasswordGenerator generator;

    @MockBean
    private UserService userService;

    @Autowired
    private TeacherService service;

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.add(null);
        });
    }

    @Test
    void add_whenInputParamTeacherDTO() {
        TeacherDTO dto = new TeacherDTO();
        char[] password = {'1'};
        when(generator.generate()).thenReturn(password);
        service.add(dto);
        Teacher teacher = new Teacher();
        teacher.addRole(Role.TEACHER);
        verify(repository, times(1)).save(teacher);
    }

    @Test
    void update_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.update(null);
        });
    }

    @Test
    void update_whenInputParamTeacherDTO() {
        TeacherDTO dto = new TeacherDTO();
        Teacher teacher = new Teacher();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(teacher));
        when(userService.editUserData(teacher, dto)).thenReturn(teacher);
        service.update(dto);
        verify(repository, times(1)).save(teacher);
    }
}
