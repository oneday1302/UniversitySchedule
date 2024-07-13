package ua.foxminded.javaspring.universityschedule.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.foxminded.javaspring.universityschedule.entities.Course;
import ua.foxminded.javaspring.universityschedule.validation.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class TeacherDTO extends UserDTO {

    @Null(groups = UpdateUserProfile.class)
    @NotNull(groups = {CreateEntity.class,
                       UpdateEntity.class,
                       UpdateAdminProfile.class},
             message = "Please choose a course!")
    private Set<Course> courses;

    private boolean admin;
}
