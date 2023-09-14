package ua.foxminded.javaspring.universityschedule.entities;

import lombok.*;
import ua.foxminded.javaspring.universityschedule.utils.Event;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "lessons", schema = "university")
public class Lesson implements Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinColumn(name = "gorup_id")
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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm");
        String format = "%sT%s";
        return String.format(format, dateFormatter.format(date), timeFormatter.format(startTime));
    }

    @Override
    public String getEnd() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm");
        String format = "%sT%s";
        return String.format(format, dateFormatter.format(date), timeFormatter.format(endTime));
    }
}
