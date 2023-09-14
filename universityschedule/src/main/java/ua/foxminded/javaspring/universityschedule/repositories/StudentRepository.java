package ua.foxminded.javaspring.universityschedule.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.universityschedule.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
