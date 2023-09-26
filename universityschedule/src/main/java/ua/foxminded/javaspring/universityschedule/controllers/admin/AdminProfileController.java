package ua.foxminded.javaspring.universityschedule.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.foxminded.javaspring.universityschedule.dto.TeacherDTO;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.services.CourseService;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;
import ua.foxminded.javaspring.universityschedule.services.UserService;

@RequiredArgsConstructor
@Controller
public class AdminProfileController {

    private final UserService userService;
    private final TeacherService teacherService;
    private final CourseService courseService;

    @GetMapping("/admin/profile")
    public String profile(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Teacher teacher = (Teacher) userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", teacher);
        return "admin/profile";
    }

    @GetMapping("/admin/profile/edit")
    public String editProfile(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Teacher teacher = (Teacher) userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", teacher);
        model.addAttribute("courses", courseService.getAll());
        return "admin/edit/profile";
    }

    @PostMapping("/admin/profile/edit")
    public String postEditProfile(Authentication authentication, @ModelAttribute TeacherDTO dto) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Teacher teacher = (Teacher) userService.findByUsername(userDetails.getUsername());
        dto.setId(teacher.getId());
        dto.setIsAdmin("on");
        teacherService.update(dto);
        return "redirect:/admin/profile";
    }
}
