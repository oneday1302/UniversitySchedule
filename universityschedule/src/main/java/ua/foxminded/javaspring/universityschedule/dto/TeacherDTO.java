package ua.foxminded.javaspring.universityschedule.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeacherDTO extends UserDTO {

    private List<Long> courses;
    private boolean admin;
}
