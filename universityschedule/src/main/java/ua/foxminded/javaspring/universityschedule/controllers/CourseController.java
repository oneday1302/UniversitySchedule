package ua.foxminded.javaspring.universityschedule.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.universityschedule.dto.CourseDTO;
import ua.foxminded.javaspring.universityschedule.services.CourseService;

@RequiredArgsConstructor
@Controller
@EnableMethodSecurity
public class CourseController {

    private final CourseService courseService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addCourse")
    public String addCourse() {
        return "/add/course";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addCourse")
    public String saveCourse(@ModelAttribute CourseDTO dto) {
        courseService.add(dto);
        return "redirect:/home";
    }

    @GetMapping("/courses")
    public String getCourses(Model model) {
        model.addAttribute("courses", courseService.getAll());
        return "/list/courses";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editCourse/{id}")
    public String editCourse(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("course", courseService.findById(id));
        return "/edit/course";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/editCourse/{id}")
    public String postEditCourse(@PathVariable(value = "id") long id, @ModelAttribute CourseDTO dto) {
        dto.setId(id);
        courseService.update(dto);
        return "redirect:/courses";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deleteCourse/{id}")
    public String deleteCourse(@PathVariable(value = "id") long id) {
        courseService.removeById(id);
        return "redirect:/courses";
    }
}
