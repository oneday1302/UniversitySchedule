package ua.foxminded.javaspring.universityschedule.controllers.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.javaspring.universityschedule.dto.TeacherDTO;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;

@RequiredArgsConstructor
@Controller
@EnableMethodSecurity
@PreAuthorize("hasAuthority('TEACHER')")
public class TeacherProfileController {

    private final TeacherService teacherService;

    @GetMapping("/teacher/profile")
    public String profile(Model model, Authentication authentication) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        Teacher teacher = principal.unwrap(Teacher.class);
        model.addAttribute("teacher", teacher);
        return "teacher/profile";
    }

    @GetMapping("/teacher/profile/edit")
    public String edit_profile(Model model, Authentication authentication) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        Teacher teacher = principal.unwrap(Teacher.class);
        model.addAttribute("teacher", teacher);
        return "teacher/editProfile";
    }

    @PostMapping("/teacher/profile/edit")
    public String post_edit_profile(Authentication authentication, @ModelAttribute TeacherDTO dto) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        Teacher teacher = principal.unwrap(Teacher.class);
        dto.setId(teacher.getId());
        teacherService.update(dto);
        return "redirect:/teacher/profile";
    }
}
