package ua.foxminded.javaspring.universityschedule.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.javaspring.universityschedule.dto.GroupDTO;
import ua.foxminded.javaspring.universityschedule.services.GroupService;

@RequiredArgsConstructor
@Controller
@EnableMethodSecurity
public class GroupController {

    private final GroupService groupService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addGroup")
    public String addGroup() {
        return "/add/group";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addGroup")
    public String saveGroup(@ModelAttribute GroupDTO dto) {
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
        model.addAttribute("group", groupService.findById(id));
        return "/edit/group";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/editGroup/{id}")
    public String postEditGroup(@PathVariable(value = "id") long id, @ModelAttribute GroupDTO dto) {
        dto.setId(id);
        groupService.update(dto);
        return "redirect:/groups";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deleteGroup/{id}")
    public String deleteGroup(@PathVariable(value = "id") long id) {
        groupService.removeById(id);
        return "redirect:/groups";
    }
}
