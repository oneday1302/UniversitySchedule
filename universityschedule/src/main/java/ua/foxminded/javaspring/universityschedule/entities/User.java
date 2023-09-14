package ua.foxminded.javaspring.universityschedule.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "users", schema = "university")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
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
    private List<Role> roles = new ArrayList<>();

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

        if (roles.contains(role)) {
            return;
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

    public boolean hasRole(String role) {
        return roles.contains(Role.valueOf(role));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
