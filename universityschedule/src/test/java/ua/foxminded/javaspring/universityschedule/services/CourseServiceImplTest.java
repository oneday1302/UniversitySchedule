package ua.foxminded.javaspring.universityschedule.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.foxminded.javaspring.universityschedule.dto.CourseDTO;
import ua.foxminded.javaspring.universityschedule.entities.Course;
import ua.foxminded.javaspring.universityschedule.repositories.CourseRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CourseServiceImplTest {

    @MockBean
    private CourseRepository repository;

    @Autowired
    CourseService service;

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.add(null);
        });
    }

    @Test
    void add_whenInputParamCourseDTO() {
        CourseDTO dto = new CourseDTO();
        service.add(dto);
        Course course = new Course();
        verify(repository, times(1)).save(course);
    }

    @Test
    void update_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.update(null);
        });
    }

    @Test
    void update_whenInputParamCourseDTO() {
        CourseDTO dto = new CourseDTO();
        dto.setId(1);
        Course course = new Course();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(course));
        service.update(dto);
        verify(repository, times(1)).save(course);
    }
}
