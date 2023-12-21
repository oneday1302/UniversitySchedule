package ua.foxminded.javaspring.universityschedule.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.javaspring.universityschedule.dto.PasswordDTO;
import ua.foxminded.javaspring.universityschedule.dto.TeacherDTO;
import ua.foxminded.javaspring.universityschedule.entities.Role;
import ua.foxminded.javaspring.universityschedule.entities.Student;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.entities.User;
import ua.foxminded.javaspring.universityschedule.mapper.StudentMapper;
import ua.foxminded.javaspring.universityschedule.mapper.TeacherMapper;
import ua.foxminded.javaspring.universityschedule.services.CourseService;
import ua.foxminded.javaspring.universityschedule.services.StudentService;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;
import ua.foxminded.javaspring.universityschedule.services.UserService;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;
import ua.foxminded.javaspring.universityschedule.validation.UpdateAdminProfile;
import ua.foxminded.javaspring.universityschedule.validation.UpdateUserProfile;

@RequiredArgsConstructor
@Controller
public class ProfileController {

    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final TeacherMapper teacherMapper;
    private final StudentMapper studentMapper;

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        if (principal.getAuthorities().contains(Role.TEACHER)) {
            Teacher teacher = principal.unwrap(Teacher.class);
            model.addAttribute("user", teacher);
        } else {
            Student student = principal.unwrap(Student.class);
            model.addAttribute("user", student);
        }
        return "/profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model, Authentication authentication) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        if (principal.getAuthorities().contains(Role.TEACHER)) {
            if (principal.getAuthorities().contains(Role.ADMIN)) {
                model.addAttribute("courses", courseService.getAll());
            }
            if (!model.containsAttribute("userDTO")) {
                model.addAttribute("userDTO", teacherMapper.teacherToDTO(principal.unwrap(Teacher.class)));
            }
        } else {
            if (!model.containsAttribute("userDTO")) {
                model.addAttribute("userDTO", studentMapper.studentToDTO(principal.unwrap(Student.class)));
            }
        }
        return "/editProfile";
    }

    @PostMapping("/profile/edit")
    public String postEditProfile(Authentication authentication,
                                  @ModelAttribute("userDTO") TeacherDTO dto,
                                  RedirectAttributes attr) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        attr.addFlashAttribute("userDTO", dto);
        if (principal.getAuthorities().contains(Role.ADMIN)) {
            return "redirect:/profile/editAdminProfile";
        }
        return "redirect:/profile/editUserProfile";
    }

    @GetMapping("/profile/editAdminProfile")
    public String editAdminProfile(Authentication authentication,
                                   @Validated(UpdateAdminProfile.class) @ModelAttribute("userDTO") TeacherDTO dto,
                                   BindingResult result,
                                   RedirectAttributes attr,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.userDTO", result);
            attr.addFlashAttribute("userDTO", dto);
            return "redirect:/profile/edit";
        }

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        Teacher teacher = principal.unwrap(Teacher.class);
        dto.setId(teacher.getId());
        dto.setAdmin(teacher.isAdmin());
        teacherService.update(dto);

        new SecurityContextLogoutHandler().logout(request, response, authentication);
        return "redirect:/login";
    }

    @GetMapping("/profile/editUserProfile")
    public String editUserProfile(Authentication authentication,
                                  @Validated(UpdateUserProfile.class) @ModelAttribute("userDTO") TeacherDTO dto,
                                  BindingResult result,
                                  RedirectAttributes attr,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.userDTO", result);
            attr.addFlashAttribute("userDTO", dto);
            return "redirect:/profile/edit";
        }
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        if (principal.getAuthorities().contains(Role.TEACHER)) {
            Teacher teacher = principal.unwrap(Teacher.class);
            dto.setId(teacher.getId());
            teacherService.update(dto);
        } else {
            Student student = principal.unwrap(Student.class);
            dto.setId(student.getId());
            studentService.update(studentMapper.teacherDTOToStudentDTO(dto));
        }

        new SecurityContextLogoutHandler().logout(request, response, authentication);
        return "redirect:/login";
    }

    @GetMapping("/profile/editPassword")
    public String editPassword(Model model) {
        if (!model.containsAttribute("passwordDTO")) {
            model.addAttribute("passwordDTO", new PasswordDTO());
        }
        return "/editPassword";
    }

    @PostMapping("/profile/editPassword")
    public String postEditPassword(Authentication authentication,
                                   @Validated(UpdateUserProfile.class) @ModelAttribute("passwordDTO") PasswordDTO dto,
                                   BindingResult result,
                                   RedirectAttributes attr,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.passwordDTO", result);
            attr.addFlashAttribute("passwordDTO", dto);
            return "redirect:/profile/editPassword";
        }
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        User user = principal.unwrap(User.class);
        dto.setId(user.getId());
        userService.updatePassword(dto);

        new SecurityContextLogoutHandler().logout(request, response, authentication);
        return "redirect:/login";
    }
}
