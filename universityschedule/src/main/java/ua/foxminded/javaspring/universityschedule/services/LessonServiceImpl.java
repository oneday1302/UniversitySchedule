package ua.foxminded.javaspring.universityschedule.services;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.universityschedule.dto.LessonDTO;
import ua.foxminded.javaspring.universityschedule.entities.*;
import ua.foxminded.javaspring.universityschedule.repositories.LessonRepository;
import ua.foxminded.javaspring.universityschedule.utils.QPredicates;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository repository;
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final GroupService groupService;
    private final ClassroomService classroomService;

    @Transactional
    @Override
    public void add(LessonDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        Lesson lesson = Lesson.builder()
                              .course(courseService.findById(dto.getCourseId()))
                              .teacher(teacherService.findById(dto.getTeacherId()))
                              .group(groupService.findById(dto.getGroupId()))
                              .classroom(classroomService.findById(dto.getClassroomId()))
                              .date(dto.getDate())
                              .startTime(dto.getStartTime())
                              .endTime(dto.getEndTime())
                              .build();
        repository.save(lesson);
    }

    @Transactional
    @Override
    public void update(LessonDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        Lesson lesson = findById(dto.getId());
        lesson.setCourse(courseService.findById(dto.getCourseId()));
        lesson.setTeacher(teacherService.findById(dto.getTeacherId()));
        lesson.setGroup(groupService.findById(dto.getGroupId()));
        lesson.setClassroom(classroomService.findById(dto.getClassroomId()));
        lesson.setDate(dto.getDate());
        lesson.setStartTime(dto.getStartTime());
        lesson.setEndTime(dto.getEndTime());
        repository.save(lesson);
    }

    @Transactional
    @Override
    public List<Lesson> findByFilter(LessonDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        Course course = null;
        if (dto.getCourseId() != 0) {
            course = courseService.findById(dto.getCourseId());
        }

        Teacher teacher = null;
        if (dto.getTeacherId() != 0) {
            teacher = teacherService.findById(dto.getTeacherId());
        }

        Group group = null;
        if (dto.getGroupId() != 0) {
            group = groupService.findById(dto.getGroupId());
        }

        Classroom classroom = null;
        if (dto.getClassroomId() != 0) {
            classroom = classroomService.findById(dto.getClassroomId());
        }

        LocalDate from = null;
        if (dto.getDateFrom() != null) {
            from = dto.getDateFrom();
        }

        LocalDate to = null;
        if (dto.getDateTo() != null) {
            to = dto.getDateTo();
        }

        if (from == null && to == null) {
            LocalDate initial = LocalDate.now();
            from = initial.withDayOfMonth(1);
            to = initial.withDayOfMonth(initial.lengthOfMonth());
        }

        Predicate predicate = QPredicates.builder()
                                         .add(course, QLesson.lesson.course::eq)
                                         .add(teacher, QLesson.lesson.teacher::eq)
                                         .add(group, QLesson.lesson.group::eq)
                                         .add(classroom, QLesson.lesson.classroom::eq)
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
