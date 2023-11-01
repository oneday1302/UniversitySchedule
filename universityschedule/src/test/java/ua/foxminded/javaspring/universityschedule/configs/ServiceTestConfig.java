package ua.foxminded.javaspring.universityschedule.configs;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.foxminded.javaspring.universityschedule.repositories.*;
import ua.foxminded.javaspring.universityschedule.utils.PasswordGenerator;

@Configuration
@ComponentScan("ua.foxminded.javaspring.universityschedule.services")
public class ServiceTestConfig {

    @Bean
    public JavaMailSender emailService() {
        return Mockito.mock(JavaMailSender.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Mockito.mock(PasswordEncoder.class);
    }

    @Bean
    public PasswordGenerator passwordGenerator() {
        return Mockito.mock(PasswordGenerator.class);
    }

    @Bean
    public ClassroomRepository classroomRepository() {
        return Mockito.mock(ClassroomRepository.class);
    }

    @Bean
    public CourseRepository courseRepository() {
        return Mockito.mock(CourseRepository.class);
    }

    @Bean
    public GroupRepository groupRepository() {
        return Mockito.mock(GroupRepository.class);
    }

    @Bean
    public LessonRepository lessonRepository() {
        return Mockito.mock(LessonRepository.class);
    }

    @Bean
    public StudentRepository studentRepository() {
        return Mockito.mock(StudentRepository.class);
    }

    @Bean
    public TeacherRepository teacherRepository() {
        return Mockito.mock(TeacherRepository.class);
    }

    @Bean
    public  UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }
}
