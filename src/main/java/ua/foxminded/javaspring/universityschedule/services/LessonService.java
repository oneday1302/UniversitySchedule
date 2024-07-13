package ua.foxminded.javaspring.universityschedule.services;

import ua.foxminded.javaspring.universityschedule.dto.LessonDTO;
import ua.foxminded.javaspring.universityschedule.entities.Lesson;

import java.util.List;

public interface LessonService {

    void add(LessonDTO dto);

    void update(LessonDTO dto);

    List<Lesson> findByFilter(LessonDTO dto);

    Lesson findById(long id);

    List<Lesson> getAll();

    void removeById(long id);
}
