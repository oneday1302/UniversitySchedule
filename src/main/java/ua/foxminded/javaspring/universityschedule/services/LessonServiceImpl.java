package ua.foxminded.javaspring.universityschedule.services;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.universityschedule.dto.LessonDTO;
import ua.foxminded.javaspring.universityschedule.entities.*;
import ua.foxminded.javaspring.universityschedule.repositories.LessonRepository;
import ua.foxminded.javaspring.universityschedule.utils.QPredicates;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository repository;

    @Override
    public void add(LessonDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        Lesson lesson = Lesson.builder()
                              .course(dto.getCourse())
                              .teacher(dto.getTeacher())
                              .group(dto.getGroup())
                              .classroom(dto.getClassroom())
                              .date(dto.getDate())
                              .startTime(dto.getStartTime())
                              .endTime(dto.getEndTime())
                              .build();
        repository.save(lesson);
    }

    @Override
    public void update(LessonDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        Lesson lesson = findById(dto.getId());
        lesson.setCourse(dto.getCourse());
        lesson.setTeacher(dto.getTeacher());
        lesson.setGroup(dto.getGroup());
        lesson.setClassroom(dto.getClassroom());
        lesson.setDate(dto.getDate());
        lesson.setStartTime(dto.getStartTime());
        lesson.setEndTime(dto.getEndTime());
        repository.save(lesson);
    }

    @Override
    public List<Lesson> findByFilter(LessonDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        LocalDate from = dto.getDateFrom();
        LocalDate to = dto.getDateTo();
        if (from == null && to == null) {
            LocalDate initial = LocalDate.now();
            from = initial.withDayOfMonth(1);
            to = initial.withDayOfMonth(initial.lengthOfMonth());
        }

        Predicate predicate = QPredicates.builder()
                                         .add(dto.getCourse(), QLesson.lesson.course::eq)
                                         .add(dto.getTeacher(), QLesson.lesson.teacher::eq)
                                         .add(dto.getGroup(), QLesson.lesson.group::eq)
                                         .add(dto.getClassroom(), QLesson.lesson.classroom::eq)
                                         .add(from, QLesson.lesson.date::after)
                                         .add(to, QLesson.lesson.date::before)
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
    public void removeById(long id) {
        repository.deleteById(id);
    }
}
