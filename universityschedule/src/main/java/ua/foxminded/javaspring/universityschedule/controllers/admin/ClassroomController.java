package ua.foxminded.javaspring.universityschedule.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.universityschedule.dto.ClassroomDTO;
import ua.foxminded.javaspring.universityschedule.services.ClassroomService;

@RequiredArgsConstructor
@Controller
@EnableMethodSecurity
@PreAuthorize("hasAuthority('ADMIN')")
public class ClassroomController {

    private final ClassroomService classroomService;

    @GetMapping("/admin/addClassroom")
    public String addClassroom() {
        return "/admin/add/classroom";
    }

    @PostMapping("/admin/addClassroom")
    public String saveClassroom(@ModelAttribute ClassroomDTO dto) {
        classroomService.add(dto);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/classrooms")
    public String getClassrooms(Model model) {
        model.addAttribute("classrooms", classroomService.getAll());
        return "/admin/list/classrooms";
    }

    @GetMapping("/admin/editClassroom/{id}")
    public String editClassroom(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("classroom", classroomService.findById(id));
        return "/admin/edit/classroom";
    }

    @PostMapping("/admin/editClassroom/{id}")
    public String postEditClassroom(@PathVariable(value = "id") long id, @ModelAttribute ClassroomDTO dto) {
        dto.setId(id);
        classroomService.update(dto);
        return "redirect:/admin/classrooms";
    }

    @GetMapping("/admin/deleteClassroom/{id}")
    public String deleteClassroom(@PathVariable(value = "id") long id) {
        classroomService.removeById(id);
        return "redirect:/admin/classrooms";
    }
}
