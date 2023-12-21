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
import ua.foxminded.javaspring.universityschedule.dto.StudentDTO;
import ua.foxminded.javaspring.universityschedule.entities.Group;
import ua.foxminded.javaspring.universityschedule.entities.Student;
import ua.foxminded.javaspring.universityschedule.mapper.StudentMapperImpl;
import ua.foxminded.javaspring.universityschedule.services.GroupService;
import ua.foxminded.javaspring.universityschedule.services.StudentService;
import ua.foxminded.javaspring.universityschedule.services.UserService;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(StudentController.class)
@Import(StudentMapperImpl.class)
@MockBean(UserService.class)
@MockBean(GroupService.class)
public class StudentControllerTest {

    @MockBean
    private StudentService studentService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void addStudent_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/addStudent"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void addStudent_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/addStudent"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addStudent_shouldReturnAddStudentView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/addStudent"))
                .andExpect(status().isOk())
                .andExpect(view().name("/add/student"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attributeExists("studentDTO"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void saveStudent_shouldRedirectToAddStudentView_whenRequestParametersNotValid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/addStudent")
                                          .flashAttr("studentDTO", new StudentDTO())
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/addStudent"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void saveStudent_shouldRedirectToHomeView() throws Exception {
        StudentDTO dto = new StudentDTO();
        dto.setId(0);
        dto.setUsername("test");
        dto.setFirstName("test");
        dto.setLastName("test");
        dto.setEmail("test@gmail.com");
        dto.setGroup(new Group());

        mvc.perform(MockMvcRequestBuilders.post("/addStudent")
                                          .flashAttr("studentDTO", dto)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    @WithAnonymousUser
    public void getStudents_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/students"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getStudents_shouldReturnStudentsView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/students")
                                          .with(user(new CustomUserDetails(new Student()))))
                .andExpect(status().isOk())
                .andExpect(view().name("/list/students"))
                .andExpect(model().attributeExists("students"));
    }

    @Test
    @WithAnonymousUser
    public void editStudent_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/editStudent/{id}", 1L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void editStudent_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/editStudent/{id}", 1L))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void editStudent_shouldReturnEditStudentView() throws Exception {
        Student student = Student.builder()
                                 .group(new Group())
                                 .build();
        when(studentService.findById(1)).thenReturn(student);
        mvc.perform(MockMvcRequestBuilders.get("/editStudent/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("/edit/student"))
                .andExpect(model().attributeExists("studentDTO"))
                .andExpect(model().attributeExists("groups"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void postEditStudent_shouldRedirectToEditStudentView_whenRequestParametersNotValid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/editStudent/{id}", 1L)
                                          .flashAttr("studentDTO", new StudentDTO())
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/editStudent/" + 1L));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void postEditStudent_shouldRedirectToStudentsView() throws Exception {
        StudentDTO dto = new StudentDTO();
        dto.setId(1);
        dto.setFirstName("test");
        dto.setLastName("test");
        dto.setGroup(new Group());

        mvc.perform(MockMvcRequestBuilders.post("/editStudent/{id}", 1L)
                                          .flashAttr("studentDTO", dto)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/students"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void deleteStudent_shouldRedirectToStudentsView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/deleteStudent/{id}", 1L)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
