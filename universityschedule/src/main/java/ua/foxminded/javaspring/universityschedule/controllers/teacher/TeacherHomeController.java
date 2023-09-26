package ua.foxminded.javaspring.universityschedule.controllers.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.foxminded.javaspring.universityschedule.dto.LessonDTO;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.services.*;
import ua.foxminded.javaspring.universityschedule.utils.Event;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class TeacherHomeController {

    private final UserService userService;
    private final LessonService lessonService;
    private final CourseService courseService;
    private final GroupService groupService;
    private final ClassroomService classroomService;

    @GetMapping("/teacher/getEvents")
    @ResponseBody
    public List<Event> getEvents(Authentication authentication, @ModelAttribute LessonDTO dto) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Teacher teacher = (Teacher) userService.findByUsername(userDetails.getUsername());
        dto.setTeacherId(teacher.getId());
        return new ArrayList<>(lessonService.findByFilter(dto));
    }

    @GetMapping("/teacher/home")
    public String home(Model model) {
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("classrooms", classroomService.getAll());
        return "teacher/home";
    }
}
