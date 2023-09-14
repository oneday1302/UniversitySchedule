package ua.foxminded.javaspring.universityschedule.entities;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "groups", schema = "university")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Column(unique = true)
    private String name;

    public Group(String name) {
        this.name = name;
    }
}
