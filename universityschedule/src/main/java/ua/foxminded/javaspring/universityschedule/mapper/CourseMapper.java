package ua.foxminded.javaspring.universityschedule.mapper;

import ua.foxminded.javaspring.universityschedule.dto.CourseDTO;
import ua.foxminded.javaspring.universityschedule.entities.Course;

public interface CourseMapper {

    CourseDTO courseToDTO(Course course);
}
