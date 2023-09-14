package ua.foxminded.javaspring.universityschedule.services;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.universityschedule.entities.Lesson;
import ua.foxminded.javaspring.universityschedule.entities.QLesson;
import ua.foxminded.javaspring.universityschedule.repositories.LessonRepository;
import ua.foxminded.javaspring.universityschedule.utils.LessonFilter;
import ua.foxminded.javaspring.universityschedule.utils.QPredicates;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository repository;

    @Override
    public void add(Lesson lesson) {
        if (lesson == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.save(lesson);
    }

    @Override
    public void update(Lesson lesson) {
        if (lesson == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.save(lesson);
    }

    @Override
    public List<Lesson> findByFilter(LessonFilter filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        Predicate predicate = QPredicates.builder().add(filter.getCourse(), QLesson.lesson.course::eq)
                                                   .add(filter.getTeacher(), QLesson.lesson.teacher::eq)
                                                   .add(filter.getGroup(), QLesson.lesson.group::eq)
                                                   .add(filter.getClassroom(), QLesson.lesson.classroom::eq)
                                                   .add(filter.getFrom(), QLesson.lesson.date::after)
                                                   .add(filter.getTo(), QLesson.lesson.date::before)
                                                   .buildAnd();

        return Streamable.of(repository.findAll(predicate)).toList();
    }

    @Override
    public Lesson findById(long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Entity no found."));
    }

    @Override
    public List<Lesson> getAll() {
        return repository.findAll();
    }

    @Override
    public void remove(Lesson lesson) {
        if (lesson == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.delete(lesson);
    }

    @Override
    public void removeById(long id) {
        repository.deleteById(id);
    }
}
