package ua.foxminded.javaspring.universityschedule.controllers.teacher;

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
import ua.foxminded.javaspring.universityschedule.services.TeacherService;
import ua.foxminded.javaspring.universityschedule.services.UserService;

@RequiredArgsConstructor
@Controller
public class TeacherProfileController {

    private final UserService userService;
    private final TeacherService teacherService;

    @GetMapping("/teacher/profile")
    public String profile(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Teacher teacher = (Teacher) userService.findByUsername(userDetails.getUsername());
        model.addAttribute("teacher", teacher);
        return "teacher/profile";
    }

    @GetMapping("/teacher/profile/edit")
    public String edit_profile(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Teacher teacher = (Teacher) userService.findByUsername(userDetails.getUsername());
        model.addAttribute("teacher", teacher);
        return "teacher/editProfile";
    }

    @PostMapping("/teacher/profile/edit")
    public String post_edit_profile(Authentication authentication, @ModelAttribute TeacherDTO dto) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Teacher teacher = (Teacher) userService.findByUsername(userDetails.getUsername());
        dto.setId(teacher.getId());
        teacherService.update(dto);
        return "redirect:/teacher/profile";
    }
}
