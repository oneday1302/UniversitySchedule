package ua.foxminded.javaspring.universityschedule.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.foxminded.javaspring.universityschedule.dto.GroupDTO;
import ua.foxminded.javaspring.universityschedule.entities.Group;
import ua.foxminded.javaspring.universityschedule.repositories.GroupRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GroupServiceImplTest {

    @MockBean
    private GroupRepository repository;

    @Autowired
    private GroupService service;

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.add(null);
        });
    }

    @Test
    void add_whenInputParamGroupDTO() {
        GroupDTO dto = new GroupDTO();
        service.add(dto);
        Group group = new Group();
        verify(repository, times(1)).save(group);
    }

    @Test
    void update_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.update(null);
        });
    }

    @Test
    void update_whenInputParamGroupDTO() {
        GroupDTO dto = new GroupDTO();
        dto.setId(1);
        Group group = new Group();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(group));
        service.update(dto);
        verify(repository, times(1)).save(group);
    }
}
