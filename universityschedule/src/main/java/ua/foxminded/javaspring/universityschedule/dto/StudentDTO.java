package ua.foxminded.javaspring.universityschedule.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.foxminded.javaspring.universityschedule.entities.Group;
import ua.foxminded.javaspring.universityschedule.entities.Student;
import ua.foxminded.javaspring.universityschedule.validation.CreateEntity;
import ua.foxminded.javaspring.universityschedule.validation.UpdateEntity;
import ua.foxminded.javaspring.universityschedule.validation.UpdateUserProfile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class StudentDTO extends UserDTO {

    @Null(groups = UpdateUserProfile.class)
    @NotNull(groups = {CreateEntity.class, UpdateEntity.class}, message = "Please choose a group!")
    private Group group;

    public StudentDTO(TeacherDTO dto) {
        this.setId(dto.getId());
        this.setUsername(dto.getUsername());
        this.setFirstName(dto.getFirstName());
        this.setLastName(dto.getLastName());
        this.setEmail(dto.getEmail());
    }

    public StudentDTO(Student student) {
        this.setId(student.getId());
        this.setUsername(student.getUsername());
        this.setFirstName(student.getFirstName());
        this.setLastName(student.getLastName());
        this.setEmail(student.getEmail());
        this.group = student.getGroup();
    }
}
