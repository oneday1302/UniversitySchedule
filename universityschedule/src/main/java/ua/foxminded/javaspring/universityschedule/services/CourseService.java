package ua.foxminded.javaspring.universityschedule.services;

import ua.foxminded.javaspring.universityschedule.entities.Course;

import java.util.List;

public interface CourseService {

    void add(Course course);

    void update(Course course);

    List<Course> getAll();

    Course findById(long id);

    Course findByName(String name);

    void remove(Course course);

    void removeById(long id);
}
