package ua.foxminded.javaspring.universityschedule.mapper;

import org.springframework.stereotype.Component;
import ua.foxminded.javaspring.universityschedule.dto.CourseDTO;
import ua.foxminded.javaspring.universityschedule.entities.Course;

@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public CourseDTO courseToDTO(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setName(course.getName());
        return dto;
    }
}
