package ua.foxminded.javaspring.universityschedule.controllers.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.universityschedule.entities.Course;
import ua.foxminded.javaspring.universityschedule.services.CourseService;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @MockBean
    private CourseService courseService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void addCourse_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/addCourse"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void addCourse_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/addCourse"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void saveCourse_shouldRedirectToHomeView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/addCourse")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void getCourses_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/courses"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void getCourses_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/courses"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void getCourses_shouldReturnViewCourses() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/courses"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/list/courses"))
                .andExpect(model().attributeExists("courses"));
    }

    @Test
    @WithAnonymousUser
    public void editCourse_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/editCourse/{id}", 1L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void editCourse_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/editCourse/{id}", 1L))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void editCourse_shouldReturnViewEditCourse() throws Exception {
        Course course = new Course();
        when(courseService.findById(1)).thenReturn(course);
        mvc.perform(MockMvcRequestBuilders.get("/admin/editCourse/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/edit/course"))
                .andExpect(model().attributeExists("course"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void postEditCourse_shouldRedirectToCoursesView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/editCourse/{id}", 1L)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void deleteCourse_shouldRedirectToCoursesView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/deleteCourse/{id}", 1L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
