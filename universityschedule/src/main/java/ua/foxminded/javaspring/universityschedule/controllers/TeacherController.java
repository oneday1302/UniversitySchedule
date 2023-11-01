package ua.foxminded.javaspring.universityschedule.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.universityschedule.dto.TeacherDTO;
import ua.foxminded.javaspring.universityschedule.services.CourseService;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;
import ua.foxminded.javaspring.universityschedule.services.UserService;

@RequiredArgsConstructor
@Controller
@EnableMethodSecurity
public class TeacherController {

    private final UserService userService;
    private final TeacherService teacherService;
    private final CourseService courseService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addTeacher")
    public String addTeacher(Model model) {
        model.addAttribute("courses", courseService.getAll());
        return "/add/teacher";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addTeacher")
    public String saveTeacher(@ModelAttribute TeacherDTO dto, @RequestParam(defaultValue = "false") boolean isAdmin) {
        dto.setAdmin(isAdmin);
        teacherService.add(dto);
        return "redirect:/home";
    }

    @GetMapping("/teachers")
    public String getTeachers(Model model) {
        model.addAttribute("teachers", teacherService.getAll());
        return "/list/teachers";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editTeacher/{id}")
    public String editTeacher(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("teacher", teacherService.findById(id));
        model.addAttribute("courses", courseService.getAll());
        return "/edit/teacher";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/editTeacher/{id}")
    public String postEditTeacher(@PathVariable(value = "id") long id, @ModelAttribute TeacherDTO dto, @RequestParam(defaultValue = "false") boolean isAdmin) {
        dto.setId(id);
        dto.setAdmin(isAdmin);
        teacherService.update(dto);
        return "redirect:/teachers";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deleteTeacher/{id}")
    public String deleteTeacher(@PathVariable(value = "id") long id) {
        userService.removeById(id);
        return "redirect:/teachers";
    }
}
