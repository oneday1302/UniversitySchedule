package ua.foxminded.javaspring.universityschedule.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ua.foxminded.javaspring.universityschedule.validation.CreateEntity;
import ua.foxminded.javaspring.universityschedule.validation.UpdateEntity;
import ua.foxminded.javaspring.universityschedule.validation.annotations.Zero;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@Data
public class CourseDTO {

    @Zero(groups = CreateEntity.class, message = "Incorrect id!")
    @Positive(groups = UpdateEntity.class, message = "Incorrect id!")
    private long id;

    @NotBlank(groups = {CreateEntity.class, UpdateEntity.class}, message = "Name must be not blank!")
    private String name;
}
