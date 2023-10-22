package ua.foxminded.javaspring.universityschedule.controllers.student;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.foxminded.javaspring.universityschedule.dto.StudentDTO;
import ua.foxminded.javaspring.universityschedule.entities.Student;
import ua.foxminded.javaspring.universityschedule.services.StudentService;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;

@RequiredArgsConstructor
@Controller
@EnableMethodSecurity
@PreAuthorize("hasAuthority('STUDENT')")
public class StudentProfileController {

    private final StudentService studentService;

    @GetMapping("/student/profile")
    public String profile(Model model, Authentication authentication) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        Student student = principal.unwrap(Student.class);
        model.addAttribute("student", student);
        return "student/profile";
    }

    @GetMapping("/student/profile/edit")
    public String editProfile(Model model, Authentication authentication) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        Student student = principal.unwrap(Student.class);
        model.addAttribute("student", student);
        return "student/editProfile";
    }

    @PostMapping("/student/profile/edit")
    public String postEditProfile(Authentication authentication, @ModelAttribute StudentDTO dto) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        Student student = principal.unwrap(Student.class);
        dto.setId(student.getId());
        dto.setGroupId(student.getGroup().getId());
        studentService.update(dto);
        return "redirect:/student/profile";
    }
}
