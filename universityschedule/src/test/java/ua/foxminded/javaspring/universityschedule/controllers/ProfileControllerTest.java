package ua.foxminded.javaspring.universityschedule.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.universityschedule.entities.Role;
import ua.foxminded.javaspring.universityschedule.entities.Student;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.services.CourseService;
import ua.foxminded.javaspring.universityschedule.services.StudentService;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfileController.class)
@MockBean(StudentService.class)
@MockBean(TeacherService.class)
@MockBean(CourseService.class)
public class ProfileControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void profile_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/profile"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void profile_shouldReturnViewProfile() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.TEACHER);
        mvc.perform(MockMvcRequestBuilders.get("/profile")
                        .with(user(new CustomUserDetails(teacher))))
                .andExpect(status().isOk())
                .andExpect(view().name("/profile"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithAnonymousUser
    public void editProfile_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/profile/edit"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void editProfile_shouldReturnViewEditProfileAdmin() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.ADMIN);
        mvc.perform(MockMvcRequestBuilders.get("/profile/edit")
                        .with(user(new CustomUserDetails(teacher))))
                .andExpect(status().isOk())
                .andExpect(view().name("/editProfile"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("courses"));
    }

    @Test
    public void editProfile_shouldReturnViewEditProfileTeacher() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.TEACHER);
        mvc.perform(MockMvcRequestBuilders.get("/profile/edit")
                        .with(user(new CustomUserDetails(teacher))))
                .andExpect(status().isOk())
                .andExpect(view().name("/editProfile"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void editProfile_shouldReturnViewEditProfileStudent() throws Exception {
        Student student = new Student();
        student.addRole(Role.STUDENT);
        mvc.perform(MockMvcRequestBuilders.get("/profile/edit")
                        .with(user(new CustomUserDetails(student))))
                .andExpect(status().isOk())
                .andExpect(view().name("/editProfile"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void postEditProfile_shouldRedirectToProfileView() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.TEACHER);
        mvc.perform(MockMvcRequestBuilders.post("/profile/edit")
                        .with(user(new CustomUserDetails(teacher)))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
