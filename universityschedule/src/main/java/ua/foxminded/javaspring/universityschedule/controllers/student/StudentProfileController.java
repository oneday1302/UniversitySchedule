package ua.foxminded.javaspring.universityschedule.controllers.student;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.javaspring.universityschedule.entities.Student;
import ua.foxminded.javaspring.universityschedule.services.StudentService;

@RequiredArgsConstructor
@Controller
public class StudentProfileController {

    private final StudentService studentService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/student/profile")
    public String profile(Model model, Authentication authentication) {
        model.addAttribute("student", (Student) authentication.getPrincipal());
        return "student/profile";
    }

    @GetMapping("/student/profile/edit")
    public String edit_profile(Model model, Authentication authentication) {
        Student student = (Student) authentication.getPrincipal();
        model.addAttribute("student", student);
        return "student/editProfile";
    }

    @PostMapping("/student/profile/edit")
    public String post_edit_profile(Authentication authentication,
                                    @RequestParam String username,
                                    @RequestParam String password,
                                    @RequestParam String firstName,
                                    @RequestParam String lastName,
                                    @RequestParam String email) {

        Student student = (Student) authentication.getPrincipal();
        student.setUsername(username);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        if (!password.isEmpty()) {
            student.setPassword(passwordEncoder.encode(password));
        }

        studentService.update(student);

        return "redirect:/student/profile";
    }
}
