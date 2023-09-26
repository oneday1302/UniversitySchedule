package ua.foxminded.javaspring.universityschedule.entities;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "classrooms", schema = "university")
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="classrooms_seq")
    @SequenceGenerator(name="classrooms_seq", schema = "university", sequenceName="classrooms_seq", allocationSize = 1)
    private long id;

    @Setter
    @Column(unique = true)
    private String name;

    public Classroom(String name) {
        this.name = name;
    }
}
