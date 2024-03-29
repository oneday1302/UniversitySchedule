package ua.foxminded.javaspring.universityschedule.dto;

import lombok.Data;
import ua.foxminded.javaspring.universityschedule.validation.annotations.NotEmpty;
import ua.foxminded.javaspring.universityschedule.validation.UpdateUserProfile;
import ua.foxminded.javaspring.universityschedule.validation.annotations.Zero;

import java.util.Arrays;

@Data
public class PasswordDTO {

    @Zero(groups = UpdateUserProfile.class, message = "Incorrect id!")
    private long id;

    @NotEmpty(groups = UpdateUserProfile.class, message = "Current password must be not empty")
    private char[] currentPassword;

    @NotEmpty(groups = UpdateUserProfile.class, message = "New password must be not empty")
    private char[] newPassword;

    public void invalidate() {
        Arrays.fill(currentPassword, '\0');
        Arrays.fill(newPassword, '\0');;
    }
}
