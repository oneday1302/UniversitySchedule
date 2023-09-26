package ua.foxminded.javaspring.universityschedule.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudentDTO extends UserDTO {

    private long groupId;
}
