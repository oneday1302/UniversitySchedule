package ua.foxminded.javaspring.universityschedule.controllers.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;

@RequiredArgsConstructor
@Controller
public class TeacherProfileController {

    private final TeacherService teacherService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/teacher/profile")
    public String profile(Model model, Authentication authentication) {
        model.addAttribute("teacher", (Teacher) authentication.getPrincipal());
        return "teacher/profile";
    }

    @GetMapping("/teacher/profile/edit")
    public String edit_profile(Model model, Authentication authentication) {
        Teacher teacher = (Teacher) authentication.getPrincipal();
        model.addAttribute("teacher", teacher);
        return "teacher/editProfile";
    }

    @PostMapping("/teacher/profile/edit")
    public String post_edit_profile(Authentication authentication,
                                    @RequestParam String username,
                                    @RequestParam String password,
                                    @RequestParam String firstName,
                                    @RequestParam String lastName,
                                    @RequestParam String email) {

        Teacher teacher = (Teacher) authentication.getPrincipal();
        teacher.setUsername(username);
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setEmail(email);
        if (!password.isEmpty()) {
            teacher.setPassword(passwordEncoder.encode(password));
        }

        teacherService.update(teacher);

        return "redirect:/teacher/profile";
    }
}
