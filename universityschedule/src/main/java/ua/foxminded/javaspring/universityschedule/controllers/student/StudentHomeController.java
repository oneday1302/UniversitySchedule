package ua.foxminded.javaspring.universityschedule.controllers.student;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.foxminded.javaspring.universityschedule.dto.LessonDTO;
import ua.foxminded.javaspring.universityschedule.entities.Student;
import ua.foxminded.javaspring.universityschedule.services.*;
import ua.foxminded.javaspring.universityschedule.utils.Event;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class StudentHomeController {

    private final UserService userService;
    private final LessonService lessonService;
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final ClassroomService classroomService;

    @GetMapping("/student/getEvents")
    @ResponseBody
    public List<Event> getEvents(Authentication authentication, @ModelAttribute LessonDTO dto) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Student student = (Student) userService.findByUsername(userDetails.getUsername());
        dto.setGroupId(student.getGroup().getId());
        return new ArrayList<>(lessonService.findByFilter(dto));
    }

    @GetMapping("/student/home")
    public String home(Model model) {
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("teachers", teacherService.getAll());
        model.addAttribute("classrooms", classroomService.getAll());
        return "student/home";
    }
}
