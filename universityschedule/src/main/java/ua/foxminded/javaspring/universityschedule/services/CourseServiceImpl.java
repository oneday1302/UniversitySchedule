package ua.foxminded.javaspring.universityschedule.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.universityschedule.entities.Course;
import ua.foxminded.javaspring.universityschedule.repositories.CourseRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;

    @Override
    public void add(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.save(course);
    }

    @Override
    public void update(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.save(course);
    }

    @Override
    public List<Course> getAll() {
        return repository.findAll();
    }

    @Override
    public Course findById(long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Entity no found."));
    }

    @Override
    public Course findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        return repository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Entity no found."));
    }

    @Override
    public void remove(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.delete(course);
    }

    @Override
    public void removeById(long id) {
        repository.deleteById(id);
    }
}
