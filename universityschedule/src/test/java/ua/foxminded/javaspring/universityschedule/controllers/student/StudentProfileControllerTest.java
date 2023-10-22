package ua.foxminded.javaspring.universityschedule.controllers.student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.universityschedule.entities.Group;
import ua.foxminded.javaspring.universityschedule.entities.Student;
import ua.foxminded.javaspring.universityschedule.services.StudentService;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockBean(StudentService.class)
@WebMvcTest(StudentProfileController.class)
public class StudentProfileControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void profile_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/student/profile"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = { "TEACHER", "ADMIN" })
    public void profile_shouldNotAllowAccessForAuthenticatedIfNotStudent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/student/profile"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void profile_shouldReturnViewProfile() throws Exception {
        Student student = Student.builder()
                                 .group(new Group())
                                 .build();
        mvc.perform(MockMvcRequestBuilders.get("/student/profile")
                                          .with(user(new CustomUserDetails(student))))
                .andExpect(status().isOk())
                .andExpect(view().name("student/profile"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    @WithAnonymousUser
    public void editProfile_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/student/profile/edit"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = { "TEACHER", "ADMIN" })
    public void editProfile_shouldNotAllowAccessForAuthenticatedIfNotStudent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/student/profile/edit"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void editProfile_shouldReturnViewEditProfile() throws Exception {
        Student student = Student.builder()
                                 .group(new Group())
                                 .build();
        mvc.perform(MockMvcRequestBuilders.get("/student/profile/edit")
                                          .with(user(new CustomUserDetails(student))))
                .andExpect(status().isOk())
                .andExpect(view().name("student/editProfile"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    public void postEditProfile_shouldRedirectToProfileView() throws Exception {
        Student student = Student.builder()
                                 .group(new Group())
                                 .build();
        mvc.perform(MockMvcRequestBuilders.post("/student/profile/edit")
                                          .with(user(new CustomUserDetails(student)))
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
