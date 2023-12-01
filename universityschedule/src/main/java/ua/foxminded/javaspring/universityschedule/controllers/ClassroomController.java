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
import ua.foxminded.javaspring.universityschedule.dto.ClassroomDTO;
import ua.foxminded.javaspring.universityschedule.mapper.ClassroomMapper;
import ua.foxminded.javaspring.universityschedule.services.ClassroomService;
import ua.foxminded.javaspring.universityschedule.validation.CreateEntity;
import ua.foxminded.javaspring.universityschedule.validation.UpdateEntity;

@RequiredArgsConstructor
@Controller
@EnableMethodSecurity
public class ClassroomController {

    private final ClassroomService classroomService;
    private final ClassroomMapper mapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addClassroom")
    public String addClassroom(Model model) {
        if (!model.containsAttribute("classroomDTO")) {
            model.addAttribute("classroomDTO", new ClassroomDTO());
        }
        return "/add/classroom";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addClassroom")
    public String saveClassroom(@Validated(CreateEntity.class) @ModelAttribute("classroomDTO") ClassroomDTO dto,
                                BindingResult result,
                                RedirectAttributes attr) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.classroomDTO", result);
            attr.addFlashAttribute("classroomDTO", dto);
            return "redirect:/addClassroom";
        }
        classroomService.add(dto);
        return "redirect:/home";
    }

    @GetMapping("/classrooms")
    public String getClassrooms(Model model) {
        model.addAttribute("classrooms", classroomService.getAll());
        return "/list/classrooms";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editClassroom/{id}")
    public String editClassroom(@PathVariable(value = "id") long id, Model model) {
        if (!model.containsAttribute("classroomDTO")) {
            model.addAttribute("classroomDTO", mapper.classroomToDTO(classroomService.findById(id)));
        }
        return "/edit/classroom";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/editClassroom/{id}")
    public String postEditClassroom(@PathVariable(value = "id") long id,
                                    @Validated(UpdateEntity.class) @ModelAttribute("classroomDTO") ClassroomDTO dto,
                                    BindingResult result,
                                    RedirectAttributes attr) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.classroomDTO", result);
            attr.addFlashAttribute("classroomDTO", dto);
            return "redirect:/editClassroom/" + id;
        }
        classroomService.update(dto);
        return "redirect:/classrooms";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteClassroom/{id}")
    public String deleteClassroom(@PathVariable(value = "id") long id) {
        classroomService.removeById(id);
        return "redirect:/classrooms";
    }
}
