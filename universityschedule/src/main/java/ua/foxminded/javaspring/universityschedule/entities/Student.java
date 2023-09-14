package ua.foxminded.javaspring.universityschedule.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "students", schema = "university")
public class Student extends User {

    private static final long serialVersionUID = 1L;

    @Setter
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public Student(String username, String password, String email, String firstName, String lastName, Group group) {
        this(username, password, email, firstName, lastName, group, Role.STUDENT);
    }

    private Student(String username, String password, String email, String firstName, String lastName, Group group, Role role) {
        super(username, password, email, firstName, lastName);
        this.group = group;
        this.addRole(role);
    }
}
