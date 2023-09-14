package ua.foxminded.javaspring.universityschedule.services;

import ua.foxminded.javaspring.universityschedule.entities.Student;

import java.util.List;

public interface StudentService {

    void add(Student student);

    void update(Student student);

    Student findById(long id);

    List<Student> getAll();

    void remove(Student student);
}
