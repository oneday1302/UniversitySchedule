package ua.foxminded.javaspring.universityschedule.mapper;

import org.springframework.stereotype.Component;
import ua.foxminded.javaspring.universityschedule.dto.ClassroomDTO;
import ua.foxminded.javaspring.universityschedule.entities.Classroom;

@Component
public class ClassroomMapperImpl implements ClassroomMapper {

    @Override
    public ClassroomDTO classroomToDTO(Classroom classroom) {
        if (classroom == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        ClassroomDTO dto = new ClassroomDTO();
        dto.setId(classroom.getId());
        dto.setName(classroom.getName());
        return dto;
    }
}
