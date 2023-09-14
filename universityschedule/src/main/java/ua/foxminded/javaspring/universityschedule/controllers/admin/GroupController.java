package ua.foxminded.javaspring.universityschedule.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.javaspring.universityschedule.entities.Group;
import ua.foxminded.javaspring.universityschedule.services.GroupService;

@RequiredArgsConstructor
@Controller
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/admin/addGroup")
    public String addGroup() {
        return "/admin/add/group";
    }

    @PostMapping("/admin/addGroup")
    public String saveGroup(@RequestParam String name) {
        groupService.add(new Group(name));
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/groups")
    public String getGroup(Model model) {
        model.addAttribute("groups", groupService.getAll());
        return "/admin/list/groups";
    }

    @GetMapping("/admin/editGroup/{id}")
    public String editGroup(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("group", groupService.findById(id));
        return "/admin/edit/group";
    }

    @PostMapping("/admin/editGroup/{id}")
    public String postEditGroup(@PathVariable(value = "id") long id, @RequestParam String name) {
        Group group = groupService.findById(id);
        group.setName(name);
        groupService.update(group);
        return "redirect:/admin/groups";
    }

    @GetMapping("/admin/deleteGroup/{id}")
    public String deleteGroup(@PathVariable(value = "id") long id) {
        groupService.removeById(id);
        return "redirect:/admin/groups";
    }
}
