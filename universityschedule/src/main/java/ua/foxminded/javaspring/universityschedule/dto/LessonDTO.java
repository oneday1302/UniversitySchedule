package ua.foxminded.javaspring.universityschedule.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class LessonDTO {

    private long id;

    private long courseId;

    private long teacherId;

    private long groupId;

    private long classroomId;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateFrom;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateTo;
}
