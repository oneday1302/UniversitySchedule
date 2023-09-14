package ua.foxminded.javaspring.universityschedule.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.universityschedule.entities.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long>, QuerydslPredicateExecutor<Lesson> {

}
