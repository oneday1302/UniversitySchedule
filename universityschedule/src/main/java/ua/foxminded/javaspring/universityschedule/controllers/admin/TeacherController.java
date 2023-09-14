package ua.foxminded.javaspring.universityschedule.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.javaspring.universityschedule.entities.Role;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.services.CourseService;
import ua.foxminded.javaspring.universityschedule.services.EmailService;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;
import ua.foxminded.javaspring.universityschedule.services.UserService;
import ua.foxminded.javaspring.universityschedule.utils.PasswordGenerator;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class TeacherController {

    private final PasswordGenerator passwordGenerator;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final TeacherService teacherService;
    private final CourseService courseService;

    private static final String emailBodyFormat = "username: %s| password: %s";

    @GetMapping("/admin/addTeacher")
    public String addTeacher(Model model) {
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("password", passwordGenerator.generate());
        return "/admin/add/teacher";
    }

    @PostMapping("/admin/addTeacher")
    public String saveTeacher(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam String firstName,
                              @RequestParam String lastName,
                              @RequestParam String email,
                              @RequestParam(required = false) List<String> courses,
                              @RequestParam(defaultValue = "false") boolean admin) {

        Teacher teacher = new Teacher(username, passwordEncoder.encode(password), email, firstName, lastName);

        if (courses != null) {
            for (String course : courses) {
                teacher.addCourse(courseService.findByName(course));
            }
        }

        if (admin) {
            teacher.addRole(Role.ADMIN);
        }

        teacherService.add(teacher);

        emailService.sendEmail(email, firstName + " " + lastName, String.format(emailBodyFormat, username, password));

        return "redirect:/admin/home";
    }

    @GetMapping("/admin/teachers")
    public String getTeachers(Model model) {
        model.addAttribute("teachers", teacherService.getAll());
        return "/admin/list/teachers";
    }

    @GetMapping("/admin/editTeacher/{id}")
    public String editTeacher(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("teacher", teacherService.findById(id));
        model.addAttribute("courses", courseService.getAll());
        return "/admin/edit/teacher";
    }

    @PostMapping("/admin/editTeacher/{id}")
    public String postEditTeacher(@PathVariable(value = "id") long id,
                                  @RequestParam String firstName,
                                  @RequestParam String lastName,
                                  @RequestParam(required = false) List<String> courses,
                                  @RequestParam(defaultValue = "false") boolean admin) {

        Teacher teacher = teacherService.findById(id);

        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        if (admin) {
            teacher.addRole(Role.ADMIN);
        } else {
            teacher.removeRole(Role.ADMIN);
        }
        teacher.clearCourse();
        if (courses != null) {
            for (String course : courses) {
                teacher.addCourse(courseService.findByName(course));
            }
        }

        teacherService.update(teacher);

        return "redirect:/admin/teachers";
    }

    @GetMapping("/admin/deleteTeacher/{id}")
    public String deleteTeacher(@PathVariable(value = "id") long id) {
        userService.removeById(id);
        return "redirect:/admin/teachers";
    }
}
