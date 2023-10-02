package ua.foxminded.javaspring.universityschedule.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.universityschedule.dto.TeacherDTO;
import ua.foxminded.javaspring.universityschedule.services.CourseService;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;
import ua.foxminded.javaspring.universityschedule.services.UserService;
import ua.foxminded.javaspring.universityschedule.utils.PasswordGenerator;

import java.util.Arrays;

@RequiredArgsConstructor
@Controller
public class TeacherController {

    private final PasswordGenerator passwordGenerator;
    private final UserService userService;
    private final TeacherService teacherService;
    private final CourseService courseService;

    private static final String emailBodyFormat = "username: %s| password: %s";

    @GetMapping("/admin/addTeacher")
    public String addTeacher(Model model) {
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("password", passwordGenerator.generate());
        return "/admin/add/teacher";
    }

    @PostMapping("/admin/addTeacher")
    public String saveTeacher(@ModelAttribute TeacherDTO dto) {
        teacherService.add(dto);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/teachers")
    public String getTeachers(Model model) {
        model.addAttribute("teachers", teacherService.getAll());
        return "/admin/list/teachers";
    }

    @GetMapping("/admin/editTeacher/{id}")
    public String editTeacher(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("teacher", teacherService.findById(id));
        model.addAttribute("courses", courseService.getAll());
        return "/admin/edit/teacher";
    }

    @PostMapping("/admin/editTeacher/{id}")
    public String postEditTeacher(@PathVariable(value = "id") long id, @ModelAttribute TeacherDTO dto, @RequestParam(defaultValue = "false") boolean isAdmin) {
        dto.setId(id);
        dto.setAdmin(isAdmin);
        teacherService.update(dto);
        return "redirect:/admin/teachers";
    }

    @GetMapping("/admin/deleteTeacher/{id}")
    public String deleteTeacher(@PathVariable(value = "id") long id) {
        userService.removeById(id);
        return "redirect:/admin/teachers";
    }
}
