package ua.foxminded.javaspring.universityschedule.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.universityschedule.dto.LessonDTO;
import ua.foxminded.javaspring.universityschedule.entities.*;
import ua.foxminded.javaspring.universityschedule.services.*;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LessonController.class)
@MockBean(CourseService.class)
@MockBean(TeacherService.class)
@MockBean(GroupService.class)
@MockBean(ClassroomService.class)
public class LessonControllerTest {

    @MockBean
    private LessonService lessonService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void getEvents_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/getEvents"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
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

        mvc.perform(MockMvcRequestBuilders.get("/getEvents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(lesson.getId()));
    }

    @Test
    @WithAnonymousUser
    public void getLesson_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/getLesson/{id}", 1L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getLesson_shouldRedirectToEditLessonViewIfUserHasRoleAdmin() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.TEACHER);
        teacher.addRole(Role.ADMIN);
        mvc.perform(MockMvcRequestBuilders.get("/getLesson/{id}", 1L)
                                          .with(user(new CustomUserDetails(teacher))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/editLesson/" + 1L));
    }

    @Test
    public void getLesson_shouldRedirectToLessonInfoViewIfUserNotHasRoleAdmin() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.TEACHER);
        mvc.perform(MockMvcRequestBuilders.get("/getLesson/{id}", 1L)
                        .with(user(new CustomUserDetails(teacher))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/lessonInfo/" + 1L));
    }

    @Test
    @WithAnonymousUser
    public void lessonInfo_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/lessonInfo/{id}", 1L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
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
        mvc.perform(MockMvcRequestBuilders.get("/lessonInfo/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("/lessonInfo"))
                .andExpect(model().attributeExists("lesson"));
    }

    @Test
    @WithAnonymousUser
    public void addLesson_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/addLesson"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void addLesson_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/addLesson"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addLesson_shouldReturnAddLessonView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/addLesson"))
                .andExpect(status().isOk())
                .andExpect(view().name("/add/lesson"))
                .andExpect(model().attributeExists("lessonDTO"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attributeExists("teachers"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attributeExists("classrooms"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void saveLesson_shouldRedirectAddLessonView_whenRequestParametersNotValid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/addLesson")
                                          .flashAttr("lessonDTO", new LessonDTO())
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/addLesson"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void saveLesson_shouldRedirectToHomeView() throws Exception {
        LessonDTO dto = new LessonDTO();
        dto.setId(0);
        dto.setCourse(new Course());
        dto.setTeacher(new Teacher());
        dto.setGroup(new Group());
        dto.setClassroom(new Classroom());
        dto.setDate(LocalDate.now());
        dto.setStartTime(LocalTime.of(8, 0));
        dto.setEndTime(LocalTime.of(8, 45));

        mvc.perform(MockMvcRequestBuilders.post("/addLesson")
                                          .flashAttr("lessonDTO", dto)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    @WithAnonymousUser
    public void editLesson_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/editLesson/{id}", 1L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void editLesson_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/editLesson/{id}", 1L))
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
                              .date(LocalDate.now())
                              .startTime(LocalTime.of(8, 0))
                              .endTime(LocalTime.of(8, 45))
                              .build();
        when(lessonService.findById(1)).thenReturn(lesson);
        mvc.perform(MockMvcRequestBuilders.get("/editLesson/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("/edit/lesson"))
                .andExpect(model().attributeExists("lessonDTO"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attributeExists("teachers"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attributeExists("classrooms"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void postEditLesson_shouldRedirectToEditLessonView_whenRequestParametersNotValid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/editLesson/{id}", 1L)
                                          .flashAttr("lessonDTO", new LessonDTO())
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/editLesson/" + 1L));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void postEditLesson_shouldRedirectToHomeView() throws Exception {
        LessonDTO dto = new LessonDTO();
        dto.setId(1);
        dto.setCourse(new Course());
        dto.setTeacher(new Teacher());
        dto.setGroup(new Group());
        dto.setClassroom(new Classroom());
        dto.setDate(LocalDate.now());
        dto.setStartTime(LocalTime.of(8, 0));
        dto.setEndTime(LocalTime.of(8, 45));

        mvc.perform(MockMvcRequestBuilders.post("/editLesson/{id}", 1L)
                                          .flashAttr("lessonDTO", dto)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void deleteLesson_shouldRedirectToHomeView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/deleteLesson/{id}", 1L)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
