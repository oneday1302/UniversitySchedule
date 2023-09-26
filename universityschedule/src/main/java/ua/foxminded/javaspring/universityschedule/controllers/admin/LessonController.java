package ua.foxminded.javaspring.universityschedule.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.universityschedule.dto.LessonDTO;
import ua.foxminded.javaspring.universityschedule.entities.Lesson;
import ua.foxminded.javaspring.universityschedule.services.*;

import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Controller
public class LessonController {

    private final LessonService lessonService;
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final GroupService groupService;
    private final ClassroomService classroomService;

    @GetMapping("/admin/addLesson")
    public String addLesson(Model model) {
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("teachers", teacherService.getAll());
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("classrooms", classroomService.getAll());
        return "/admin/add/lesson";
    }

    @PostMapping("/admin/addLesson")
    public String saveLesson(@ModelAttribute LessonDTO dto) {
        lessonService.add(dto);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/editLesson/{id}")
    public String editLesson(@PathVariable(value = "id") long id, Model model) {
        Lesson lesson = lessonService.findById(id);
        model.addAttribute("lesson", lesson);
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("teachers", teacherService.getAll());
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("classrooms", classroomService.getAll());
        model.addAttribute("date", lesson.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        model.addAttribute("startTime", lesson.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        model.addAttribute("endTime", lesson.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        return "/admin/edit/lesson";
    }

    @PostMapping("/admin/editLesson/{id}")
    public String postEditLesson(@PathVariable(value = "id") long id, @ModelAttribute LessonDTO dto) {
        dto.setId(id);
        lessonService.update(dto);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/deleteLesson/{id}")
    public String deleteLesson(@PathVariable(value = "id") long id) {
        lessonService.removeById(id);
        return "redirect:/admin/home";
    }
}
