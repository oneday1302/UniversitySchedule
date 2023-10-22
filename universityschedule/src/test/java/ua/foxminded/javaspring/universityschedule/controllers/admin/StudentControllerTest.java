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
import ua.foxminded.javaspring.universityschedule.entities.Group;
import ua.foxminded.javaspring.universityschedule.entities.Student;
import ua.foxminded.javaspring.universityschedule.services.GroupService;
import ua.foxminded.javaspring.universityschedule.services.StudentService;
import ua.foxminded.javaspring.universityschedule.services.UserService;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@MockBeans({@MockBean(UserService.class),
            @MockBean(GroupService.class)})
@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @MockBean
    private StudentService studentService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void addStudent_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/addStudent"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void addStudent_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/addStudent"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addStudent_shouldReturnAddStudentView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/addStudent"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/add/student"))
                .andExpect(model().attributeExists("groups"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void saveStudent_shouldRedirectToHomeView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/addStudent")
                                          .param("groupId", "1")
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void getStudents_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/students"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void getStudents_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/students"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void getStudents_shouldReturnStudentsView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/list/students"))
                .andExpect(model().attributeExists("students"));
    }

    @Test
    @WithAnonymousUser
    public void editStudent_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/editStudent/{id}", 1L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void editStudent_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/editStudent/{id}", 1L))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void editStudent_shouldReturnEditStudentView() throws Exception {
        Student student = Student.builder()
                                 .group(new Group())
                                 .build();
        when(studentService.findById(1)).thenReturn(student);
        mvc.perform(MockMvcRequestBuilders.get("/admin/editStudent/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/edit/student"))
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attributeExists("groups"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void postEditStudent_shouldRedirectToStudentsView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/editStudent/{id}", 1L)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void deleteStudent_shouldRedirectToStudentsView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/deleteStudent/{id}", 1L)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
