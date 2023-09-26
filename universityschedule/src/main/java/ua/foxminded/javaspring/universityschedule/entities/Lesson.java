package ua.foxminded.javaspring.universityschedule.entities;

import lombok.*;
import ua.foxminded.javaspring.universityschedule.utils.Event;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "lessons", schema = "university")
public class Lesson implements Event {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="lessons_seq")
    @SequenceGenerator(name="lessons_seq", schema = "university", sequenceName="lessons_seq", allocationSize = 1)
    private long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Setter
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Setter
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Setter
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @Setter
    @Column(name = "lesson_date")
    private LocalDate date;

    @Setter
    @Column(name = "start_time")
    private LocalTime startTime;

    @Setter
    @Column(name = "end_time")
    private LocalTime endTime;

    @Builder
    public Lesson(Course course, Teacher teacher, Group group, Classroom classroom, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.course = course;
        this.teacher = teacher;
        this.group = group;
        this.classroom = classroom;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String getTitle() {
        return course.getName();
    }

    @Override
    public String getStart() {
        return getDateTime(date, startTime);
    }

    @Override
    public String getEnd() {
        return getDateTime(date, endTime);
    }

    private String getDateTime(LocalDate date, LocalTime time) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm").format(date.atTime(time));
    }
}
