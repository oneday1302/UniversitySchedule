package ua.foxminded.javaspring.universityschedule.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.foxminded.javaspring.universityschedule.configs.ServiceTestConfig;
import ua.foxminded.javaspring.universityschedule.dto.StudentDTO;
import ua.foxminded.javaspring.universityschedule.entities.Role;
import ua.foxminded.javaspring.universityschedule.entities.Student;
import ua.foxminded.javaspring.universityschedule.repositories.StudentRepository;
import ua.foxminded.javaspring.universityschedule.utils.PasswordGenerator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ServiceTestConfig.class)
@MockBean(EmailService.class)
@MockBean(GroupService.class)
public class StudentServiceImplTest {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private PasswordGenerator generator;

    @MockBean
    private UserService userService;

    @Autowired
    private StudentService service;

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.add(null);
        });
    }

    @Test
    void add_whenInputParamStudentDTO() {
        StudentDTO dto = new StudentDTO();
        char[] password = {'1'};
        when(generator.generate()).thenReturn(password);
        service.add(dto);
        Student student = new Student();
        student.addRole(Role.STUDENT);
        verify(repository, times(1)).save(student);
    }

    @Test
    void update_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.update(null);
        });
    }

    @Test
    void update_whenInputParamStudentDTO() {
        StudentDTO dto = new StudentDTO();
        Student student = new Student();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(student));
        when(userService.editUserData(student, dto)).thenReturn(student);
        service.update(dto);
        verify(repository, times(1)).save(student);
    }
}
