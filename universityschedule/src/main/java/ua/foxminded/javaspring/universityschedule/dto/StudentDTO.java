package ua.foxminded.javaspring.universityschedule.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.foxminded.javaspring.universityschedule.entities.Group;
import ua.foxminded.javaspring.universityschedule.validation.CreateEntity;
import ua.foxminded.javaspring.universityschedule.validation.UpdateEntity;
import ua.foxminded.javaspring.universityschedule.validation.UpdateUserProfile;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class StudentDTO extends UserDTO {

    @Null(groups = UpdateUserProfile.class)
    @NotNull(groups = {CreateEntity.class, UpdateEntity.class}, message = "Please choose a group!")
    private Group group;
}
