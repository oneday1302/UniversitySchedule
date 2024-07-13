package ua.foxminded.javaspring.universityschedule.mapper;

import ua.foxminded.javaspring.universityschedule.dto.ClassroomDTO;
import ua.foxminded.javaspring.universityschedule.entities.Classroom;

public interface ClassroomMapper {

    ClassroomDTO classroomToDTO(Classroom classroom);
}
