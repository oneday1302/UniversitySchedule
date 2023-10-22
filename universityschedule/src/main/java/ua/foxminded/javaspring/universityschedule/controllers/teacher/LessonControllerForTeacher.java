package ua.foxminded.javaspring.universityschedule.controllers.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.foxminded.javaspring.universityschedule.entities.Lesson;
import ua.foxminded.javaspring.universityschedule.services.LessonService;

@RequiredArgsConstructor
@Controller
@EnableMethodSecurity
@PreAuthorize("hasAuthority('TEACHER')")
public class LessonControllerForTeacher {

    private final LessonService lessonService;

    @GetMapping("/teacher/lessonInfo/{id}")
    public String lessonInfo(@PathVariable(value = "id") long id, Model model) {
        Lesson lesson = lessonService.findById(id);
        model.addAttribute("lesson", lesson);
        return "teacher/lessonInfo";
    }
}
