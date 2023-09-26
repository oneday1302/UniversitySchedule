package ua.foxminded.javaspring.universityschedule.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.universityschedule.dto.CourseDTO;
import ua.foxminded.javaspring.universityschedule.entities.Course;
import ua.foxminded.javaspring.universityschedule.repositories.CourseRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;

    @Override
    public void add(CourseDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        repository.save(new Course(dto.getName()));
    }

    @Override
    public void update(CourseDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        Course course = findById(dto.getId());
        course.setName(dto.getName());
        repository.save(course);
    }

    @Override
    public List<Course> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Course> getAllByListId(List<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public Course findById(long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Entity no found."));
    }

    @Override
    public void removeById(long id) {
        repository.deleteById(id);
    }
}
