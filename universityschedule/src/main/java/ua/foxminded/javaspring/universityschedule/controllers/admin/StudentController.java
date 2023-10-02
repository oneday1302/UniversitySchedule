package ua.foxminded.javaspring.universityschedule.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.universityschedule.dto.StudentDTO;
import ua.foxminded.javaspring.universityschedule.services.GroupService;
import ua.foxminded.javaspring.universityschedule.services.StudentService;
import ua.foxminded.javaspring.universityschedule.services.UserService;

@RequiredArgsConstructor
@Controller
public class StudentController {

    private final UserService userService;
    private final StudentService studentService;
    private final GroupService groupService;

    @GetMapping("/admin/addStudent")
    public String addStudent(Model model) {
        model.addAttribute("groups", groupService.getAll());
        return "/admin/add/student";
    }

    @PostMapping("/admin/addStudent")
    public String saveStudent(@ModelAttribute StudentDTO dto) {
        if (dto.getGroupId() == 0) {
            throw new IllegalArgumentException("Not selected group");
        }
        studentService.add(dto);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/students")
    public String getStudents(Model model) {
        model.addAttribute("students", studentService.getAll());
        return "/admin/list/students";
    }

    @GetMapping("/admin/editStudent/{id}")
    public String editStudent(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        model.addAttribute("groups", groupService.getAll());
        return "/admin/edit/student";
    }

    @PostMapping("/admin/editStudent/{id}")
    public String postEditStudent(@PathVariable(value = "id") long id, @ModelAttribute StudentDTO dto) {
        dto.setId(id);
        studentService.update(dto);
        return "redirect:/admin/students";
    }

    @GetMapping("/admin/deleteStudent/{id}")
    public String deleteStudent(@PathVariable(value = "id") long id) {
        userService.removeById(id);
        return "redirect:/admin/students";
    }
}
