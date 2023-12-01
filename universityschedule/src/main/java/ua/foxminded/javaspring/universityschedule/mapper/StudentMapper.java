package ua.foxminded.javaspring.universityschedule.mapper;

import ua.foxminded.javaspring.universityschedule.dto.StudentDTO;
import ua.foxminded.javaspring.universityschedule.entities.Student;

public interface StudentMapper {

    StudentDTO studentToDTO(Student student);
}
