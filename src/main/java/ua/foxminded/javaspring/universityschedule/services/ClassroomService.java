package ua.foxminded.javaspring.universityschedule.services;

import ua.foxminded.javaspring.universityschedule.dto.ClassroomDTO;
import ua.foxminded.javaspring.universityschedule.entities.Classroom;

import java.util.List;

public interface ClassroomService {

    void add(ClassroomDTO dto);

    void update(ClassroomDTO dto);

    Classroom findById(long id);

    List<Classroom> getAll();

    void removeById(long id);
}
