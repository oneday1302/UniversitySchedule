package ua.foxminded.javaspring.universityschedule.services;

import ua.foxminded.javaspring.universityschedule.dto.StudentDTO;
import ua.foxminded.javaspring.universityschedule.entities.Student;

import java.util.List;

public interface StudentService {

    void add(StudentDTO dto);

    void update(StudentDTO dto);

    Student findById(long id);

    List<Student> getAll();
}
