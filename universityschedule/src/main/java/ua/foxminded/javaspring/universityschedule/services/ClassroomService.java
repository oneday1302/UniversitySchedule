package ua.foxminded.javaspring.universityschedule.services;

import ua.foxminded.javaspring.universityschedule.entities.Classroom;

import java.util.List;

public interface ClassroomService {

    void add(Classroom classroom);

    void update(Classroom classroom);

    Classroom findById(long id);

    Classroom findByName(String name);

    List<Classroom> getAll();

    void remove(Classroom classroom);

    void removeById(long id);
}
