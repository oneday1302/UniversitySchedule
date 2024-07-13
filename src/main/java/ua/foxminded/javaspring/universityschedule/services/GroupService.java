package ua.foxminded.javaspring.universityschedule.services;

import ua.foxminded.javaspring.universityschedule.dto.GroupDTO;
import ua.foxminded.javaspring.universityschedule.entities.Group;

import java.util.List;

public interface GroupService {

    void add(GroupDTO dto);

    void update(GroupDTO dto);

    List<Group> getAll();

    Group findById(long id);

    void removeById(long id);
}
