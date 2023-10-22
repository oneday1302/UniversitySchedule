package ua.foxminded.javaspring.universityschedule.controllers.teacher;

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

@WebMvcTest(LessonControllerForTeacher.class)
public class LessonControllerForTeacherTest {

    @MockBean
    private LessonService lessonService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void lessonInfo_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/teacher/lessonInfo/{id}", 1L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = { "STUDENT", "ADMIN" })
    public void lessonInfo_shouldNotAllowAccessForAuthenticatedIfNotTeacher() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/teacher/lessonInfo/{id}", 1L))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "TEACHER")
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
        mvc.perform(MockMvcRequestBuilders.get("/teacher/lessonInfo/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher/lessonInfo"))
                .andExpect(model().attributeExists("lesson"));
    }
}
