package ua.foxminded.javaspring.universityschedule.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.javaspring.universityschedule.dto.TeacherDTO;
import ua.foxminded.javaspring.universityschedule.services.CourseService;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;
import ua.foxminded.javaspring.universityschedule.services.UserService;
import ua.foxminded.javaspring.universityschedule.validation.CreateEntity;
import ua.foxminded.javaspring.universityschedule.validation.UpdateEntity;

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
        if (!model.containsAttribute("teacherDTO")) {
            model.addAttribute("teacherDTO", new TeacherDTO());
        }
        model.addAttribute("courses", courseService.getAll());
        return "/add/teacher";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addTeacher")
    public String saveTeacher(@Validated(CreateEntity.class) @ModelAttribute("teacherDTO") TeacherDTO dto,
                              BindingResult result,
                              RedirectAttributes attr) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.teacherDTO", result);
            attr.addFlashAttribute("teacherDTO", dto);
            return "redirect:/addTeacher";
        }
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
        if (!model.containsAttribute("teacherDTO")) {
            model.addAttribute("teacherDTO", new TeacherDTO(teacherService.findById(id)));
        }
        model.addAttribute("courses", courseService.getAll());
        return "/edit/teacher";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/editTeacher/{id}")
    public String postEditTeacher(@PathVariable(value = "id") long id,
                                  @Validated(UpdateEntity.class) @ModelAttribute("teacherDTO") TeacherDTO dto,
                                  BindingResult result,
                                  RedirectAttributes attr) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.teacherDTO", result);
            attr.addFlashAttribute("teacherDTO", dto);
            return "redirect:/editTeacher/" + id;
        }
        teacherService.update(dto);
        return "redirect:/teachers";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteTeacher/{id}")
    public String deleteTeacher(@PathVariable(value = "id") long id) {
        userService.removeById(id);
        return "redirect:/teachers";
    }
}
