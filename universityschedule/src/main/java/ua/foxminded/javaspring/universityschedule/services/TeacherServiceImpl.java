package ua.foxminded.javaspring.universityschedule.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.repositories.TeacherRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository repository;

    @Override
    public void add(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.save(teacher);
    }

    @Override
    public void update(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.save(teacher);
    }

    @Override
    public List<Teacher> getAll() {
        return repository.findAll();
    }

    @Override
    public Teacher findById(long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Entity no found."));
    }

    @Override
    public void remove(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.delete(teacher);
    }
}
