package ua.foxminded.javaspring.universityschedule.controllers.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.foxminded.javaspring.universityschedule.entities.*;
import ua.foxminded.javaspring.universityschedule.services.ClassroomService;
import ua.foxminded.javaspring.universityschedule.services.CourseService;
import ua.foxminded.javaspring.universityschedule.services.GroupService;
import ua.foxminded.javaspring.universityschedule.services.LessonService;
import ua.foxminded.javaspring.universityschedule.utils.FullCalendarEvent;
import ua.foxminded.javaspring.universityschedule.utils.LessonFilter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class TeacherHomeController {

    private final LessonService lessonService;
    private final CourseService courseService;
    private final GroupService groupService;
    private final ClassroomService classroomService;

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @GetMapping("/teacher/getEvents")
    @ResponseBody
    public List<FullCalendarEvent> getEvents(Authentication authentication,
                                             @RequestParam long courseId,
                                             @RequestParam long groupId,
                                             @RequestParam long classroomId,
                                             @RequestParam String dateFrom,
                                             @RequestParam String dateTo) {
        Course course = null;
        if (courseId != 0) {
            course = courseService.findById(courseId);
        }

        Group group = null;
        if (groupId != 0) {
            group = groupService.findById(groupId);
        }

        Classroom classroom = null;
        if (classroomId != 0) {
            classroom = classroomService.findById(classroomId);
        }

        LocalDate from = null;
        if (!dateFrom.isEmpty()) {
            from = LocalDate.parse(dateFrom, dateFormat);
        }

        LocalDate to = null;
        if (!dateTo.isEmpty()) {
            to = LocalDate.parse(dateTo, dateFormat);
        }

        if (from == null && to == null) {
            LocalDate initial = LocalDate.now();
            from = initial.withDayOfMonth(1);
            to = initial.withDayOfMonth(initial.lengthOfMonth());
        }

        Teacher teacher = (Teacher) authentication.getPrincipal();
        LessonFilter filter = LessonFilter.builder()
                                          .course(course)
                                          .teacher(teacher)
                                          .group(group)
                                          .classroom(classroom)
                                          .from(from)
                                          .to(to)
                                          .build();

        List<FullCalendarEvent> events = new ArrayList<>();
        for (Lesson lesson : lessonService.findByFilter(filter)) {
            events.add(new FullCalendarEvent(lesson));
        }
        return events;
    }

    @GetMapping("/teacher/home")
    public String home(Model model) {
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("classrooms", classroomService.getAll());
        return "teacher/home";
    }
}
