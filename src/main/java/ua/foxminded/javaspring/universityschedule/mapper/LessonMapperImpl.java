package ua.foxminded.javaspring.universityschedule.mapper;

import org.springframework.stereotype.Component;
import ua.foxminded.javaspring.universityschedule.dto.LessonDTO;
import ua.foxminded.javaspring.universityschedule.entities.Lesson;

@Component
public class LessonMapperImpl implements LessonMapper {

    @Override
    public LessonDTO lessonToDTO(Lesson lesson) {
        if (lesson == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        LessonDTO dto = new LessonDTO();
        dto.setId(lesson.getId());
        dto.setCourse(lesson.getCourse());
        dto.setTeacher(lesson.getTeacher());
        dto.setGroup(lesson.getGroup());
        dto.setClassroom(lesson.getClassroom());
        dto.setDate(lesson.getDate());
        dto.setStartTime(lesson.getStartTime());
        dto.setEndTime(lesson.getEndTime());
        return dto;
    }
}
