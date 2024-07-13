package ua.foxminded.javaspring.universityschedule.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.foxminded.javaspring.universityschedule.configs.ServiceTestConfig;
import ua.foxminded.javaspring.universityschedule.dto.ClassroomDTO;
import ua.foxminded.javaspring.universityschedule.entities.Classroom;
import ua.foxminded.javaspring.universityschedule.repositories.ClassroomRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ServiceTestConfig.class)
public class ClassroomServiceImplTest {

    @Autowired
    private ClassroomRepository repository;

    @Autowired
    private ClassroomService service;

    @BeforeEach
    public void mockReset() {
        reset(repository);
    }

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.add(null);
        });
    }

    @Test
    void add_shouldAddClassroomToDatabase() {
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
    void update_shouldUpdateClassroom() {
        ClassroomDTO dto = new ClassroomDTO();
        Classroom classroom = new Classroom();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(classroom));
        service.update(dto);
        verify(repository, times(1)).save(classroom);
    }
}
