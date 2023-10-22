package ua.foxminded.javaspring.universityschedule.controllers.student;

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
import ua.foxminded.javaspring.universityschedule.entities.*;
import ua.foxminded.javaspring.universityschedule.services.ClassroomService;
import ua.foxminded.javaspring.universityschedule.services.CourseService;
import ua.foxminded.javaspring.universityschedule.services.LessonService;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@MockBeans({@MockBean(CourseService.class),
            @MockBean(TeacherService.class),
            @MockBean(ClassroomService.class)})
@WebMvcTest(StudentHomeController.class)
public class StudentHomeControllerTest {

    @MockBean
    private LessonService lessonService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void getEvents_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/student/getEvents"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getEvents_shouldNotAllowAccessForAuthenticatedIfNotStudent() throws Exception {
        User user = new User();
        user.addRole(Role.TEACHER);
        user.addRole(Role.ADMIN);
        mvc.perform(MockMvcRequestBuilders.get("/student/getEvents")
                                          .with(user(new CustomUserDetails(user))))
                .andExpect(status().isForbidden());
    }

    @Test
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

        Student student = Student.builder()
                                 .group(new Group())
                                 .build();
        mvc.perform(MockMvcRequestBuilders.get("/student/getEvents")
                                          .with(user(new CustomUserDetails(student))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(lesson.getId()));
    }

    @Test
    @WithAnonymousUser
    public void home_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/student/home"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = { "TEACHER", "ADMIN" })
    public void home_shouldNotAllowAccessForAuthenticatedIfNotStudent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/student/home"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "STUDENT")
    public void home_shouldReturnHomeView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/student/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/home"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attributeExists("teachers"))
                .andExpect(model().attributeExists("classrooms"));
    }
}
