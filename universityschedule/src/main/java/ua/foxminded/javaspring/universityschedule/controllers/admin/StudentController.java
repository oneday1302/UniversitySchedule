package ua.foxminded.javaspring.universityschedule.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.javaspring.universityschedule.entities.Student;
import ua.foxminded.javaspring.universityschedule.services.EmailService;
import ua.foxminded.javaspring.universityschedule.services.GroupService;
import ua.foxminded.javaspring.universityschedule.services.StudentService;
import ua.foxminded.javaspring.universityschedule.services.UserService;
import ua.foxminded.javaspring.universityschedule.utils.PasswordGenerator;

@RequiredArgsConstructor
@Controller
public class StudentController {

    private final PasswordGenerator passwordGenerator;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final StudentService studentService;
    private final GroupService groupService;

    private static final String emailBodyFormat = "username: %s| password: %s";

    @GetMapping("/admin/addStudent")
    public String addStudent(Model model) {
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("password", passwordGenerator.generate());
        return "/admin/add/student";
    }

    @PostMapping("/admin/addStudent")
    public String saveStudent(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam String firstName,
                              @RequestParam String lastName,
                              @RequestParam String email,
                              @RequestParam String group) {

        if (group.equals("Choose group")) {
            throw new IllegalArgumentException("Not selected group");
        }

        Student student = new Student(username, passwordEncoder.encode(password), email, firstName, lastName, groupService.findByName(group));

        studentService.add(student);

        emailService.sendEmail(email, firstName + " " + lastName, String.format(emailBodyFormat, username, password));

        return "redirect:/admin/home";
    }

    @GetMapping("/admin/students")
    public String getStudents(Model model) {
        model.addAttribute("students", studentService.getAll());
        return "/admin/list/students";
    }

    @GetMapping("/admin/editStudent/{id}")
    public String editStudent(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        model.addAttribute("groups", groupService.getAll());
        return "/admin/edit/student";
    }

    @PostMapping("/admin/editStudent/{id}")
    public String postEditStudent(@PathVariable(value = "id") long id,
                                  @RequestParam String firstName,
                                  @RequestParam String lastName,
                                  @RequestParam String group) {

        Student student = studentService.findById(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setGroup(groupService.findByName(group));

        studentService.update(student);

        return "redirect:/admin/students";
    }

    @GetMapping("/admin/deleteStudent/{id}")
    public String deleteStudent(@PathVariable(value = "id") long id) {
        userService.removeById(id);
        return "redirect:/admin/students";
    }
}
