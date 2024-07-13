package ua.foxminded.javaspring.universityschedule.mapper;

import org.springframework.stereotype.Component;
import ua.foxminded.javaspring.universityschedule.dto.TeacherDTO;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;

@Component
public class TeacherMapperImpl implements TeacherMapper {

    @Override
    public TeacherDTO teacherToDTO(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        TeacherDTO dto = new TeacherDTO();
        dto.setId(teacher.getId());
        dto.setUsername(teacher.getUsername());
        dto.setFirstName(teacher.getFirstName());
        dto.setLastName(teacher.getLastName());
        dto.setEmail(teacher.getEmail());
        dto.setCourses(teacher.getCourses());
        dto.setAdmin(teacher.isAdmin());
        return dto;
    }
}
