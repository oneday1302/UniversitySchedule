package ua.foxminded.javaspring.universityschedule.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.universityschedule.dto.LessonDTO;
import ua.foxminded.javaspring.universityschedule.entities.Lesson;
import ua.foxminded.javaspring.universityschedule.services.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Controller
@EnableMethodSecurity
public class LessonController {

    private final LessonService lessonService;
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final GroupService groupService;
    private final ClassroomService classroomService;

    @GetMapping("/getEvents")
    @ResponseBody
    public List<Lesson> getEvents(@ModelAttribute LessonDTO dto) {
        return lessonService.findByFilter(dto);
    }

    @GetMapping("/lessonInfo/{id}")
    public String lessonInfo(@PathVariable(value = "id") long id, Model model) {
        Lesson lesson = lessonService.findById(id);
        model.addAttribute("lesson", lesson);
        return "/lessonInfo";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addLesson")
    public String addLesson(Model model) {
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("teachers", teacherService.getAll());
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("classrooms", classroomService.getAll());
        return "/add/lesson";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addLesson")
    public String saveLesson(@ModelAttribute LessonDTO dto) {
        lessonService.add(dto);
        return "redirect:/home";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editLesson/{id}")
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
        return "/edit/lesson";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/editLesson/{id}")
    public String postEditLesson(@PathVariable(value = "id") long id, @ModelAttribute LessonDTO dto) {
        dto.setId(id);
        lessonService.update(dto);
        return "redirect:/home";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deleteLesson/{id}")
    public String deleteLesson(@PathVariable(value = "id") long id) {
        lessonService.removeById(id);
        return "redirect:/home";
    }
}
