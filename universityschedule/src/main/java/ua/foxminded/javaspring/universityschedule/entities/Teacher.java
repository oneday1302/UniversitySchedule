package ua.foxminded.javaspring.universityschedule.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "teachers", schema = "university")
public class Teacher extends User {

    private static final long serialVersionUID = 1L;

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(name = "teachers_courses", schema = "university", joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    List<Course> courses = new ArrayList<>();

    public Teacher(String username, String password, String email, String firstName, String lastName) {
        this(username, password, email, firstName, lastName, Role.TEACHER);
    }

    private Teacher(String username, String password, String email, String firstName, String lastName, Role role) {
        super(username, password, email, firstName, lastName);
        this.addRole(role);
    }

    public void addCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        if (courses.contains(course)) {
            return;
        }

        courses.add(course);
    }

    public boolean hasCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        return courses.contains(course);
    }

    public void removeCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        courses.remove(course);
    }

    public void clearCourse() {
        courses.clear();
    }
}
