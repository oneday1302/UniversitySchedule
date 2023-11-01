package ua.foxminded.javaspring.universityschedule.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.universityschedule.entities.Role;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.services.ClassroomService;
import ua.foxminded.javaspring.universityschedule.services.CourseService;
import ua.foxminded.javaspring.universityschedule.services.GroupService;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(HomeController.class)
@MockBean(GroupService.class)
@MockBean(CourseService.class)
@MockBean(TeacherService.class)
@MockBean(ClassroomService.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void home_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void home_shouldReturnHomeView() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.TEACHER);
        teacher.addRole(Role.ADMIN);
        mvc.perform(MockMvcRequestBuilders.get("/home")
                                          .with(user(new CustomUserDetails(teacher))))
                .andExpect(status().isOk())
                .andExpect(view().name("/home"))
                .andExpect(model().attributeExists("role"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attributeExists("teachers"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attributeExists("classrooms"));
    }
}
