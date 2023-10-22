package ua.foxminded.javaspring.universityschedule.controllers.student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.universityschedule.entities.*;
import ua.foxminded.javaspring.universityschedule.services.LessonService;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LessonControllerForStudent.class)
public class LessonControllerForStudentTest {

    @MockBean
    private LessonService lessonService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void lessonInfo_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/student/lessonInfo/{id}", 1L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = { "TEACHER", "ADMIN" })
    public void lessonInfo_shouldNotAllowAccessForAuthenticatedIfNotStudent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/student/lessonInfo/{id}", 1L))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "STUDENT")
    public void lessonInfo_shouldReturnLessonInfoView() throws Exception {
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
        mvc.perform(MockMvcRequestBuilders.get("/student/lessonInfo/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("student/lessonInfo"))
                .andExpect(model().attributeExists("lesson"));
    }
}
