package ua.foxminded.javaspring.universityschedule.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.universityschedule.dto.ClassroomDTO;
import ua.foxminded.javaspring.universityschedule.entities.Classroom;
import ua.foxminded.javaspring.universityschedule.repositories.ClassroomRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository repository;

    @Override
    public void add(ClassroomDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        repository.save(new Classroom(dto.getName()));
    }

    @Override
    public void update(ClassroomDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        Classroom classroom = findById(dto.getId());
        classroom.setName(dto.getName());
        repository.save(classroom);
    }

    @Override
    public Classroom findById(long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Entity no found."));
    }

    @Override
    public List<Classroom> getAll() {
        return repository.findAll();
    }

    @Override
    public void removeById(long id) {
        repository.deleteById(id);
    }
}
