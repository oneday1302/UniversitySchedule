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
import ua.foxminded.javaspring.universityschedule.dto.GroupDTO;
import ua.foxminded.javaspring.universityschedule.services.GroupService;
import ua.foxminded.javaspring.universityschedule.validation.CreateEntity;
import ua.foxminded.javaspring.universityschedule.validation.UpdateEntity;

@RequiredArgsConstructor
@Controller
@EnableMethodSecurity
public class GroupController {

    private final GroupService groupService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addGroup")
    public String addGroup(Model model) {
        if (!model.containsAttribute("groupDTO")) {
            model.addAttribute("groupDTO", new GroupDTO());
        }
        return "/add/group";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addGroup")
    public String saveGroup(@Validated(CreateEntity.class) @ModelAttribute("groupDTO") GroupDTO dto,
                            BindingResult result,
                            RedirectAttributes attr) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.groupDTO", result);
            attr.addFlashAttribute("groupDTO", dto);
            return "redirect:/addGroup";
        }
        groupService.add(dto);
        return "redirect:/home";
    }

    @GetMapping("/groups")
    public String getGroup(Model model) {
        model.addAttribute("groups", groupService.getAll());
        return "/list/groups";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editGroup/{id}")
    public String editGroup(@PathVariable(value = "id") long id, Model model) {
        if (!model.containsAttribute("groupDTO")) {
            model.addAttribute("groupDTO", new GroupDTO(groupService.findById(id)));
        }
        return "/edit/group";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/editGroup/{id}")
    public String postEditGroup(@PathVariable(value = "id") long id,
                                @Validated(UpdateEntity.class) @ModelAttribute("groupDTO") GroupDTO dto,
                                BindingResult result,
                                RedirectAttributes attr) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.groupDTO", result);
            attr.addFlashAttribute("groupDTO", dto);
            return "redirect:/editGroup/" + id;
        }
        groupService.update(dto);
        return "redirect:/groups";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteGroup/{id}")
    public String deleteGroup(@PathVariable(value = "id") long id) {
        groupService.removeById(id);
        return "redirect:/groups";
    }
}
