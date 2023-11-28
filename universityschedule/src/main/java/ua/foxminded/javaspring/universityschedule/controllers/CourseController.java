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
import ua.foxminded.javaspring.universityschedule.dto.CourseDTO;
import ua.foxminded.javaspring.universityschedule.services.CourseService;
import ua.foxminded.javaspring.universityschedule.validation.CreateEntity;
import ua.foxminded.javaspring.universityschedule.validation.UpdateEntity;

@RequiredArgsConstructor
@Controller
@EnableMethodSecurity
public class CourseController {

    private final CourseService courseService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addCourse")
    public String addCourse(Model model) {
        if (!model.containsAttribute("courseDTO")) {
            model.addAttribute("courseDTO", new CourseDTO());
        }
        return "/add/course";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addCourse")
    public String saveCourse(@Validated(CreateEntity.class) @ModelAttribute("courseDTO") CourseDTO dto,
                             BindingResult result,
                             RedirectAttributes attr) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.courseDTO", result);
            attr.addFlashAttribute("courseDTO", dto);
            return "redirect:/addCourse";
        }
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
        if (!model.containsAttribute("courseDTO")) {
            model.addAttribute("courseDTO", new CourseDTO(courseService.findById(id)));
        }
        return "/edit/course";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/editCourse/{id}")
    public String postEditCourse(@PathVariable(value = "id") long id,
                                 @Validated(UpdateEntity.class) @ModelAttribute("courseDTO") CourseDTO dto,
                                 BindingResult result,
                                 RedirectAttributes attr) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.courseDTO", result);
            attr.addFlashAttribute("courseDTO", dto);
            return "redirect:/editCourse/" + id;
        }
        courseService.update(dto);
        return "redirect:/courses";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteCourse/{id}")
    public String deleteCourse(@PathVariable(value = "id") long id) {
        courseService.removeById(id);
        return "redirect:/courses";
    }
}
