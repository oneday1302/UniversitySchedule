package ua.foxminded.javaspring.universityschedule.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.universityschedule.entities.Classroom;
import ua.foxminded.javaspring.universityschedule.repositories.ClassroomRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository repository;

    @Override
    public void add(Classroom classroom) {
        if (classroom == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.save(classroom);
    }

    @Override
    public void update(Classroom classroom) {
        if (classroom == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.save(classroom);
    }

    @Override
    public Classroom findById(long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Entity no found."));
    }

    @Override
    public Classroom findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        return repository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Entity no found."));
    }

    @Override
    public List<Classroom> getAll() {
        return repository.findAll();
    }

    @Override
    public void remove(Classroom classroom) {
        if (classroom == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.delete(classroom);
    }

    @Override
    public void removeById(long id) {
        repository.deleteById(id);
    }
}
