package ua.foxminded.javaspring.universityschedule.controllers.student;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.foxminded.javaspring.universityschedule.dto.StudentDTO;
import ua.foxminded.javaspring.universityschedule.entities.Student;
import ua.foxminded.javaspring.universityschedule.services.StudentService;
import ua.foxminded.javaspring.universityschedule.services.UserService;

@RequiredArgsConstructor
@Controller
public class StudentProfileController {

    private final StudentService studentService;
    private final UserService userService;

    @GetMapping("/student/profile")
    public String profile(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Student student = (Student) userService.findByUsername(userDetails.getUsername());
        model.addAttribute("student", student);
        return "student/profile";
    }

    @GetMapping("/student/profile/edit")
    public String editProfile(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Student student = (Student) userService.findByUsername(userDetails.getUsername());
        model.addAttribute("student", student);
        return "student/editProfile";
    }

    @PostMapping("/student/profile/edit")
    public String postEditProfile(Authentication authentication, @ModelAttribute StudentDTO dto) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Student student = (Student) userService.findByUsername(userDetails.getUsername());
        dto.setId(student.getId());
        dto.setGroupId(student.getGroup().getId());
        studentService.update(dto);
        return "redirect:/student/profile";
    }
}
