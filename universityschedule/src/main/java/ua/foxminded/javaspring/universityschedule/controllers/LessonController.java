package ua.foxminded.javaspring.universityschedule.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.javaspring.universityschedule.dto.LessonDTO;
import ua.foxminded.javaspring.universityschedule.entities.Lesson;
import ua.foxminded.javaspring.universityschedule.entities.Role;
import ua.foxminded.javaspring.universityschedule.mapper.LessonMapper;
import ua.foxminded.javaspring.universityschedule.services.*;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;
import ua.foxminded.javaspring.universityschedule.validation.CreateEntity;
import ua.foxminded.javaspring.universityschedule.validation.FilterForLesson;
import ua.foxminded.javaspring.universityschedule.validation.UpdateEntity;

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
    private final LessonMapper mapper;

    @GetMapping("/getEvents")
    @ResponseBody
    public List<Lesson> getEvents(@Validated(FilterForLesson.class) @ModelAttribute LessonDTO dto) {
        return lessonService.findByFilter(dto);
    }

    @GetMapping("/getLesson/{id}")
    public String getLesson(@PathVariable(value = "id") long id, Authentication authentication) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        if (principal.getAuthorities().contains(Role.ADMIN)) {
            return "redirect:/editLesson/" + id;
        } else {
            return "redirect:/lessonInfo/" + id;
        }
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
        if (!model.containsAttribute("lessonDTO")) {
            model.addAttribute("lessonDTO", new LessonDTO());
        }
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("teachers", teacherService.getAll());
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("classrooms", classroomService.getAll());
        return "/add/lesson";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addLesson")
    public String saveLesson(@Validated(CreateEntity.class) @ModelAttribute("lessonDTO") LessonDTO lessonDTO,
                             BindingResult result,
                             RedirectAttributes attr) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.lessonDTO", result);
            attr.addFlashAttribute("lessonDTO", lessonDTO);
            return "redirect:/addLesson";
        }
        lessonService.add(lessonDTO);
        return "redirect:/home";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editLesson/{id}")
    public String editLesson(@PathVariable(value = "id") long id, Model model) {
        if (!model.containsAttribute("lessonDTO")) {
            model.addAttribute("lessonDTO", mapper.lessonToDTO(lessonService.findById(id)));
        }
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("teachers", teacherService.getAll());
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("classrooms", classroomService.getAll());
        return "/edit/lesson";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/editLesson/{id}")
    public String postEditLesson(@PathVariable(value = "id") long id,
                                 @Validated(UpdateEntity.class) @ModelAttribute("lessonDTO") LessonDTO dto,
                                 BindingResult result,
                                 RedirectAttributes attr) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.lessonDTO", result);
            attr.addFlashAttribute("lessonDTO", dto);
            return "redirect:/editLesson/" + id;
        }
        lessonService.update(dto);
        return "redirect:/home";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteLesson/{id}")
    public String deleteLesson(@PathVariable(value = "id") long id) {
        lessonService.removeById(id);
        return "redirect:/home";
    }
}
