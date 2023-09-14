package ua.foxminded.javaspring.universityschedule.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.javaspring.universityschedule.entities.Lesson;
import ua.foxminded.javaspring.universityschedule.services.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Controller
public class LessonController {

    private final LessonService lessonService;
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final GroupService groupService;
    private final ClassroomService classroomService;

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    @GetMapping("/admin/addLesson")
    public String addLesson(Model model) {
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("teachers", teacherService.getAll());
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("classrooms", classroomService.getAll());
        return "/admin/add/lesson";
    }

    @PostMapping("/admin/addLesson")
    public String saveLesson(@RequestParam String course,
                             @RequestParam long teacher_id,
                             @RequestParam String group,
                             @RequestParam String classroom,
                             @RequestParam String date,
                             @RequestParam String startTime,
                             @RequestParam String endTime) {

        lessonService.add(new Lesson(courseService.findByName(course),
                                     teacherService.findById(teacher_id),
                                     groupService.findByName(group),
                                     classroomService.findByName(classroom),
                                     LocalDate.parse(date, dateFormat),
                                     LocalTime.parse(startTime, timeFormat),
                                     LocalTime.parse(endTime, timeFormat)));

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
        model.addAttribute("date", lesson.getDate().format(dateFormat));
        model.addAttribute("startTime", lesson.getStartTime().format(timeFormat));
        model.addAttribute("endTime", lesson.getEndTime().format(timeFormat));
        return "/admin/edit/lesson";
    }

    @PostMapping("/admin/editLesson/{id}")
    public String postEditLesson(@PathVariable(value = "id") long id,
                                 @RequestParam long courseId,
                                 @RequestParam long teacherId,
                                 @RequestParam long groupId,
                                 @RequestParam long classroomId,
                                 @RequestParam String date,
                                 @RequestParam String startTime,
                                 @RequestParam String endTime) {

        Lesson lesson = lessonService.findById(id);
        lesson.setCourse(courseService.findById(courseId));
        lesson.setTeacher(teacherService.findById(teacherId));
        lesson.setGroup(groupService.findById(groupId));
        lesson.setClassroom(classroomService.findById(classroomId));
        lesson.setDate(LocalDate.parse(date, dateFormat));
        lesson.setStartTime(LocalTime.parse(startTime, timeFormat));
        lesson.setEndTime(LocalTime.parse(endTime, timeFormat));
        lessonService.update(lesson);

        return "redirect:/admin/home";
    }

    @GetMapping("/admin/deleteLesson/{id}")
    public String deleteLesson(@PathVariable(value = "id") long id) {
        lessonService.removeById(id);
        return "redirect:/admin/home";
    }
}
