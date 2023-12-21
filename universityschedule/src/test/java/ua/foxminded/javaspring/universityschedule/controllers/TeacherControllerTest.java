package ua.foxminded.javaspring.universityschedule.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.universityschedule.dto.TeacherDTO;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.mapper.TeacherMapperImpl;
import ua.foxminded.javaspring.universityschedule.services.CourseService;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;
import ua.foxminded.javaspring.universityschedule.services.UserService;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;

import java.util.HashSet;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherController.class)
@Import(TeacherMapperImpl.class)
@MockBean(UserService.class)
@MockBean(CourseService.class)
public class TeacherControllerTest {

    @MockBean
    private TeacherService teacherService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void addTeacher_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/addTeacher"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void addTeacher_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/addTeacher"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addTeacher_shouldReturnAddTeacherView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/addTeacher"))
                .andExpect(status().isOk())
                .andExpect(view().name("/add/teacher"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attributeExists("teacherDTO"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void saveTeacher_shouldRedirectToAddTeacherView_whenRequestParametersNotValid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/addTeacher")
                                          .flashAttr("teacherDTO", new TeacherDTO())
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/addTeacher"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void saveTeacher_shouldRedirectToHomeView() throws Exception {
        TeacherDTO dto = new TeacherDTO();
        dto.setId(0);
        dto.setUsername("test");
        dto.setFirstName("test");
        dto.setLastName("test");
        dto.setEmail("test@gmail.com");
        dto.setCourses(new HashSet<>());

        mvc.perform(MockMvcRequestBuilders.post("/addTeacher")
                                          .flashAttr("teacherDTO", dto)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    @WithAnonymousUser
    public void getTeachers_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/teachers"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getTeachers_shouldReturnTeachersView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/teachers")
                                          .with(user(new CustomUserDetails(new Teacher()))))
                .andExpect(status().isOk())
                .andExpect(view().name("/list/teachers"))
                .andExpect(model().attributeExists("teachers"));
    }

    @Test
    @WithAnonymousUser
    public void editTeacher_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/editTeacher/{id}", 1L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void editTeacher_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/editTeacher/{id}", 1L))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void editTeacher_shouldReturnEditTeacherView() throws Exception {
        Teacher teacher = new Teacher();
        when(teacherService.findById(1)).thenReturn(teacher);
        mvc.perform(MockMvcRequestBuilders.get("/editTeacher/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("/edit/teacher"))
                .andExpect(model().attributeExists("teacherDTO"))
                .andExpect(model().attributeExists("courses"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void postEditTeacher_shouldRedirectToEditTeacherView_whenRequestParametersNotValid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/editTeacher/{id}", 1L)
                                          .flashAttr("teacherDTO", new TeacherDTO())
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/editTeacher/" + 1L));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void postEditTeacher_shouldRedirectToTeachersView() throws Exception {
        TeacherDTO dto = new TeacherDTO();
        dto.setId(1);
        dto.setFirstName("test");
        dto.setLastName("test");
        dto.setCourses(new HashSet<>());

        mvc.perform(MockMvcRequestBuilders.post("/editTeacher/{id}", 1L)
                                          .flashAttr("teacherDTO", dto)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/teachers"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void deleteTeacher_shouldRedirectToTeachersView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/deleteTeacher/{id}", 1L)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
