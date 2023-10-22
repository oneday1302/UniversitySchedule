package ua.foxminded.javaspring.universityschedule.controllers.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.universityschedule.entities.Role;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.services.CourseService;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@MockBeans({@MockBean(CourseService.class), @MockBean(TeacherService.class)})
@WebMvcTest(AdminProfileController.class)
public class AdminProfileControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void profile_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/profile"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = { "TEACHER", "STUDENT" })
    public void profile_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/profile"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void profile_shouldReturnViewProfile() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.ADMIN);
        mvc.perform(MockMvcRequestBuilders.get("/admin/profile")
                                          .with(user(new CustomUserDetails(teacher))))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/profile"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithAnonymousUser
    public void editProfile_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/profile/edit"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = { "TEACHER", "STUDENT" })
    public void editProfile_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/profile/edit"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void editProfile_shouldReturnViewEditProfile() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.ADMIN);
        mvc.perform(MockMvcRequestBuilders.get("/admin/profile/edit")
                        .with(user(new CustomUserDetails(teacher))))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/edit/profile"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("courses"));
    }

    @Test
    public void postEditProfile_shouldRedirectToProfileView() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.ADMIN);
        mvc.perform(MockMvcRequestBuilders.post("/admin/profile/edit")
                                          .with(user(new CustomUserDetails(teacher)))
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
