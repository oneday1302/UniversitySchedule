package ua.foxminded.javaspring.universityschedule.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "users", schema = "university")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="users_seq")
    @SequenceGenerator(name="users_seq", schema = "university", sequenceName="users_seq", allocationSize = 1)
    private long id;

    @Column(unique = true)
    private String username;

    @Setter
    private String password;

    @Setter
    @Column(unique = true)
    private String email;

    @Setter
    @Column(name = "first_name")
    private String firstName;

    @Setter
    @Column(name = "last_name")
    private String lastName;

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = "users_roles", schema = "university", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private final Set<Role> roles = new HashSet<>();

    public User(String username, String password, String email, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    public void addRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        roles.add(role);
    }

    public void removeRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        if (!roles.contains(role)) {
            return;
        }
        roles.remove(role);
    }

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }
}
