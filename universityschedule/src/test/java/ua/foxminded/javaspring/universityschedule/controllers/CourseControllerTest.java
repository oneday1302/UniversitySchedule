package ua.foxminded.javaspring.universityschedule.controllers;

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
        mvc.perform(MockMvcRequestBuilders.get("/addCourse"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void addCourse_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/addCourse"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addCourse_shouldReturnAddCourseView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/addCourse"))
                .andExpect(status().isOk())
                .andExpect(view().name("/add/course"))
                .andExpect(model().attributeExists("courseDTO"));;
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void saveCourse_shouldRedirectToAddCourseView_whenRequestParametersNotValid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/addCourse")
                                          .param("id", "0")
                                          .param("name", "")
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/addCourse"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void saveCourse_shouldRedirectToHomeView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/addCourse")
                                          .param("id", "0")
                                          .param("name", "History")
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    @WithAnonymousUser
    public void getCourses_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/courses"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void getCourses_shouldReturnViewCourses() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/courses"))
                .andExpect(status().isOk())
                .andExpect(view().name("/list/courses"))
                .andExpect(model().attributeExists("courses"));
    }

    @Test
    @WithAnonymousUser
    public void editCourse_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/editCourse/{id}", 1L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void editCourse_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/editCourse/{id}", 1L))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void editCourse_shouldReturnViewEditCourse() throws Exception {
        Course course = new Course();
        when(courseService.findById(1)).thenReturn(course);
        mvc.perform(MockMvcRequestBuilders.get("/editCourse/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("/edit/course"))
                .andExpect(model().attributeExists("courseDTO"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void postEditCourse_shouldRedirectToEditCourseView_whenRequestParametersNotValid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/editCourse/{id}", 1L)
                                          .param("id", "0")
                                          .param("name", "")
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/editCourse/" + 1L));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void postEditCourse_shouldRedirectToCoursesView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/editCourse/{id}", 1L)
                                          .param("id", "1")
                                          .param("name", "History")
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/courses"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void deleteCourse_shouldRedirectToCoursesView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/deleteCourse/{id}", 1L)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
