package ua.foxminded.javaspring.universityschedule.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.universityschedule.dto.StudentDTO;
import ua.foxminded.javaspring.universityschedule.services.GroupService;
import ua.foxminded.javaspring.universityschedule.services.StudentService;
import ua.foxminded.javaspring.universityschedule.services.UserService;

@RequiredArgsConstructor
@Controller
@EnableMethodSecurity
public class StudentController {

    private final UserService userService;
    private final StudentService studentService;
    private final GroupService groupService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addStudent")
    public String addStudent(Model model) {
        model.addAttribute("groups", groupService.getAll());
        return "/add/student";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addStudent")
    public String saveStudent(@ModelAttribute StudentDTO dto) {
        if (dto.getGroupId() == 0) {
            throw new IllegalArgumentException("Not selected group");
        }
        studentService.add(dto);
        return "redirect:/home";
    }

    @GetMapping("/students")
    public String getStudents(Model model) {
        model.addAttribute("students", studentService.getAll());
        return "/list/students";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editStudent/{id}")
    public String editStudent(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        model.addAttribute("groups", groupService.getAll());
        return "/edit/student";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/editStudent/{id}")
    public String postEditStudent(@PathVariable(value = "id") long id, @ModelAttribute StudentDTO dto) {
        dto.setId(id);
        studentService.update(dto);
        return "redirect:/students";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable(value = "id") long id) {
        userService.removeById(id);
        return "redirect:/students";
    }
}
