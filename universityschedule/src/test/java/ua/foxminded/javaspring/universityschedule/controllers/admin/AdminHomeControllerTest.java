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
import ua.foxminded.javaspring.universityschedule.dto.LessonDTO;
import ua.foxminded.javaspring.universityschedule.entities.Course;
import ua.foxminded.javaspring.universityschedule.entities.Lesson;
import ua.foxminded.javaspring.universityschedule.services.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminHomeController.class)
@MockBeans({@MockBean(GroupService.class),
            @MockBean(CourseService.class),
            @MockBean(TeacherService.class),
            @MockBean(ClassroomService.class)})
public class AdminHomeControllerTest {

    @MockBean
    private LessonService lessonService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void getEvents_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/getEvents"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = { "TEACHER", "STUDENT" })
    public void getEvents_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/getEvents"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void getEvents_shouldReturnListOfLessons() throws Exception {
        LessonDTO dto = new LessonDTO();
        Lesson lesson = Lesson.builder()
                              .course(new Course("History"))
                              .date(LocalDate.of(2023, 10, 15))
                              .startTime(LocalTime.of(8, 0))
                              .endTime(LocalTime.of(8, 45))
                              .build();
        List<Lesson> lessons = Arrays.asList(lesson);
        when(lessonService.findByFilter(dto)).thenReturn(lessons);

        mvc.perform(MockMvcRequestBuilders.get("/admin/getEvents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(lesson.getId()));
    }

    @Test
    @WithAnonymousUser
    public void home_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/home"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = { "TEACHER", "STUDENT" })
    public void home_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/home"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void home_shouldReturnHomeView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/home"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attributeExists("teachers"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attributeExists("classrooms"));
    }
}
