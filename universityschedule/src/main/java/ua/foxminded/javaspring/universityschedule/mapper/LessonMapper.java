package ua.foxminded.javaspring.universityschedule.mapper;

import ua.foxminded.javaspring.universityschedule.dto.LessonDTO;
import ua.foxminded.javaspring.universityschedule.entities.Lesson;

public interface LessonMapper {

    LessonDTO lessonToDTO(Lesson lesson);
}
