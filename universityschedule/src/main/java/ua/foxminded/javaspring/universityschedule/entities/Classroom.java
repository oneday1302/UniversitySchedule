package ua.foxminded.javaspring.universityschedule.entities;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "classrooms", schema = "university")
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Column(unique = true)
    private String name;

    public Classroom(String name) {
        this.name = name;
    }
}
