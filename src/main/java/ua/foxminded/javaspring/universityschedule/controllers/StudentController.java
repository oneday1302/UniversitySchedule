package ua.foxminded.javaspring.universityschedule.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.javaspring.universityschedule.dto.StudentDTO;
import ua.foxminded.javaspring.universityschedule.entities.Role;
import ua.foxminded.javaspring.universityschedule.entities.Student;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.mapper.StudentMapper;
import ua.foxminded.javaspring.universityschedule.services.GroupService;
import ua.foxminded.javaspring.universityschedule.services.StudentService;
import ua.foxminded.javaspring.universityschedule.services.UserService;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;
import ua.foxminded.javaspring.universityschedule.validation.CreateEntity;
import ua.foxminded.javaspring.universityschedule.validation.UpdateEntity;

import java.util.List;

@RequiredArgsConstructor
@Controller
@EnableMethodSecurity
public class StudentController {

    private final UserService userService;
    private final StudentService studentService;
    private final GroupService groupService;
    private final StudentMapper mapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addStudent")
    public String addStudent(Model model) {
        if (!model.containsAttribute("studentDTO")) {
            model.addAttribute("studentDTO", new StudentDTO());
        }
        model.addAttribute("groups", groupService.getAll());
        return "add/student";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addStudent")
    public String saveStudent(@Validated(CreateEntity.class) @ModelAttribute("studentDTO") StudentDTO dto,
                              BindingResult result,
                              RedirectAttributes attr) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.studentDTO", result);
            attr.addFlashAttribute("studentDTO", dto);
            return "redirect:/addStudent";
        }
        studentService.add(dto);
        return "redirect:/home";
    }

    @GetMapping("/students")
    public String getStudents(Model model, Authentication authentication) {
        List<Student> students = studentService.getAll();
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        if (principal.getAuthorities().contains(Role.STUDENT)) {
            students.remove(principal.unwrap(Student.class));
        }
        model.addAttribute("students", students);
        return "list/students";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editStudent/{id}")
    public String editStudent(@PathVariable(value = "id") long id, Model model) {
        if (!model.containsAttribute("studentDTO")) {
            model.addAttribute("studentDTO", mapper.studentToDTO(studentService.findById(id)));
        }
        model.addAttribute("groups", groupService.getAll());
        return "edit/student";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/editStudent/{id}")
    public String postEditStudent(@PathVariable(value = "id") long id,
                                  @Validated(UpdateEntity.class) @ModelAttribute("studentDTO") StudentDTO dto,
                                  BindingResult result,
                                  RedirectAttributes attr) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.studentDTO", result);
            attr.addFlashAttribute("studentDTO", dto);
            return "redirect:/editStudent/" + id;
        }
        studentService.update(dto);
        return "redirect:/students";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable(value = "id") long id) {
        userService.removeById(id);
        return "redirect:/students";
    }
}
