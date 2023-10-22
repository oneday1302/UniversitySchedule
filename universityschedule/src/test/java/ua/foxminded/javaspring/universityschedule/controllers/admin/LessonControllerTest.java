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
import ua.foxminded.javaspring.universityschedule.entities.*;
import ua.foxminded.javaspring.universityschedule.services.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockBeans({@MockBean(CourseService.class),
            @MockBean(TeacherService.class),
            @MockBean(GroupService.class),
            @MockBean(ClassroomService.class)})
@WebMvcTest(LessonController.class)
public class LessonControllerTest {

    @MockBean
    private LessonService lessonService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void addLesson_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/addLesson"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void addLesson_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/addLesson"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addLesson_shouldReturnAddLessonView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/addLesson"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/add/lesson"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attributeExists("teachers"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attributeExists("classrooms"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void saveLesson_shouldRedirectToHomeView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/addLesson")
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void editLesson_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/editLesson/{id}", 1L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void editLesson_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/editLesson/{id}", 1L))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void editLesson_shouldReturnViewEditLesson() throws Exception {
        Lesson lesson = Lesson.builder()
                              .course(new Course())
                              .teacher(new Teacher())
                              .group(new Group())
                              .classroom(new Classroom())
                              .date(LocalDate.of(2023, 10, 15))
                              .startTime(LocalTime.of(8, 0))
                              .endTime(LocalTime.of(8, 45))
                              .build();
        when(lessonService.findById(1)).thenReturn(lesson);
        mvc.perform(MockMvcRequestBuilders.get("/admin/editLesson/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/edit/lesson"))
                .andExpect(model().attributeExists("lesson"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attributeExists("teachers"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attributeExists("classrooms"))
                .andExpect(model().attributeExists("date"))
                .andExpect(model().attributeExists("startTime"))
                .andExpect(model().attributeExists("endTime"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void postEditLesson_shouldRedirectToHomeView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/editLesson/{id}", 1L)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void deleteLesson_shouldRedirectToHomeView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/deleteLesson/{id}", 1L)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
