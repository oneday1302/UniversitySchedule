package ua.foxminded.javaspring.universityschedule.controllers.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.foxminded.javaspring.universityschedule.entities.Lesson;
import ua.foxminded.javaspring.universityschedule.services.LessonService;

import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Controller
public class LessonControllerForStudent {

    private final LessonService lessonService;

    @GetMapping("/student/lessonInfo/{id}")
    public String lessonInfo(@PathVariable(value = "id") long id, Model model) {
        Lesson lesson = lessonService.findById(id);
        model.addAttribute("lesson", lesson);
        return "student/lessonInfo";
    }
}
