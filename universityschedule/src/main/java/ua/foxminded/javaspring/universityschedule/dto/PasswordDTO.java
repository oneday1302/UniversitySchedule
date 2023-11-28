package ua.foxminded.javaspring.universityschedule.dto;

import lombok.Data;
import ua.foxminded.javaspring.universityschedule.validation.annotations.NotEmpty;
import ua.foxminded.javaspring.universityschedule.validation.UpdateUserProfile;
import ua.foxminded.javaspring.universityschedule.validation.annotations.Zero;

import javax.validation.constraints.NotNull;

@Data
public class PasswordDTO {

    @Zero(groups = UpdateUserProfile.class, message = "Incorrect id!")
    private long id;

    @NotEmpty(groups = UpdateUserProfile.class, message = "Current password must be not empty")
    private char[] currentPassword;

    @NotEmpty(groups = UpdateUserProfile.class, message = "New password must be not empty")
    private char[] newPassword;

    @NotEmpty(groups = UpdateUserProfile.class, message = "Password confirmation must be not empty")
    private char[] passwordConfirmation;
}
