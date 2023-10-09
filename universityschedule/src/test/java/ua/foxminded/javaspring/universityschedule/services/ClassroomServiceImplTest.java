package ua.foxminded.javaspring.universityschedule.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.foxminded.javaspring.universityschedule.dto.ClassroomDTO;
import ua.foxminded.javaspring.universityschedule.entities.Classroom;
import ua.foxminded.javaspring.universityschedule.repositories.ClassroomRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClassroomServiceImplTest {

    @MockBean
    private ClassroomRepository repository;

    @Autowired
    private ClassroomService service;

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.add(null);
        });
    }

    @Test
    void add_whenInputParamClassroomDTO() {
        ClassroomDTO dto = new ClassroomDTO();
        service.add(dto);
        Classroom classroom = new Classroom();
        verify(repository, times(1)).save(classroom);
    }

    @Test
    void update_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.update(null);
        });
    }

    @Test
    void update_whenInputParamClassroomDTO() {
        ClassroomDTO dto = new ClassroomDTO();
        dto.setId(1);
        Classroom classroom = new Classroom();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(classroom));
        service.update(dto);
        verify(repository, times(1)).save(classroom);
    }
}
