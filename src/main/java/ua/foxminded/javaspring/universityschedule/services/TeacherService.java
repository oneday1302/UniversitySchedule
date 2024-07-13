package ua.foxminded.javaspring.universityschedule.services;

import ua.foxminded.javaspring.universityschedule.dto.TeacherDTO;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;

import java.util.List;

public interface TeacherService {

    void add(TeacherDTO dto);

    void update(TeacherDTO dto);

    List<Teacher> getAll();

    Teacher findById(long id);
}
