package ua.foxminded.javaspring.universityschedule.services;

import ua.foxminded.javaspring.universityschedule.entities.Teacher;

import java.util.List;

public interface TeacherService {

    void add(Teacher teacher);

    void update(Teacher teacher);

    List<Teacher> getAll();

    Teacher findById(long id);

    void remove(Teacher teacher);
}
