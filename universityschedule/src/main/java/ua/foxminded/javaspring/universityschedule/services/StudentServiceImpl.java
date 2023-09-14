package ua.foxminded.javaspring.universityschedule.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.universityschedule.entities.Student;
import ua.foxminded.javaspring.universityschedule.repositories.StudentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    @Override
    public void add(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.save(student);
    }

    @Override
    public void update(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.save(student);
    }

    @Override
    public Student findById(long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Entity no found."));
    }

    @Override
    public List<Student> getAll() {
        return repository.findAll();
    }

    @Override
    public void remove(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.delete(student);
    }
}
