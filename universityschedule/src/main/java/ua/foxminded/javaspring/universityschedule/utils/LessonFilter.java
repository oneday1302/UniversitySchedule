package ua.foxminded.javaspring.universityschedule.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.foxminded.javaspring.universityschedule.entities.Classroom;
import ua.foxminded.javaspring.universityschedule.entities.Course;
import ua.foxminded.javaspring.universityschedule.entities.Group;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonFilter {

    private Course course;

    private Teacher teacher;

    private Group group;

    private Classroom classroom;

    private LocalDate from;

    private LocalDate to;
}
