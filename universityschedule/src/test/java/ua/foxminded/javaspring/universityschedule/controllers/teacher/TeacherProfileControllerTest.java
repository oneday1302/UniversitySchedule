package ua.foxminded.javaspring.universityschedule.controllers.teacher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.universityschedule.entities.Role;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockBean(TeacherService.class)
@WebMvcTest(TeacherProfileController.class)
public class TeacherProfileControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void profile_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/teacher/profile"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = { "STUDENT", "ADMIN" })
    public void profile_shouldNotAllowAccessForAuthenticatedIfNotTeacher() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/teacher/profile"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void profile_shouldReturnViewProfile() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.TEACHER);
        mvc.perform(MockMvcRequestBuilders.get("/teacher/profile")
                                          .with(user(new CustomUserDetails(teacher))))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher/profile"))
                .andExpect(model().attributeExists("teacher"));
    }

    @Test
    @WithAnonymousUser
    public void editProfile_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/teacher/profile/edit"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = { "STUDENT", "ADMIN" })
    public void editProfile_shouldNotAllowAccessForAuthenticatedIfNotTeacher() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/teacher/profile/edit"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void editProfile_shouldReturnViewEditProfile() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.TEACHER);
        mvc.perform(MockMvcRequestBuilders.get("/teacher/profile/edit")
                                          .with(user(new CustomUserDetails(teacher))))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher/editProfile"))
                .andExpect(model().attributeExists("teacher"));
    }

    @Test
    public void postEditProfile_shouldRedirectToProfileView() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.TEACHER);
        mvc.perform(MockMvcRequestBuilders.post("/teacher/profile/edit")
                                          .with(user(new CustomUserDetails(teacher)))
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
