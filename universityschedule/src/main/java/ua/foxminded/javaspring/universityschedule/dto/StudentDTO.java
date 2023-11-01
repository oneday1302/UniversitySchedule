package ua.foxminded.javaspring.universityschedule.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class StudentDTO extends UserDTO {

    public StudentDTO(TeacherDTO dto) {
        this.setId(dto.getId());
        this.setUsername(dto.getUsername());
        this.setPassword(dto.getPassword());
        this.setFirstName(dto.getFirstName());
        this.setLastName(dto.getLastName());
        this.setEmail(dto.getEmail());
    }

    private long groupId;
}
