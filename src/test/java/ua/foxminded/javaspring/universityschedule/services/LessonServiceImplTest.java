package ua.foxminded.javaspring.universityschedule.services;

import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.foxminded.javaspring.universityschedule.configs.ServiceTestConfig;
import ua.foxminded.javaspring.universityschedule.dto.LessonDTO;
import ua.foxminded.javaspring.universityschedule.entities.Lesson;
import ua.foxminded.javaspring.universityschedule.entities.QLesson;
import ua.foxminded.javaspring.universityschedule.repositories.LessonRepository;
import ua.foxminded.javaspring.universityschedule.utils.QPredicates;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = ServiceTestConfig.class)
public class LessonServiceImplTest {

    @Autowired
    private LessonRepository repository;

    @Autowired
    private LessonService service;

    @BeforeEach
    public void mockReset() {
        reset(repository);
    }

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.add(null);
        });
    }

    @Test
    void add_shouldAddLessonToDatabase() {
        LessonDTO dto = new LessonDTO();
        service.add(dto);
        Lesson lesson = new Lesson();
        verify(repository, times(1)).save(lesson);
    }

    @Test
    void update_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.update(null);
        });
    }

    @Test
    void update_shouldUpdateLesson() {
        LessonDTO dto = new LessonDTO();
        Lesson lesson = new Lesson();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(lesson));
        service.update(dto);
        verify(repository, times(1)).save(lesson);
    }

    @Test
    void findByFilter_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.findByFilter(null);
        });
    }

    @Test
    void findByFilter_shouldReturnListOfLesson_whenInputParamLessonDTO() {
        Lesson lesson = Lesson.builder()
                              .date(LocalDate.of(2023, 10, 9))
                              .build();
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(lesson);

        LessonDTO dto = new LessonDTO();
        dto.setDateFrom(LocalDate.of(2023, 10, 8));
        dto.setDateTo(LocalDate.of(2023, 10, 10));

        Predicate predicate = QPredicates.builder()
                                         .add(dto.getDateFrom(), QLesson.lesson.date::after)
                                         .add(dto.getDateTo(), QLesson.lesson.date::before)
                                         .buildAnd();
        when(repository.findAll(predicate)).thenReturn(lessons);

        assertEquals(lessons, service.findByFilter(dto));
    }
}
