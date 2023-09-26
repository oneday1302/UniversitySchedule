package ua.foxminded.javaspring.universityschedule.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.universityschedule.dto.CourseDTO;
import ua.foxminded.javaspring.universityschedule.services.CourseService;

@RequiredArgsConstructor
@Controller
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/admin/addCourse")
    public String addCourse() {
        return "/admin/add/course";
    }

    @PostMapping("/admin/addCourse")
    public String saveCourse(@ModelAttribute CourseDTO dto) {
        courseService.add(dto);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/courses")
    public String getCourses(Model model) {
        model.addAttribute("courses", courseService.getAll());
        return "/admin/list/courses";
    }

    @GetMapping("/admin/editCourse/{id}")
    public String editCourse(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("course", courseService.findById(id));
        return "/admin/edit/course";
    }

    @PostMapping("/admin/editCourse/{id}")
    public String postEditCourse(@PathVariable(value = "id") long id, @ModelAttribute CourseDTO dto) {
        dto.setId(id);
        courseService.update(dto);
        return "redirect:/admin/courses";
    }

    @GetMapping("/admin/deleteCourse/{id}")
    public String deleteCourse(@PathVariable(value = "id") long id) {
        courseService.removeById(id);
        return "redirect:/admin/courses";
    }
}
