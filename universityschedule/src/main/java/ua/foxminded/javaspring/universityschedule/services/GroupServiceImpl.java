package ua.foxminded.javaspring.universityschedule.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.universityschedule.entities.Group;
import ua.foxminded.javaspring.universityschedule.repositories.GroupRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository repository;

    @Override
    public void add(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.save(group);
    }

    @Override
    public void update(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.save(group);
    }

    @Override
    public List<Group> getAll() {
        return repository.findAll();
    }

    @Override
    public Group findById(long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Entity no found."));
    }

    @Override
    public Group findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        return repository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Entity no found."));
    }

    @Override
    public void remove(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.delete(group);
    }

    @Override
    public void removeById(long id) {
        repository.deleteById(id);
    }
}
