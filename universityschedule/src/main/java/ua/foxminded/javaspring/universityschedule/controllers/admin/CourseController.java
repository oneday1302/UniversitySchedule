package ua.foxminded.javaspring.universityschedule.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.javaspring.universityschedule.entities.Course;
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
    public String saveCourse(@RequestParam String name) {
        courseService.add(new Course(name));
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
    public String postEditCourse(@PathVariable(value = "id") long id, @RequestParam String name) {
        Course course = courseService.findById(id);
        course.setName(name);
        courseService.update(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/admin/deleteCourse/{id}")
    public String deleteCourse(@PathVariable(value = "id") long id) {
        courseService.removeById(id);
        return "redirect:/admin/courses";
    }
}
