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
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.services.CourseService;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;
import ua.foxminded.javaspring.universityschedule.services.UserService;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBeans({@MockBean(UserService.class),
            @MockBean(CourseService.class)})
@WebMvcTest(TeacherController.class)
public class TeacherControllerTest {

    @MockBean
    private TeacherService teacherService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void addTeacher_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/addTeacher"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void addTeacher_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/addTeacher"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addTeacher_shouldReturnAddTeacherView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/addTeacher"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/add/teacher"))
                .andExpect(model().attributeExists("courses"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void saveTeacher_shouldRedirectToHomeView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/addTeacher")
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void getTeachers_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/teachers"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void getTeachers_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/teachers"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void getTeachers_shouldReturnTeachersView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/teachers"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/list/teachers"))
                .andExpect(model().attributeExists("teachers"));
    }

    @Test
    @WithAnonymousUser
    public void editTeacher_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/editTeacher/{id}", 1L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void editTeacher_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/editTeacher/{id}", 1L))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void editTeacher_shouldReturnEditTeacherView() throws Exception {
        Teacher teacher = new Teacher();
        when(teacherService.findById(1)).thenReturn(teacher);
        mvc.perform(MockMvcRequestBuilders.get("/admin/editTeacher/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/edit/teacher"))
                .andExpect(model().attributeExists("teacher"))
                .andExpect(model().attributeExists("courses"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void postEditTeacher_shouldRedirectToTeachersView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/editTeacher/{id}", 1L)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void deleteTeacher_shouldRedirectToTeachersView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/deleteTeacher/{id}", 1L)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
