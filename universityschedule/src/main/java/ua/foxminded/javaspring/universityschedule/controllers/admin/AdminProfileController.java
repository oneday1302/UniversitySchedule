package ua.foxminded.javaspring.universityschedule.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.foxminded.javaspring.universityschedule.dto.TeacherDTO;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.services.CourseService;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;

@RequiredArgsConstructor
@Controller
public class AdminProfileController {

    private final TeacherService teacherService;
    private final CourseService courseService;

    @GetMapping("/admin/profile")
    public String profile(Model model, Authentication authentication) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        Teacher teacher = principal.unwrap(Teacher.class);
        model.addAttribute("user", teacher);
        return "admin/profile";
    }

    @GetMapping("/admin/profile/edit")
    public String editProfile(Model model, Authentication authentication) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        Teacher teacher = principal.unwrap(Teacher.class);
        model.addAttribute("user", teacher);
        model.addAttribute("courses", courseService.getAll());
        return "admin/edit/profile";
    }

    @PostMapping("/admin/profile/edit")
    public String postEditProfile(Authentication authentication, @ModelAttribute TeacherDTO dto) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        Teacher teacher = principal.unwrap(Teacher.class);
        dto.setId(teacher.getId());
        dto.setAdmin(true);
        teacherService.update(dto);
        return "redirect:/admin/profile";
    }
}
