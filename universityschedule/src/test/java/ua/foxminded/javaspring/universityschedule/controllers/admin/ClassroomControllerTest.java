package ua.foxminded.javaspring.universityschedule.controllers.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.universityschedule.entities.Classroom;
import ua.foxminded.javaspring.universityschedule.services.ClassroomService;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClassroomController.class)
public class ClassroomControllerTest {

    @MockBean
    private ClassroomService classroomService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void addClassroom_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/addClassroom"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void addClassroom_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/addClassroom"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void saveClassroom_shouldRedirectToHomeView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/addClassroom")
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void getClassrooms_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/classrooms"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void getClassrooms_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/classrooms"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void getClassrooms_shouldReturnViewClassrooms() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/classrooms"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/list/classrooms"))
                .andExpect(model().attributeExists("classrooms"));
    }

    @Test
    @WithAnonymousUser
    public void editClassroom_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/editClassroom/{id}", 1L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void editClassroom_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/editClassroom/{id}", 1L))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void editClassroom_shouldReturnViewEditClassroom() throws Exception {
        Classroom classroom = new Classroom();
        when(classroomService.findById(1)).thenReturn(classroom);
        mvc.perform(MockMvcRequestBuilders.get("/admin/editClassroom/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/edit/classroom"))
                .andExpect(model().attributeExists("classroom"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void postEditClassroom_shouldRedirectToClassroomsView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/editClassroom/{id}", 1L)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void deleteClassroom_shouldRedirectToClassroomsView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/deleteClassroom/{id}", 1L)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
