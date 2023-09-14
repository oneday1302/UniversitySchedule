package ua.foxminded.javaspring.universityschedule.services;

import ua.foxminded.javaspring.universityschedule.entities.Group;

import java.util.List;

public interface GroupService {

    void add(Group group);

    void update(Group group);

    Group findByName(String name);

    List<Group> getAll();

    Group findById(long id);

    void remove(Group group);

    void removeById(long id);
}
