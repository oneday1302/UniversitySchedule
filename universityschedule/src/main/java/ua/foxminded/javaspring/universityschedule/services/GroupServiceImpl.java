package ua.foxminded.javaspring.universityschedule.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.universityschedule.dto.GroupDTO;
import ua.foxminded.javaspring.universityschedule.entities.Group;
import ua.foxminded.javaspring.universityschedule.repositories.GroupRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository repository;

    @Override
    public void add(GroupDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        repository.save(new Group(dto.getName()));
    }

    @Override
    public void update(GroupDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        Group group = findById(dto.getId());
        group.setName(dto.getName());
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
    public void removeById(long id) {
        repository.deleteById(id);
    }
}
