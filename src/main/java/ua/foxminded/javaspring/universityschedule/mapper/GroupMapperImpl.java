package ua.foxminded.javaspring.universityschedule.mapper;

import org.springframework.stereotype.Component;
import ua.foxminded.javaspring.universityschedule.dto.GroupDTO;
import ua.foxminded.javaspring.universityschedule.entities.Group;

@Component
public class GroupMapperImpl implements GroupMapper {

    @Override
    public GroupDTO groupToDTO(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        GroupDTO dto = new GroupDTO();
        dto.setId(group.getId());
        dto.setName(group.getName());
        return dto;
    }
}
