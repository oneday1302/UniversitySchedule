package ua.foxminded.javaspring.universityschedule.services;

import ua.foxminded.javaspring.universityschedule.dto.CourseDTO;
import ua.foxminded.javaspring.universityschedule.entities.Course;

import java.util.List;

public interface CourseService {

    void add(CourseDTO dto);

    void update(CourseDTO dto);

    List<Course> getAll();

    List<Course> getAllByListId(List<Long> ids);

    Course findById(long id);

    void removeById(long id);
}
