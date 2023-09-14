package ua.foxminded.javaspring.universityschedule.services;

import ua.foxminded.javaspring.universityschedule.entities.Lesson;
import ua.foxminded.javaspring.universityschedule.utils.LessonFilter;

import java.util.List;

public interface LessonService {

    void add(Lesson lesson);

    void update(Lesson lesson);

    List<Lesson> findByFilter(LessonFilter filter);

    Lesson findById(long id);

    List<Lesson> getAll();

    void remove(Lesson lesson);

    void removeById(long id);
}
