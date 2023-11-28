package ua.foxminded.javaspring.universityschedule.dto;

import lombok.Data;
import ua.foxminded.javaspring.universityschedule.validation.CreateEntity;
import ua.foxminded.javaspring.universityschedule.validation.UpdateAdminProfile;
import ua.foxminded.javaspring.universityschedule.validation.UpdateEntity;
import ua.foxminded.javaspring.universityschedule.validation.UpdateUserProfile;
import ua.foxminded.javaspring.universityschedule.validation.annotations.Zero;

import javax.validation.constraints.*;

@Data
public class UserDTO {

    @Zero(groups = {CreateEntity.class,
                    UpdateUserProfile.class,
                    UpdateAdminProfile.class}, message = "Incorrect id!")
    @Positive(groups = UpdateEntity.class, message = "Incorrect id!")
    private long id;

    @Null(groups = {UpdateEntity.class,
                    UpdateUserProfile.class,
                    UpdateAdminProfile.class})
    @NotBlank(groups = CreateEntity.class, message = "Username must be not blank!")
    private String username;

    @NotBlank(groups = {CreateEntity.class,
                        UpdateEntity.class,
                        UpdateUserProfile.class,
                        UpdateAdminProfile.class},
              message = "First name must be not blank!")
    private String firstName;

    @NotBlank(groups = {CreateEntity.class,
                        UpdateEntity.class,
                        UpdateUserProfile.class,
                        UpdateAdminProfile.class},
              message = "Last name must be not blank!")
    private String lastName;

    @Null(groups = UpdateEntity.class)
    @NotBlank(groups = {CreateEntity.class,
                        UpdateUserProfile.class,
                        UpdateAdminProfile.class},
              message = "Email must be not blank!")
    @Email(groups = {CreateEntity.class,
                     UpdateUserProfile.class,
                     UpdateAdminProfile.class},
            message = "Email must be correct!")
    private String email;
}
