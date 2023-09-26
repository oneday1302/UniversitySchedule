package ua.foxminded.javaspring.universityschedule.entities;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "courses", schema = "university")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="courses_seq")
    @SequenceGenerator(name="courses_seq", schema = "university", sequenceName="courses_seq", allocationSize = 1)
    private long id;

    @Setter
    @Column(unique = true)
    private String name;

    public Course(String name) {
        this.name = name;
    }
}
