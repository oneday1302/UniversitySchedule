package ua.foxminded.javaspring.universityschedule.controllers;

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
public class ClassroomController {

    private final ClassroomService classroomService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addClassroom")
    public String addClassroom() {
        return "/add/classroom";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addClassroom")
    public String saveClassroom(@ModelAttribute ClassroomDTO dto) {
        classroomService.add(dto);
        return "redirect:/home";
    }

    @GetMapping("/classrooms")
    public String getClassrooms(Model model) {
        model.addAttribute("classrooms", classroomService.getAll());
        return "/list/classrooms";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editClassroom/{id}")
    public String editClassroom(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("classroom", classroomService.findById(id));
        return "/edit/classroom";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/editClassroom/{id}")
    public String postEditClassroom(@PathVariable(value = "id") long id, @ModelAttribute ClassroomDTO dto) {
        dto.setId(id);
        classroomService.update(dto);
        return "redirect:/classrooms";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deleteClassroom/{id}")
    public String deleteClassroom(@PathVariable(value = "id") long id) {
        classroomService.removeById(id);
        return "redirect:/classrooms";
    }
}
