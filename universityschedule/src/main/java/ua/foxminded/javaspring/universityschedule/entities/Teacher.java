package ua.foxminded.javaspring.universityschedule.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "teachers", schema = "university")
public class Teacher extends User {

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(name = "teachers_courses", schema = "university", joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    Set<Course> courses = new HashSet<>();

    @Builder
    public Teacher(String username, String password, String email, String firstName, String lastName) {
        this(username, password, email, firstName, lastName, Role.TEACHER);
    }

    private Teacher(String username, String password, String email, String firstName, String lastName, Role role) {
        super(username, password, email, firstName, lastName);
        this.addRole(role);
    }

    public void addCourses(Set<Course> courses) {
        if (courses == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.courses.addAll(courses);
    }

    public void clearCourse() {
        courses.clear();
    }

    public boolean hasCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        return courses.contains(course);
    }

    public boolean isAdmin() {
        return hasRole(Role.ADMIN);
    }
}
