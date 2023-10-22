package ua.foxminded.javaspring.universityschedule.controllers.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.foxminded.javaspring.universityschedule.dto.LessonDTO;
import ua.foxminded.javaspring.universityschedule.entities.Lesson;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.services.*;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;

import java.util.List;

@RequiredArgsConstructor
@Controller
@EnableMethodSecurity
@PreAuthorize("hasAuthority('TEACHER')")
public class TeacherHomeController {

    private final LessonService lessonService;
    private final CourseService courseService;
    private final GroupService groupService;
    private final ClassroomService classroomService;

    @GetMapping("/teacher/getEvents")
    @ResponseBody
    public List<Lesson> getEvents(Authentication authentication, @ModelAttribute LessonDTO dto) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        Teacher teacher = principal.unwrap(Teacher.class);
        dto.setTeacherId(teacher.getId());
        return lessonService.findByFilter(dto);
    }

    @GetMapping("/teacher/home")
    public String home(Model model) {
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("classrooms", classroomService.getAll());
        return "teacher/home";
    }
}
