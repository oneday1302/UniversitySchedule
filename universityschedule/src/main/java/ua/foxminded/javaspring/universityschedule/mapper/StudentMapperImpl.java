package ua.foxminded.javaspring.universityschedule.mapper;

import org.springframework.stereotype.Component;
import ua.foxminded.javaspring.universityschedule.dto.StudentDTO;
import ua.foxminded.javaspring.universityschedule.dto.TeacherDTO;
import ua.foxminded.javaspring.universityschedule.entities.Student;

@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public StudentDTO studentToDTO(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setUsername(student.getUsername());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setGroup(student.getGroup());
        return dto;
    }

    @Override
    public StudentDTO teacherDTOToStudentDTO(TeacherDTO teacherDTO) {
        if (teacherDTO == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        StudentDTO dto = new StudentDTO();
        dto.setId(teacherDTO.getId());
        dto.setUsername(teacherDTO.getUsername());
        dto.setFirstName(teacherDTO.getFirstName());
        dto.setLastName(teacherDTO.getLastName());
        dto.setEmail(teacherDTO.getEmail());
        return dto;
    }
}
