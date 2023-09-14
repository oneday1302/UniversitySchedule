package ua.foxminded.javaspring.universityschedule.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.javaspring.universityschedule.entities.Classroom;
import ua.foxminded.javaspring.universityschedule.services.ClassroomService;

@RequiredArgsConstructor
@Controller
public class ClassroomController {

    private final ClassroomService classroomService;

    @GetMapping("/admin/addClassroom")
    public String addClassroom() {
        return "/admin/add/classroom";
    }

    @PostMapping("/admin/addClassroom")
    public String saveClassroom(@RequestParam String name) {
        classroomService.add(new Classroom(name));
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
    public String postEditClassroom(@PathVariable(value = "id") long id, @RequestParam String name) {
        Classroom classroom = classroomService.findById(id);
        classroom.setName(name);
        classroomService.update(classroom);
        return "redirect:/admin/classrooms";
    }

    @GetMapping("/admin/deleteClassroom/{id}")
    public String deleteClassroom(@PathVariable(value = "id") long id) {
        classroomService.removeById(id);
        return "redirect:/admin/classrooms";
    }
}
