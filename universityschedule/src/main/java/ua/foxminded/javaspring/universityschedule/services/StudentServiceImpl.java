package ua.foxminded.javaspring.universityschedule.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.universityschedule.dto.StudentDTO;
import ua.foxminded.javaspring.universityschedule.entities.Student;
import ua.foxminded.javaspring.universityschedule.repositories.StudentRepository;

import javax.transaction.Transactional;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;
    private final GroupService groupService;
    private final UserService userService;
    private  final PasswordEncoder encoder;
    private final EmailService emailService;

    private static final String emailBodyFormat = "username: %s| password: %s";

    @Override
    public void add(StudentDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        char[] password = dto.getPassword();
        String encoded = encoder.encode(CharBuffer.wrap(password));
        Arrays.fill(password, '\0');
        Student student = Student.builder()
                                 .username(dto.getUsername())
                                 .password(encoded)
                                 .email(dto.getEmail())
                                 .firstName(dto.getFirstName())
                                 .lastName(dto.getLastName())
                                 .group(groupService.findById(dto.getGroupId()))
                                 .build();
        repository.save(student);
        emailService.sendEmail(student.getEmail(),
                               student.getFullName(),
                               String.format(emailBodyFormat, student.getUsername(), student.getPassword()));
    }

    @Transactional
    @Override
    public void update(StudentDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        Student student = (Student) userService.editUserData(findById(dto.getId()), dto);
        student.setGroup(groupService.findById(dto.getGroupId()));
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

}
