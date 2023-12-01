package ua.foxminded.javaspring.universityschedule.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ua.foxminded.javaspring.universityschedule.entities.*;
import ua.foxminded.javaspring.universityschedule.validation.annotations.CanBeNull;
import ua.foxminded.javaspring.universityschedule.validation.CreateEntity;
import ua.foxminded.javaspring.universityschedule.validation.FilterForLesson;
import ua.foxminded.javaspring.universityschedule.validation.UpdateEntity;
import ua.foxminded.javaspring.universityschedule.validation.annotations.Zero;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Data
public class LessonDTO {

    @Zero(groups = {CreateEntity.class, FilterForLesson.class}, message = "Incorrect id!")
    @Positive(groups = UpdateEntity.class, message = "Incorrect id!")
    private long id;

    @CanBeNull(groups = FilterForLesson.class)
    @NotNull(groups = {CreateEntity.class, UpdateEntity.class}, message = "Please choose a course!")
    private Course course;

    @CanBeNull(groups = FilterForLesson.class)
    @NotNull(groups = {CreateEntity.class, UpdateEntity.class}, message = "Please choose a teacher!")
    private Teacher teacher;

    @CanBeNull(groups = FilterForLesson.class)
    @NotNull(groups = {CreateEntity.class, UpdateEntity.class}, message = "Please choose a group!")
    private Group group;

    @CanBeNull(groups = FilterForLesson.class)
    @NotNull(groups = {CreateEntity.class, UpdateEntity.class}, message = "Please choose a classroom!")
    private Classroom classroom;

    @Null(groups = FilterForLesson.class)
    @NotNull(groups = {CreateEntity.class, UpdateEntity.class}, message = "Please choose a date!")
    @FutureOrPresent(groups = {CreateEntity.class, UpdateEntity.class}, message = "Date must be future!")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    @Null(groups = FilterForLesson.class)
    @NotNull(groups = {CreateEntity.class, UpdateEntity.class}, message = "Please choose a start time")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @Null(groups = FilterForLesson.class)
    @NotNull(groups = {CreateEntity.class, UpdateEntity.class}, message = "Please choose an end time")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @Null(groups = {CreateEntity.class, UpdateEntity.class})
    @CanBeNull(groups = FilterForLesson.class)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateFrom;

    @Null(groups = {CreateEntity.class, UpdateEntity.class})
    @CanBeNull(groups = FilterForLesson.class)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateTo;
}
