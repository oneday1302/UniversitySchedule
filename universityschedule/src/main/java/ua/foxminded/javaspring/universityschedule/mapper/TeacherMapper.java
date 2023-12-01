package ua.foxminded.javaspring.universityschedule.mapper;

import ua.foxminded.javaspring.universityschedule.dto.TeacherDTO;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;

public interface TeacherMapper {

    TeacherDTO teacherToDTO(Teacher teacher);
}
