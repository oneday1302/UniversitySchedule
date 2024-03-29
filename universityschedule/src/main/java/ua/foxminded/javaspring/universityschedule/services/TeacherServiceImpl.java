package ua.foxminded.javaspring.universityschedule.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.javaspring.universityschedule.dto.TeacherDTO;
import ua.foxminded.javaspring.universityschedule.entities.Role;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.repositories.TeacherRepository;
import ua.foxminded.javaspring.universityschedule.utils.PasswordGenerator;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private final UserService userService;
    private final TeacherRepository repository;
    private final PasswordEncoder encoder;
    private final EmailService emailService;
    private final PasswordGenerator passwordGenerator;

    private static final String emailBodyFormat = "username: %s| password: %s";

    @Transactional
    @Override
    public void add(TeacherDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        char[] password = passwordGenerator.generate();
        String encoded = encoder.encode(CharBuffer.wrap(password));
        Teacher teacher = Teacher.builder()
                                 .username(dto.getUsername())
                                 .password(encoded)
                                 .email(dto.getEmail())
                                 .firstName(dto.getFirstName())
                                 .lastName(dto.getLastName())
                                 .build();
        if (dto.getCourses() != null) {
            teacher.addCourses(dto.getCourses());
        }
        if (dto.isAdmin()) {
            teacher.addRole(Role.ADMIN);
        }
        repository.save(teacher);
        emailService.sendEmail(teacher.getEmail(),
                               teacher.getFullName(),
                               String.format(emailBodyFormat, teacher.getUsername(), CharBuffer.wrap(password)));
        Arrays.fill(password, '\0');
    }

    @Override
    public void update(TeacherDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        Teacher teacher = (Teacher) userService.editUserData(findById(dto.getId()), dto);
        if (dto.isAdmin()) {
            teacher.addRole(Role.ADMIN);
        } else {
            teacher.removeRole(Role.ADMIN);
        }
        if (dto.getCourses() != null) {
            teacher.clearCourse();
            teacher.addCourses(dto.getCourses());
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
}
