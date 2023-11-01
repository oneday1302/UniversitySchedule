package ua.foxminded.javaspring.universityschedule.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.foxminded.javaspring.universityschedule.dto.StudentDTO;
import ua.foxminded.javaspring.universityschedule.dto.TeacherDTO;
import ua.foxminded.javaspring.universityschedule.entities.Role;
import ua.foxminded.javaspring.universityschedule.entities.Student;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.services.CourseService;
import ua.foxminded.javaspring.universityschedule.services.StudentService;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;

@RequiredArgsConstructor
@Controller
public class ProfileController {

    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        if (principal.getAuthorities().contains(Role.TEACHER)) {
            Teacher teacher = principal.unwrap(Teacher.class);
            model.addAttribute("user", teacher);
        } else {
            Student student = principal.unwrap(Student.class);
            model.addAttribute("user", student);
        }
        return "/profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model, Authentication authentication) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        if (principal.getAuthorities().contains(Role.ADMIN)) {
            Teacher teacher = principal.unwrap(Teacher.class);
            model.addAttribute("user", teacher);
            model.addAttribute("courses", courseService.getAll());
        } else if (principal.getAuthorities().contains(Role.TEACHER)) {
            Teacher teacher = principal.unwrap(Teacher.class);
            model.addAttribute("user", teacher);
        } else {
            Student student = principal.unwrap(Student.class);
            model.addAttribute("user", student);
        }
        return "/editProfile";
    }

    @PostMapping("/profile/edit")
    public String postEditProfile(Authentication authentication, @ModelAttribute TeacherDTO dto) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        if (principal.getAuthorities().contains(Role.ADMIN)) {
            Teacher teacher = principal.unwrap(Teacher.class);
            dto.setId(teacher.getId());
            dto.setAdmin(true);
            teacherService.update(dto);
        } else if (principal.getAuthorities().contains(Role.TEACHER)) {
            Teacher teacher = principal.unwrap(Teacher.class);
            dto.setId(teacher.getId());
            teacherService.update(dto);
        } else {
            Student student = principal.unwrap(Student.class);
            StudentDTO studentDTO = new StudentDTO(dto);
            studentDTO.setId(student.getId());
            studentDTO.setGroupId(student.getGroup().getId());
            studentService.update(studentDTO);
        }
        return "redirect:/profile";
    }
}
