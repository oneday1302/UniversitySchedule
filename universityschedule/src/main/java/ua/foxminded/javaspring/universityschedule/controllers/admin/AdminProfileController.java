package ua.foxminded.javaspring.universityschedule.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.services.CourseService;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminProfileController {

    private final PasswordEncoder passwordEncoder;
    private final TeacherService teacherService;
    private final CourseService courseService;

    @GetMapping("/admin/profile")
    public String profile(Model model, Authentication authentication) {
        Teacher teacher = (Teacher) authentication.getPrincipal();
        model.addAttribute("user", teacher);
        return "admin/profile";
    }

    @GetMapping("/admin/profile/edit")
    public String editProfile(Model model, Authentication authentication) {
        Teacher teacher = (Teacher) authentication.getPrincipal();
        model.addAttribute("user", teacher);
        model.addAttribute("courses", courseService.getAll());
        return "admin/edit/profile";
    }

    @PostMapping("/admin/profile/edit")
    public String postEditProfile(Authentication authentication,
                                  @RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam String firstName,
                                  @RequestParam String lastName,
                                  @RequestParam String email,
                                  @RequestParam(required = false) List<String> courses) {

        Teacher teacher = (Teacher) authentication.getPrincipal();

        teacher.setUsername(username);
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setEmail(email);
        if (!password.isEmpty()) {
            teacher.setPassword(passwordEncoder.encode(password));
        }
        teacher.clearCourse();
        if (courses != null) {
            for (String course : courses) {
                teacher.addCourse(courseService.findByName(course));
            }
        }

        teacherService.update(teacher);

        return "redirect:/admin/profile";
    }
}
