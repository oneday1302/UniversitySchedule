package ua.foxminded.javaspring.universityschedule.controllers;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        mvc.perform(MockMvcRequestBuilders.get("/addClassroom"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void addClassroom_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/addClassroom"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addClassroom_shouldReturnAddClassroomView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/addClassroom"))
                .andExpect(status().isOk())
                .andExpect(view().name("/add/classroom"))
                .andExpect(model().attributeExists("classroomDTO"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void saveClassroom_shouldRedirectToAddClassroomView_whenRequestParametersNotValid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/addClassroom")
                                          .param("id", "0")
                                          .param("name", "")
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/addClassroom"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void saveClassroom_shouldRedirectToHomeView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/addClassroom")
                                          .param("id", "0")
                                          .param("name", "121")
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    @WithAnonymousUser
    public void getClassrooms_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/classrooms"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void getClassrooms_shouldReturnViewClassrooms() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/classrooms"))
                .andExpect(status().isOk())
                .andExpect(view().name("/list/classrooms"))
                .andExpect(model().attributeExists("classrooms"));
    }

    @Test
    @WithAnonymousUser
    public void editClassroom_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/editClassroom/{id}", 1L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void editClassroom_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/editClassroom/{id}", 1L))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void editClassroom_shouldReturnEditClassroomView() throws Exception {
        Classroom classroom = new Classroom();
        when(classroomService.findById(1)).thenReturn(classroom);
        mvc.perform(MockMvcRequestBuilders.get("/editClassroom/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("/edit/classroom"))
                .andExpect(model().attributeExists("classroomDTO"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void postEditClassroom_shouldRedirectToEditClassroom_whenRequestParametersNotValid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/editClassroom/{id}", 1L)
                                          .param("id", "0")
                                          .param("name", "")
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/editClassroom/" + 1L));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void postEditClassroom_shouldRedirectToClassroomsView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/editClassroom/{id}", 1L)
                                          .param("id", "1")
                                          .param("name", "121")
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/classrooms"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void deleteClassroom_shouldRedirectToClassroomsView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/deleteClassroom/{id}", 1L)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
