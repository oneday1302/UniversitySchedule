package ua.foxminded.javaspring.universityschedule.mapper;

import ua.foxminded.javaspring.universityschedule.dto.GroupDTO;
import ua.foxminded.javaspring.universityschedule.entities.Group;

public interface GroupMapper {

    GroupDTO groupToDTO(Group group);
}
