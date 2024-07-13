package ua.foxminded.javaspring.universityschedule.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "students", schema = "university")
public class Student extends User {

    @Setter
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Builder
    public Student(String username, String password, String email, String firstName, String lastName, Group group) {
        this(username, password, email, firstName, lastName, group, Role.STUDENT);
    }

    private Student(String username, String password, String email, String firstName, String lastName, Group group, Role role) {
        super(username, password, email, firstName, lastName);
        this.group = group;
        this.addRole(role);
    }
}
