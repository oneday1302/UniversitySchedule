package ua.foxminded.javaspring.universityschedule.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.foxminded.javaspring.universityschedule.entities.Role;
import ua.foxminded.javaspring.universityschedule.entities.Student;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.services.ClassroomService;
import ua.foxminded.javaspring.universityschedule.services.CourseService;
import ua.foxminded.javaspring.universityschedule.services.GroupService;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final GroupService groupService;
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final ClassroomService classroomService;

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        if (principal.getAuthorities().contains(Role.TEACHER)) {
            Teacher teacher = principal.unwrap(Teacher.class);
            model.addAttribute("teacherId", teacher.getId());
            model.addAttribute("teacherSelected", false);
            model.addAttribute("groupSelected", true);
        } else {
            Student student = principal.unwrap(Student.class);
            model.addAttribute("groupId", student.getGroup().getId());
            model.addAttribute("teacherSelected", true);
            model.addAttribute("groupSelected", false);
        }
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("teachers", teacherService.getAll());
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("classrooms", classroomService.getAll());
        return "home";
    }
}
